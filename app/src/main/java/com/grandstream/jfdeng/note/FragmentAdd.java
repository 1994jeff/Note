package com.grandstream.jfdeng.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yf on 18-7-9.
 */

public class FragmentAdd extends Fragment {

    private EditText mTitle;
    private TextView mTime;
    private EditText mContent;
    private Button save;

    DataBaseHelper mHelper;
    SQLiteDatabase db;

    int noteId = -1;
    Note note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            noteId = bundle.getInt("id");
        }

        mHelper = new DataBaseHelper(getActivity(),"notes.db",null,1);
        db = mHelper.getReadableDatabase();

        mTitle = (EditText) view.findViewById(R.id.title);
        mTime = (TextView) view.findViewById(R.id.time);
        mContent = (EditText) view.findViewById(R.id.content);
        save = (Button) view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(submit()){
                    Toast.makeText(getContext(), "保存成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        if(noteId==-1) return;
        String sql = "select * from notes where id=?";
        Cursor cursor = db.rawQuery(sql,new String[]{noteId+""});
        while (cursor!=null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            note = new Note(id,title,content,date);
        }
        if(note!=null){
            mTitle.setText(note.getTitle());
            mTime.setText(note.getDate());
            mContent.setText(note.getContent());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private boolean submit() {
        // validate
        String titleString = mTitle.getText().toString().trim();
        if (TextUtils.isEmpty(titleString)) {
            Toast.makeText(getContext(), "请输入标题", Toast.LENGTH_SHORT).show();
            return false;
        }

        String contentString = mContent.getText().toString().trim();
        if (TextUtils.isEmpty(contentString)) {
            Toast.makeText(getContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String sql = "insert into notes(title,content,date) values('"+titleString+"','"+contentString+"','"+format.format(new Date())+"')";
        db.execSQL(sql);

        return true;
    }
}
