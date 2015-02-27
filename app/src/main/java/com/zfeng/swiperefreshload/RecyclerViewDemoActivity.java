package com.zfeng.swiperefreshload;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zfeng on 2015-2-27.
 */
public class RecyclerViewDemoActivity extends ActionBarActivity
{
    private SwipeRefreshLoadLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String[] arrays;
    private ArrayList<String> arrayList;
    private DemoAdapter demoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        swipeRefreshLayout=(SwipeRefreshLoadLayout)findViewById(R.id.recycler_swipe);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrays=getResources().getStringArray(R.array.test_demo);
        arrayList=new ArrayList<String>();

        for(int i=0;i<20;++i)
        {
            arrayList.add(i+"");
        }
        demoAdapter=new DemoAdapter();
        recyclerView.setAdapter(demoAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLoadLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        swipeRefreshLayout.setLoadMoreListener(new SwipeRefreshLoadLayout.LoadMoreListener()
        {
            @Override
            public void loadMore()
            {
                loadMoreData();
            }
        });
    }

    private void refreshContent()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(!arrayList.isEmpty())
                {
                    arrayList.clear();
                }
                for(int i=0;i<arrays.length;++i)
                {
                    arrayList.add(arrays[i]);
                }
                recyclerView.setAdapter(demoAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
    private void loadMoreData()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                int lastFlag=Integer.valueOf(arrayList.get(arrayList.size() - 1));
                for(int i=0;i<5;++i)
                {
                    arrayList.add(String.valueOf(lastFlag+i+1));
                }
                demoAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setLoadMore(false);
            }
        },1000);
    }

    private class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder>
    {
        class DemoViewHolder extends RecyclerView.ViewHolder
        {
            private TextView textView;

            public DemoViewHolder(View itemView)
            {
                super(itemView);
                this.textView=(TextView)itemView;
            }

            public void addDetails(int position)
            {
                textView.setText(arrayList.get(position));
            }
        }

        @Override
        public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
            return new DemoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DemoViewHolder holder, int position)
        {
            holder.addDetails(position);
        }

        @Override
        public int getItemCount()
        {
            return arrayList.size();
        }
    }
}
