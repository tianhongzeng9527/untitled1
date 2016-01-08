package tools;

/**
 * Created by tian on 15-12-28.
 */
public class Constants {
    public final static int MESSAGE_LENGTH1 = 13;
    public final static int MESSAGE_LENGTH2 = 10;
    public final static int KEY_NUM = 5;
    public final static int SENSITIVE_WORD_NUM = 1;
    public final static int LAYER = 2;
    public final static String NOISE = "noise";
    public final static double SIMILAR_SCORE_LINE = -0.5;
    public final static int TIME_OUT = 30000;
    public final static String SENSITIVE_CATEGORY = "sensitive";
    public final static String SENSITIVE_URL = "http://192.168.1.106:8080/zh-school/api/sensitive.do";
    public final static String CATEGORY_URL = "http://192.168.1.106:8080/zh-school/api/behavior.do";
    public final static String WORDSDISTANCE_URL = "http://192.168.1.106:8080/tianhongzeng/wordsDistance?word1=%s&word2=%s";
    public final static String SIMILARWORDS_URL = "http://192.168.1.106:8080/tianhongzeng/wordsDistance?word=%s&num=%s";
    public final static int RETURN_TYPE_SENSITIVE = 1;
    public final static int RETURN_TYPE_NORMAL = 2;
    public final static int RETURN_TYPE_NOISE = 3;
}
