package com.xtrend.zt_srecycleview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lin on 2018/9/3.
 */
public class SwipeAdapter extends RecyclerView.Adapter {

    private RecyclerView.Adapter mAdapter;
    private View mFooterView;

    private boolean showFooter = false;

    public SwipeAdapter(View mFooterView, RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        this.mFooterView = mFooterView;
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        int adapterCount;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (position < adapterCount) {
                return mAdapter.getItemViewType(position);
            }
        }
        // footer view
        return RecyclerView.INVALID_TYPE;
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    @Override
    public long getItemId(int position) {
        if (mAdapter != null) {
            int adapterCount = mAdapter.getItemCount();
            if (position < adapterCount) {
                return mAdapter.getItemId(position);
            }
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecyclerView.INVALID_TYPE) {
            return new FooterViewHolder(mFooterView);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int adapterCount;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (position < adapterCount) {
                mAdapter.onBindViewHolder(holder, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            if (showFooter) {
                return getFootersCount() + mAdapter.getItemCount();
            } else {
                return mAdapter.getItemCount();
            }
        } else {
            return getFootersCount();
        }
    }

    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - getFootersCount();
    }

    public int getFootersCount() {
        return mFooterView == null ? 0 : 1;
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
