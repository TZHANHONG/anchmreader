package com.status.myapplication.activityread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


import com.status.myapplication.R;
import com.status.myapplication.adapter.CatalogListAdapter;
import com.status.myapplication.jchmlib.ChmFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CatalogListActivity extends Activity {

    private RecyclerView recyclerView;
    private CatalogListAdapter mAdapter;
    private List<String> mList;

    private FrameLayout frameLayout;
    private ProgressBar progressBar;

    private String filePath = Environment.getExternalStorageDirectory() + "/汇编语言教程.chm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_list);
        recyclerView = findViewById(R.id.recycler_view);
        frameLayout = findViewById(R.id.frame_loading);
        progressBar = findViewById(R.id.progress_loading);
        mList = new ArrayList<>();
        mAdapter = new CatalogListAdapter(this, mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ChmFile file;
                file = ChmFile.getInstance(filePath);
                file.getTopicsTree();
                file.pathToTitle.values();
                Iterator<String> iterator = file.pathToTitle.values().iterator();
                mAdapter.setFileEntry(file);
                while (iterator.hasNext()) {
                    String str = iterator.next();
                    mList.add(str);
                }
                handle();

            }
        }).start();
    }

    private void handle() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                frameLayout.setVisibility(View.GONE);
            }
        });
    }
}
