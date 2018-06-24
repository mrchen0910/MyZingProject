package cc.example.com.myzingproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * phone数据库
 * Created by CC on 2017/4/20.
 */

public class PhonesDB extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "PHONES.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "phones_table";
    public final static String PHONE_ID = "phone_id";
    public final static String PHONE_NUMBER = "phone_number";


    public PhonesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABLE_NAME + " (" + PHONE_ID
                + " INTEGER primary key autoincrement, " + PHONE_NUMBER + " text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME , null, null, null, null, null, null);
        return cursor;
    }
    //增加操作
    public long insert(String phonenumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PHONE_NUMBER, phonenumber);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = PHONE_ID + " = ?";
        String[] whereValue ={ Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
    }
    //clear
/*    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME);

        db.delete from TableName;  //清空数据
        update sqlite_sequence SET seq = 0 where name ='TableName';//自增长ID为0
    }*/

    public void deleteDatabse() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "1", new String[] {});
        //Log.d("Database stuff", "Database table succesfully deleted");
        db.close();
    }
    //修改操作
    public void update(int id, String phonenumber,String author)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = PHONE_ID + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(PHONE_NUMBER, phonenumber);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
