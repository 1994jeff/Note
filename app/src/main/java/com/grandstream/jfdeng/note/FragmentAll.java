package com.grandstream.jfdeng.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yf on 18-7-9.
 */

public class FragmentAll extends Fragment {

    List<Note> list;

    public void dropData() {
        boolean[] status = mAdapter.getStatus();
        int hasCheckItem = -1;
        for(int i=list.size()-1;i>=0;i--){
            if(status[i]){
                status[i] = false;
                deleteNote(list.get(i).getId());
                list.remove(i);
                hasCheckItem = 1;
            }
        }
        if(hasCheckItem<0){
            Toast.makeText(getActivity(),"请选择要删除的日记！",Toast.LENGTH_SHORT).show();
        }else {
            mAdapter.hideAllCheckBox();
        }
    }

    private void deleteNote(int id) {
        String sql = "delete from notes where id=?";
        db.execSQL(sql,new String[]{id+""});
    }

    interface FinishSetArgs{
        void onFinish(Fragment fragment);
    }

    FinishSetArgs finishSetArgs;

    public FinishSetArgs getFinishSetArgs() {
        return finishSetArgs;
    }

    public void setFinishSetArgs(FinishSetArgs finishSetArgs) {
        this.finishSetArgs = finishSetArgs;
    }

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

        list = getData();
        mAdapter = new MyAdapter(getActivity(),list);
        mListItem = (ListView) view.findViewById(R.id.list_item);
        mListItem.setAdapter(mAdapter);
        mListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentAdd fragmentAdd = new FragmentAdd();
                Bundle args = new Bundle();
                args.putInt("id",mAdapter.getItemNoteId(i));
                fragmentAdd.setArguments(args);
                if(finishSetArgs!=null)
                finishSetArgs.onFinish(fragmentAdd);
            }
        });
        mListItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> view, View view1, int i, long l) {
                mAdapter.showAllCheckBox();
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
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
