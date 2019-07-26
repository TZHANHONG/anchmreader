package com.status.myapplication.activityread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;


import com.status.myapplication.R;
import com.status.myapplication.jchmlib.ByteBufferHelper;
import com.status.myapplication.jchmlib.ChmFile;
import com.status.myapplication.jchmlib.ChmUnitInfo;
import com.status.myapplication.utils.MImageGetter;

import java.nio.ByteBuffer;


public class ContentActivity extends Activity {

    private TextView textView;
    private ChmUnitInfo ui;
    private StringBuffer builder = new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        textView = findViewById(R.id.content_text);

        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        ui = (ChmUnitInfo) intent.getSerializableExtra("entry");
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ByteBuffer buffer = ChmFile.getInstance("").retrieveObject(ui, 0, ui.getLength());
                    String data = ByteBufferHelper.peakAsString(buffer, ChmFile.getInstance("").encoding);
                    String a = "<style type=\"text/css\"> div \n" +
                            "{ margin: 0; padding: 0; outline: 0; }</style>";
                    String s1 = a.replaceAll("<style([\\s\\S]*)</style>", "a");
                    //过滤掉格式相关的Html，事实证明，不这样做的话，格式相关的html代码会显示出来
                    final String text = data.replaceAll("(?i)<style([\\s\\S]*)</style>", "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //加上这句让图片显示出来
                            Spanned s = Html.fromHtml(text, new MImageGetter(textView, ContentActivity.this), null);
                            textView.setText(s);
                        }
                    });
                }
            }).start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
