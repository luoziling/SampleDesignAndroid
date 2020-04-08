package com.wzb.sampledesign.ui.expertentry.TreeView;


import android.app.Fragment;
import android.os.Bundle;

import android.view.MenuItem;

import com.wzb.sampledesign.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class TreeViewActivity extends AppCompatActivity {
    public final static String FRAGMENT_PARAM = "fragment";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treeview_single_fragment);

        Bundle b = getIntent().getExtras();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //传值并且开始
        Fragment f = Fragment.instantiate(this, FolderStructureFragment.class.getName());
        f.setArguments(b);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,f,FolderStructureFragment.class.getName()).commit();
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, FolderStructureFragment.class.getName()).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        ActionBar actionBar = this.getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
//        return super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(menuItem);

    }

}
