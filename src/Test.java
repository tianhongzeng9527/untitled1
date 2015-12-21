import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tian on 15-12-21.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://192.168.20.9:8080/similarWords?word=数学&num=4");
        URLConnection urlcon = url.openConnection();
        InputStream is = urlcon.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        String s = "";
        while ((s = buffer.readLine()) != null) {
            System.out.println(s);
        }
        buffer.close();
    }
}
