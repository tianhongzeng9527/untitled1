import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tian on 16-1-15.
 */
public class test {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date dt=df.parse("20160606");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_MONTH, -1);
        Date dt1 = rightNow.getTime();
        System.out.println(df.format(dt1));
    }
}
