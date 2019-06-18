package supinfo.com.supbank.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author long
 */
public class LocalData {

    /**
     * 写入本地文件
     */
    public static boolean writeNative(Map<String,Object> params, Context context){
        SQLiteDatabase db= DataHelper.getInstance(context).getWritableDatabase();
        String json = JSON.toJSONString(params);
        db.execSQL("insert into " + DataHelper.TABLE_NAME + " (id, userInfo) values (1, '" + json + "')");
        db.setTransactionSuccessful();
        return true;
    }

    /**
     * 检测本地账号
     * @return
     */
    public static boolean isNative(Context context){
        return null != getNativeAccount(context);
    }

    /**
     * 获取本地账号信息
     */
    public static Map<String,Object> getNativeAccount(Context context){
        SQLiteDatabase db= DataHelper.getInstance(context).getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=db.query(DataHelper.TABLE_NAME,DataHelper.COL_NAME,null,null,null,null,"id");
        while (cursor.moveToNext()){
            String userInfo =cursor.getString(cursor.getColumnIndex("userInfo"));
            arrayList.add(userInfo);
        }
        cursor.close();
        if(arrayList.size() == 0){
            return null;
        }
        String result = arrayList.get(0);
        return JSON.parseObject(result);
    }


    /**
     * 本地密码登录
     */
    public static boolean loginNative(String password,Context context){
        if(getNativeAccount(context).get("password").toString().equals(password)){
            return true;
        }
        return false;
    }

}
