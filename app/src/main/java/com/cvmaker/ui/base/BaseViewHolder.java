package com.cvmaker.ui.base;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by aya mohamed on 08/02/18.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);

}
