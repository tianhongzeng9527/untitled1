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
                    "    \"total\": 5,\n" +
                    "    \"rows\": 6\n" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isNeedChange;
    public Category(){

    }
}
