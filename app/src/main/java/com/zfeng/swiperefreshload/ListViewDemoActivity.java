package com.zfeng.swiperefreshload;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewDemoActivity extends ActionBarActivity
{
    private SwipeRefreshLayout sRLayout;
    private SwipeRefreshLoadLayout swipeRefreshLayout;
    private ListView listView;
    private TestBaseAdapter baseAdapter;
    private String[] arrays;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=(SwipeRefreshLoadLayout)findViewById(R.id.main_swipe);
        listView=(ListView)findViewById(R.id.main_list);
        arrays=getResources().getStringArray(R.array.test_demo);
        arrayList=new ArrayList<String>();

        for(int i=0;i<arrays.length;++i)
        {
            arrayList.add(arrays[i]);
        }
        baseAdapter=new TestBaseAdapter();
        listView.setAdapter(baseAdapter);
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
                baseAdapter.notifyDataSetChanged();
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
                for(int i=0;i<5;++i)
                {
                    arrayList.add(String.valueOf(arrayList.size()+i));
                }
                baseAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setLoadMore(false);
            }
        },1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent();
            intent.setClass(ListViewDemoActivity.this,RecyclerViewDemoActivity.class);
            ListViewDemoActivity.this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class TestBaseAdapter extends BaseAdapter
    {
        private TextView textView;

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView==null)
            {
                convertView= LayoutInflater.from(ListViewDemoActivity.this).inflate(android.R.layout.simple_list_item_1,null);
                textView=((TextView)convertView);
                convertView.setTag(textView);
            }else{
                textView=(TextView)convertView.getTag();
            }
            textView.setText(arrayList.get(position));
            return convertView;
        }
    }
}
