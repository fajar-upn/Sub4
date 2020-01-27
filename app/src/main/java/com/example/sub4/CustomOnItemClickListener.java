package com.example.sub4;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallBack onItemClickCallBack;

    public CustomOnItemClickListener(int position, OnItemClickCallBack onItemClickCallBack) {
        this.position = position;
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallBack.onItemClick(v,position);
    }

    public interface OnItemClickCallBack{
        void onItemClick(View view, int position);
    }
}
