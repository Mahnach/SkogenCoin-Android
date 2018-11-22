package com.skogen.coin.skeleton.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseVH<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public BaseVH(ViewGroup parent, @LayoutRes int layRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(layRes, parent, false));
        itemView.setOnClickListener(this);
    }

    public abstract void bind(T item);

    @Override
    public final void onClick(View view) {
        onItemClicked(getAdapterPosition());
    }

    protected Context ctx() {
        return itemView.getContext();
    }

    public void onItemClicked(int position) {
        // Handle
    }
}