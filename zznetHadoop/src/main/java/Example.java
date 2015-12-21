import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class Example {
    public static void main(String args[]) throws IOException {
        TitleKeyWords handel= new TitleKeyWords(new URL("http://blog.chenlb.com/2009/04/chinese-segment-mmseg4j-dictionary-format.html"),10);
        handel.init();
        List<String> resutls = new ArrayList<String>();
        resutls = handel.getTopNumKey();
        System.out.println("--------------------------------");
        for(int i = 0; i < resutls.size(); i++){
            System.out.println(resutls.get(i));
        }
//        URL url = new URL("http://blog.chenlb.com/2009/04/chinese-segment-mmseg4j-dictionary-format.html");
//        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//        String line = "";
//        InputStreamReader input = new InputStreamReader(httpURLConnection
//                .getInputStream(), "utf-8");
//        StringBuilder contentBuf = new StringBuilder();
//        BufferedReader bufReader = new BufferedReader(input);
//        while ((line = bufReader.readLine()) != null) {
//            contentBuf.append(line);
//        }
//        System.out.println("---------------------------");
//        Handler handel2 = new Handler(contentBuf.toString(), 10);
//        handel2.init();
//        List<String> results2 = new ArrayList<>();
//        results2 = handel2.getTopNumKey();
//        for(int i = 0; i < results2.size(); i++){
//            System.out.println(results2.get(i));
//        }
    }

    Example() {

    }
}
