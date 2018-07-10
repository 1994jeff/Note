package com.grandstream.jfdeng.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,FragmentAll.FinishSetArgs{

    private TextView mAll;
    private TextView mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {

        mAll = (TextView) findViewById(R.id.all);
        mAll.setOnClickListener(this);
        mAdd = (TextView) findViewById(R.id.add);
        mAdd.setOnClickListener(this);

        FragmentAll all = new FragmentAll();
        all.setFinishSetArgs(this);
        switchFragment(all);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if(fragment instanceof FragmentAll){
                FragmentAll all = (FragmentAll) fragment;
                all.dropData();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all:
                FragmentAll all = new FragmentAll();
                all.setFinishSetArgs(this);
                switchFragment(all);
                break;
            case R.id.add:
                switchFragment(new FragmentAdd());
                break;
        }
    }


    @Override
    public void onFinish(Fragment fragment) {
        switchFragment(fragment);
    }
}
