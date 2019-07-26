package com.status.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.status.myapplication.R;
import com.status.myapplication.activityread.ContentActivity;
import com.status.myapplication.jchmlib.ChmFile;
import com.status.myapplication.jchmlib.ChmUnitInfo;

import java.io.Serializable;
import java.util.List;



public class CatalogListAdapter extends RecyclerView.Adapter {

    private List<String> mList;
    private Context mContext;

    private ChmFile fileEntry;
    public CatalogListAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    public void setFileEntry(ChmFile fileEntry) {
        this.fileEntry = fileEntry;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_catelog, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final String s = mList.get(position);
        ((ViewHolder) holder).textView.setText(s);
        ((ViewHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContentActivity.class);
                fileEntry.getPathOfObject(s);
                ChmUnitInfo ui =  fileEntry.resolveObject(fileEntry.getPathOfObject(s));
                intent.putExtra("entry", ui);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
