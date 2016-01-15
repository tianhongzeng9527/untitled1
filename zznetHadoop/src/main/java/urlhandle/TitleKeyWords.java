package urlhandle;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.TokenUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PackedTokenAttributeImpl;
import org.jsoup.Jsoup;
import tools.Constants;
import usrhandle.SensitiveWord;

import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class TitleKeyWords {
    private URL url;
    private int topNum;
    private Map<String, Integer> contentParticiple;
    private List<String> topNumKey;
    private String claenHtml = "";
    private int timeout;
    private String title;
    private String content;
    private Set<String> titleParticipleFrequency;
    private String html;
    private static Set<String> stopWordsDictionary;
    private Set<String> containsSensitiveWordsList = new HashSet<>();
    private static boolean isInitDictionary = true;
    private String sensitiveCategory;

    private static Logger logger = Logger.getLogger(TitleKeyWords.class);

    public TitleKeyWords(URL url, int topNum) {
        this.url = url;
        this.topNum = topNum;
    }

    public TitleKeyWords(String html, int topNum) {
        this.html = html;
        this.topNum = topNum;
    }

    private void setTitle() {
        title = Jsoup.parse(claenHtml).title();
    }

    private void setClaenHtml() throws IOException {
        Readability readability;

        if ((this.html != null) && (!"".equals(html)))
            readability = new Readability(html);
        else
            readability = new Readability(url, timeout);
        if (readability != null)
            readability.init();

        claenHtml = readability.outerHtml();
    }

    private void setContent() {
        content = Jsoup.parse(claenHtml).body().text();
    }

    private void setContentParticiple() throws IOException {
        List<String> contentWords = toWords(content, new ComplexAnalyzer());
        for (String word : contentWords)
            if (!stopWordsDictionary.contains(word)) {
                if (contentParticiple.containsKey(word)) {
                    contentParticiple.put(word, contentParticiple.get(word) + 1);
                } else {
                    contentParticiple.put(word, 1);
                }
                setContainsSensitiveWordsList(word);
            }
    }

    private void setContainsSensitiveWordsList(String word) {
//        if (drugWordsDictionary.contains(word)) {
//            containsDrugWordsList.add(word);
//        } else if (illegalWordsDictionary.contains(word)) {
//            containsIllegalWordsList.add(word);
//        } else if (otherSensitiveWordsDictionary.contains(word)) {
//            containsOtherWordsList.contains(word);
//        } else if (politicalWordsDictionary.contains(word)) {
//            containsPoliticalWordsList.contains(word);
//        } else if (sexyWordsDictionary.contains(word)) {
//            containsSexyWordsList.contains(word);
//        } else if (supersitionWordsDictionary.contains(word)) {
//            containsSupersitionWordsList.contains(word);
//        }
        if (SensitiveWord.sensitiveWordList.contains(word)) {
            containsSensitiveWordsList.add(word);
        }
    }

    private List<String> toWords(String txt, Analyzer analyzer) throws IOException {
        List<String> words = new ArrayList<String>();
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("text", new StringReader(txt));
            ts.reset();
            for (PackedTokenAttributeImpl t = new PackedTokenAttributeImpl(); (t = TokenUtils.nextToken(ts, t)) != null; ) {
                words.add(t.toString());
            }
        } catch (IOException e) {
            logger.info(e.toString());
            throw e;
        } finally {
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    logger.info(e.toString());
                }
            }
        }
        return words;
    }

    private void setTitleParticipleFrequency() throws IOException {
        List<String> contentWords = toWords(title, new ComplexAnalyzer());
        for (String word : contentWords)
            if (!stopWordsDictionary.contains(word) && word.length() != 1)
                titleParticipleFrequency.add(word);
        sortMapByValue(contentParticiple);
    }

    private void setTopNumKey() {
        if (topNum > titleParticipleFrequency.size()) {
            topNum = titleParticipleFrequency.size();
        }
        int i = 1;
        for (String key : titleParticipleFrequency) {
            topNumKey.add(key);
            i++;
            if (topNum < i) {
                break;
            }
        }
    }

    private void sortMapByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, Integer> mapping : list) {
            if (titleParticipleFrequency.contains(mapping.getKey())) {
                titleParticipleFrequency.add(mapping.getKey());
            }
        }
    }

    public Map<String, Integer> getContentParticiple() {
        return contentParticiple;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTopNumKey() {
        return topNumKey;
    }

    public String getClaenHtml() {
        return claenHtml;
    }

    public void init() throws IOException {
        claenHtml = "";
        contentParticiple = new TreeMap<String, Integer>();
        topNumKey = new ArrayList<String>();
        timeout = Constants.TIME_OUT;
        titleParticipleFrequency = new LinkedHashSet<String>();
//        initContainsSensitiveWordsList();
        initStopWords();
//        initContainList();
        if (isInitDictionary) {
            isInitDictionary = false;
            initDictionary();
        }
        setClaenHtml();
        setContent();
        setTitle();
        setContentParticiple();
        setTitleParticipleFrequency();
        setTopNumKey();
//        initContainsSensitiveWordsList();
        initCategory();
    }

    private void initCategory() {
        Map<String, Integer> categoryScore = new HashMap<>();
        for (String sensitiveWord : containsSensitiveWordsList) {
            String parents = SensitiveWord.sensitiveWordCorrespondParents.get(sensitiveWord);
            if (categoryScore.containsKey(parents)) {
                categoryScore.put(parents, categoryScore.get(parents) + 1);
            } else {
                categoryScore.put(parents, 1);
            }
        }
        int max = 0;
        for (Map.Entry<String, Integer> map : categoryScore.entrySet()) {
            if (map.getValue() > max) {
                sensitiveCategory = map.getKey();
            }
        }
    }

    public String getSensitiveCategory() {
        return this.sensitiveCategory;
    }

    public Set<String> getContainsSensitiveWordsList() {
        return this.containsSensitiveWordsList;
    }

    private void initDictionary() throws IOException {
//        initStopWords();
//        initDrugWordsDictionary();
//        initIllegalWordsDictionary();
//        initOtherSensitiveWordsDictionary();
//        initPoliticalWordsDictionary();
//        initSexyWordsDictionary();
//        initSupersitionWordsDictionary();
    }

    private void initStopWords() throws IOException {
//        stopWordsDictionary = getWords("/stopWordsDictionary.txt");
        stopWordsDictionary = new HashSet<String>();
    }

    private Set<String> getWords(String path) throws IOException {
        Set<String> words = new HashSet<String>();
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String s = "";
        while ((s = bufferedReader.readLine()) != null) {
            s = s.trim();
            String[] splits = s.split(" ");
            for (int i = 0; i < splits.length; i++) {
                words.add(splits[i]);
            }
        }
        return words;
    }

}