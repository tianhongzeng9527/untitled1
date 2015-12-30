package usrhandle;

import org.json.JSONException;
import org.json.JSONObject;

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
