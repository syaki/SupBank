package supinfo.com.supbank.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * @Author long
 */
public class DataHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "user.db";
    public static final String TABLE_NAME = "user_message";
    public static final String [] COL_NAME = {"id", "userInfo"};
    private static DataHelper dataHelper;

    private DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DataHelper getInstance(Context context){
        if(dataHelper == null){
            return new DataHelper(context);
        }
        return dataHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + TABLE_NAME + " (id integer primary key, userInfo text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
