package com.grandstream.jfdeng.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created database
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE = "create table if not exists notes(" +
            "id integer primary key autoincrement," +
            "title varchar," +
            "content text," +
            "date varchar)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

    }
}
