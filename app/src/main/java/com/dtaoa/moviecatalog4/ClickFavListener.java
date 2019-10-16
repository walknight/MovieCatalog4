package com.dtaoa.moviecatalog4;

import android.view.View;

public class ClickFavListener implements View.OnClickListener {
    private final int position;
    private final OnItemClickCallback onItemClickCallback;

    public ClickFavListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);
    }
    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }



}
