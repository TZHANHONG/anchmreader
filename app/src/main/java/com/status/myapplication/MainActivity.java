package com.status.myapplication;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.status.myapplication.activityread.CatalogListActivity;

import easypermission.davidinchina.com.easylibrary.EasyPermission;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    //CHM文件路径
    private String filePath = Environment.getExternalStorageDirectory() + "/jQuery-UI-Reference-1.7.chm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        EasyPermission.with(this).code(100).request();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CatalogListActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermission.handleResult(this, requestCode, permissions, grantResults);//处理权限申请回调结果
    }
}
