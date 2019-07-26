package com.status.myapplication.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.TypedValue;
import android.widget.TextView;

import com.status.myapplication.jchmlib.ChmFile;
import com.status.myapplication.jchmlib.ChmUnitInfo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MImageGetter implements Html.ImageGetter {
    private Context c;
    private TextView container;

    public MImageGetter(TextView text, Context c) {
        this.c = c;
        this.container = text;
    }

    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable = null;
        InputStream is = null;
        ChmUnitInfo ui = ChmFile.getInstance("").resolveObject("/" + source);
        ByteBuffer buffer = ChmFile.getInstance("").retrieveObject(ui, 0, ui.getLength());
        if (buffer == null) {
            return null;
        }
        int gotLen = buffer.limit() - buffer.position();
        byte[] bytes = new byte[gotLen];

        buffer.mark();
        while (buffer.hasRemaining()) {
            buffer.get(bytes);
        }
//            is = c.getResources().getAssets().open(source);
        is = new ByteArrayInputStream(bytes);
        try {
            TypedValue typedValue = new TypedValue();
            typedValue.density = TypedValue.DENSITY_DEFAULT;
            drawable = Drawable.createFromResourceStream(null, typedValue, is, "src");
//            DisplayMetrics dm = c.getResources().getDisplayMetrics();
//            int dwidth = dm.widthPixels - 10;//padding left + padding right
//            float dheight = (float) drawable.getIntrinsicHeight() * (float) dwidth / (float) drawable.getIntrinsicWidth();
//            int dh = (int) (dheight + 0.5);
//            int wid = dwidth;
//            int hei = dh;
                /*int wid = drawable.getIntrinsicWidth() > dwidth? dwidth: drawable.getIntrinsicWidth();
                int hei = drawable.getIntrinsicHeight() > dh ? dh: drawable.getIntrinsicHeight();*/
//            drawable.setBounds(0, 0, wid, hei);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
