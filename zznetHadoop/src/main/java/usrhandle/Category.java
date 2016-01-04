package usrhandle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tian on 15-12-18.
 */

public class Category {
    public static JSONObject category;

    static {
        try {
            category = new JSONObject("{\n" +
                    "    \"数学\": {\n" +
                    "        \"语文\": 1,\n" +
                    "        \"英语\": 1\n" +
                    "    },\n" +
                    "    \"物理\": {\n" +
                    "        \"化学\": 1,\n" +
                    "        \"生物\": 1\n" +
                    "    }\n" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isNeedChange;
    public Category(){

    }
}
