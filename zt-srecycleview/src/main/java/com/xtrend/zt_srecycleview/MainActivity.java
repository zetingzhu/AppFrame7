package com.xtrend.zt_srecycleview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SwipeRecyclerView llllllllll;
    private RecentlyViewedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    int sss = 0;

    private void initView() {
        llllllllll = findViewById(R.id.llllllllll);
        mAdapter = new RecentlyViewedAdapter(this, getData(0));
        llllllllll.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        llllllllll.setEnableLoadMore(true);
        llllllllll.addFootView(LayoutInflater.from(getBaseContext()).inflate(R.layout.pull_to_load_footer, llllllllll, false));
        llllllllll.setAdapter(mAdapter);
        llllllllll.setOnLoadMoreListener(new SwipeRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (sss == 5) {
                    llllllllll.loadMoreComplete();
                } else {
                    sss++;
                    List<String> data = getData(mAdapter.getItemCount());
                    Log.d(TAG, "这里造数据：" + data);
                    mAdapter.addList(data);
                    llllllllll.loadMoreComplete();
                }
            }
        });
    }

    public List<String> getData(int cc) {
        List<String> mList = new ArrayList<>();
        int count = cc + 20;
        for (int i = cc; i < count; i++) {
            mList.add("---" + i);
        }
        return mList;
    }

}