package usrhandle;

import org.json.JSONException;
import org.json.JSONObject;
import tools.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by tian on 15-12-18.
 */

public class Category {
    public static JSONObject category;
    public static Map<String, String> categoryCorrespondId = new HashMap<>();
    public static Map<String, String> categorySuperId = new HashMap<>();
    public static Set<String> categorySuper = new HashSet<>();
    public static Map<String, String> superCategoryId = new HashMap<>();
    public static String learn = "学习";
    static {
        try {
            URL url = new URL(Constants.CATEGORY_URL);
            URLConnection urlcon = url.openConnection();
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            String s = buffer.readLine();
            category = new JSONObject(s);
            buffer.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        Iterator iterator = category.keys();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            JSONObject jsonObject = null;
            String[] splits = key.split(Constants.CATEGORY_ID_SPLIT);
            categorySuper.add(splits[0]);
            if(learn.equals(splits[0]))
                learn = key;
            System.out.println(learn);
            superCategoryId.put(splits[0],splits[1]);
            try {
                jsonObject = category.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator iterator1 = jsonObject.keys();
            while (iterator1.hasNext()) {
                String key1 = iterator1.next().toString();
                String value = null;
                try {
                    value = jsonObject.getString(key1);
                    categoryCorrespondId.put(key1, value);
                    categorySuperId.put(key1, splits[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean isNeedChange;

    public Category() {

    }

    public static void main(String[] args) {
        System.out.println(categoryCorrespondId);
        System.out.println(categorySuperId);
        System.out.println(categorySuper);
        System.out.println(superCategoryId);
    }
}
