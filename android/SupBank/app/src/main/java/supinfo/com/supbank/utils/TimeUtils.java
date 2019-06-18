package supinfo.com.supbank.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 * @Author long
 */
public class TimeUtils {

    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
