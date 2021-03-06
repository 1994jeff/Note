package com.grandstream.jfdeng.note;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yf on 18-7-9.
 */

public class MyAdapter extends BaseAdapter {

    Context mContext;
    List<Note> mList;
    boolean[] status;
    boolean isShowCheckBox;

    public void showAllCheckBox(){
        isShowCheckBox = true;
        this.notifyDataSetChanged();
    }

    public void hideAllCheckBox(){
        isShowCheckBox = false;
        for(int i=0;i<status.length;i++){
            status[i] = false;
        }
        this.notifyDataSetChanged();
    }

    public boolean[] getStatus(){
        return status;
    }

    public MyAdapter(Context mContext, List<Note> mList) {
        this.mContext = mContext;
        this.mList = mList;
        status = new boolean[mList.size()];
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mList != null ? mList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getItemNoteId(int i){
        return mList.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup group) {

        ViewHolder holder = null;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_notes, group, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTitle.setText(mList.get(i).getTitle());
        holder.mDate.setText(mList.get(i).getDate());
        if(isShowCheckBox){
            holder.mCheckbox.setVisibility(View.VISIBLE);
        }else {
            holder.mCheckbox.setVisibility(View.GONE);
        }
        if(!status[i]){
            holder.mCheckbox.setChecked(false);
        }else {
            holder.mCheckbox.setChecked(true);
        }
        holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean b) {
                if(b){
                    status[i] = true;
                }else {
                    status[i] = false;
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        public View rootView;
        public TextView mTitle;
        public TextView mDate;
        public CheckBox mCheckbox;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTitle = (TextView) rootView.findViewById(R.id.title);
            this.mDate = (TextView) rootView.findViewById(R.id.dateStr);
            this.mCheckbox = (CheckBox) rootView.findViewById(R.id.checkbox);
        }

    }
}
