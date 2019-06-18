package supinfo.com.supbank.utils;

import android.content.Context;
import android.widget.Toast;
/**
 * UI util
 * @Author long
 */
public class UIUtils {

    private static Toast toast;

    /**
     * 生成3000 到 10000 之间的毫秒数
     * @return
     */
    public static long getRandomMillsSeconds(){
        return (long)(Math.random()*7000 + 3000);
    }

    public static void showShort(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
