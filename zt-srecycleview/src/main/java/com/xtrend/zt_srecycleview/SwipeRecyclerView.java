package com.xtrend.zt_srecycleview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by lin on 2018/9/3.
 */
public class SwipeRecyclerView extends RecyclerView {

    private Adapter mAdapter;
    private OnLoadMoreListener mOnLoadMoreListener;
    private View mFooterView;
    private boolean isLoadingMoreData = false;
    private boolean isEnable = true;
    private OnArriveTopListener onArriveTopListener;
    private TextView tv_footer;
    private String loadingText;

    public SwipeRecyclerView(Context context) {
        super(context);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        if (mFooterView != null)
            adapter = new SwipeAdapter(mFooterView, adapter);
        super.setAdapter(adapter);
        mAdapter = adapter;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mOnLoadMoreListener != null && !isLoadingMoreData) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            if (isEnable && layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition != 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1) {
                if (mFooterView != null) {
                    mFooterView.setVisibility(VISIBLE);
                    if (mAdapter instanceof SwipeAdapter) {
                        ((SwipeAdapter) mAdapter).setShowFooter(true);
                    }
                    if (tv_footer != null)
                        tv_footer.setText(loadingText);
                    isLoadingMoreData = true;
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int firompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            //update by liuyang 回到顶部出现后，一直会闪，firompletelyVisibleItemPosition 可能会为负数
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition > 1) {
                if (onArriveTopListener != null) {
                    onArriveTopListener.onArriveTop(true);
                }
            } else {
                if (onArriveTopListener != null) {
                    onArriveTopListener.onArriveTop(false);
                }
            }
        }

    }

    public void setEnableLoadMore(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public void addFootView(View view) {
        final LayoutManager manager = getLayoutManager();
        if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            if (params == null)
                params = new StaggeredGridLayoutManager.LayoutParams(-1, -2);
            params.setFullSpan(true);
            view.setLayoutParams(params);
        }
        mFooterView = view;
        if (view != null) {
            mFooterView.setVisibility(GONE);
            if (mAdapter instanceof SwipeAdapter) {
                ((SwipeAdapter) mAdapter).setShowFooter(false);
            }
            tv_footer = mFooterView.findViewById(R.id.pull_to_load_footer_hint_textview);
            loadingText = tv_footer.getText().toString();
            if (mAdapter != null) {
                if (!(mAdapter instanceof SwipeAdapter)) {
                    mAdapter = new SwipeAdapter(mFooterView, mAdapter);
                    super.setAdapter(mAdapter);
                }
            }
        }
    }

    private void loadMore() {
        isLoadingMoreData = true;
        mOnLoadMoreListener.onLoadMore();
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void loadMoreComplete() {
        isLoadingMoreData = false;
        if (mFooterView != null) {
            mFooterView.setVisibility(GONE);

            if (mAdapter instanceof SwipeAdapter) {
                ((SwipeAdapter) mAdapter).setShowFooter(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void loadMoreFail() {
        isLoadingMoreData = false;
        if (mFooterView != null) {
            mFooterView.setVisibility(GONE);
            if (mAdapter instanceof SwipeAdapter) {
                ((SwipeAdapter) mAdapter).setShowFooter(false);
            }
        }
    }


    public void loadNoMoreData() {
        isLoadingMoreData = false;
        if (mFooterView != null) {
            mFooterView.setVisibility(VISIBLE);
            if (mAdapter instanceof SwipeAdapter) {
                ((SwipeAdapter) mAdapter).setShowFooter(true);
            }
            if (tv_footer != null)
                tv_footer.setText("No more content");
        }
    }

    public void loadNoMoreData(String tipStr) {
        isLoadingMoreData = false;
        if (mFooterView != null) {
            mFooterView.setVisibility(VISIBLE);
            if (mAdapter instanceof SwipeAdapter) {
                ((SwipeAdapter) mAdapter).setShowFooter(true);
            }
            if (tv_footer != null) {
                if (TextUtils.isEmpty(tipStr)) {
                    tipStr = "No more content";
                }
                tv_footer.setText(tipStr);
            }

        }
    }

    /**
     * set load more listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {

        void onLoadMore();
    }

    /**
     * 设置到达顶部
     *
     * @param onArriveTopListener
     */
    public void setOnArriveTopListener(OnArriveTopListener onArriveTopListener) {
        this.onArriveTopListener = onArriveTopListener;
    }

    public interface OnArriveTopListener {
        void onArriveTop(boolean isArriveTop);
    }
}
