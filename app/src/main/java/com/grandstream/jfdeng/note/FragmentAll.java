package com.grandstream.jfdeng.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yf on 18-7-9.
 */

public class FragmentAll extends Fragment {

    private ListView mListItem;
    private MyAdapter mAdapter;

    DataBaseHelper mHelper;
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all,container,false);
        init(view);
        return view;
    }

    private void init(View view) {

        mHelper = new DataBaseHelper(getActivity(),"notes.db",null,1);
        db = mHelper.getReadableDatabase();

        List<Note> list = getData();
        mAdapter = new MyAdapter(getActivity(),list);
        mListItem = (ListView) view.findViewById(R.id.list_item);
        mListItem.setAdapter(mAdapter);
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
        db.close();
        return list;
    }
}
