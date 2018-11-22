package com.skogen.coin.skeleton.recycler;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRvAdapter<T, VH extends BaseVH<T>> extends RecyclerView.Adapter<VH> {

    protected List<T> items;

    public void setItems(List<T> items) {
        setItems(items, true);
    }

    public void setItems(List<T> items, boolean clone) {
        if (clone) {
            if (this.items != null) {
                this.items.clear();
            }
            this.items = new ArrayList<>(items);
        } else {
            this.items = items;
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (items != null && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addItem(int position, T item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void updateItem(int position, T item) {
        if (items != null && position < items.size()) {
            items.set(position, item);
            notifyItemChanged(position);
        }
    }

    public T getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return items.get(position);
        }
        return null;
    }

    public ArrayList<T> getItemsCopy() {
        return items != null ? new ArrayList<>(items) : null;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(getItem(position));
    }

}
