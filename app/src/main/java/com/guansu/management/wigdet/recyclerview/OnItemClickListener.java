package com.guansu.management.wigdet.recyclerview;

import android.view.View;
/**
 *
 * Created by dongyaoyao
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onLongClick(View view, int position);
}