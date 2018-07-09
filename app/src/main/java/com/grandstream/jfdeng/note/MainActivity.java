package com.grandstream.jfdeng.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListItem;
    private MyAdapter mAdapter;

    DataBaseHelper mHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        mHelper = new DataBaseHelper(this,"notes.db",null,1);
        db = mHelper.getReadableDatabase();

        List<Note> list = getData();
        mAdapter = new MyAdapter(this,list);
        mListItem = (ListView) findViewById(R.id.list_item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private List<Note> getData() {
        List<Note> list = new ArrayList<>();
        String sql = "select * from notes";
        Cursor cursor = db.rawQuery(sql,null);
        Note note = null;
        while (cursor!=null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            note = new Note(id,title,content,date);
            list.add(note);
        }
        return list;
    }
}
