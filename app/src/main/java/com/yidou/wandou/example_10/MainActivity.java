package com.yidou.wandou.example_10;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.yidou.wandou.example_10.bean.News;
import com.yidou.wandou.example_10.utils.GetNews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private List<News.ResultBean.DataBean> mList;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<News.ResultBean.DataBean> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBars();
        initDrawer();//点击按钮，确定数据网络请求
        initViews();//初始化RecyclerView，并设置适配器
        getDatas("top","头条");
    }

    //初始化RecyclerView，并设置适配器
    private void initViews()
    {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseRecyclerAdapter<News.ResultBean.DataBean>(this, null, R.layout.main_item)
        {
            @Override
            protected void convert(BaseViewHolder helper, final News.ResultBean.DataBean item)
            {
                String title = item.getTitle();
                if (title.length() >= 15)
                {

                    helper.setText(R.id.texts_title, title.substring(0, 15) + "...");//避免台头的数据太长，显得不好看
                } else
                {
                    helper.setText(R.id.texts_title, item.getTitle());
                }

                helper.setText(R.id.texts_date, item.getDate());
                helper.setText(R.id.texts_authorname, item.getAuthor_name());
                ImageView imageViews = (ImageView) helper.getConvertView().findViewById(R.id.images_name);
                Picasso.with(MainActivity.this).load(item.getThumbnail_pic_s()).fit().into(imageViews);
                LinearLayout linearLayout = (LinearLayout) helper.getConvertView().findViewById(R.id.item_linear);
                linearLayout.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(Constances.TAG1, item.getUrl());
                        intent.putExtra(Constances.TAG2, item.getTitle());
                        startActivity(intent);
                    }
                });
            }
        };
        Log.e("info", "1---------------->" + Build.VERSION.SDK_INT);
        Log.e("info", "2---------------->" + Build.VERSION_CODES.LOLLIPOP);
        mRecyclerView.setAdapter(mAdapter);
    }
    //获取网络数据，设置adapter
    private void getDatas(String msg,String title)
    {
        mList = new ArrayList<>();
        mList.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constances.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofit.create(GetNews.class).getNews(msg,Constances.KEY)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<News>()
        {
            @Override
            public void onCompleted()
            {
                mAdapter.setData(mList);
            }

            @Override
            public void onError(Throwable e)
            {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(News news)
            {
                mList = news.getResult().getData();
            }
        });
        setTitle(title);
    }

    private void initDrawer()
    {
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawerLayout);
        NavigationView navigation = (NavigationView) this.findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.menu_navigation_top:
                        getDatas("top","头条");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_shehui:
                        getDatas("shehui","社会");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_guonei:
                        getDatas("guonei","国内");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_guoji:
                        getDatas("guoji","国际");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_yule:
                        getDatas("yule","娱乐");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_tiyu:
                        getDatas("tiyu","体育");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_junshi:
                        getDatas("junshi","军事");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_keji:
                        getDatas("keyi","科技");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_caijing:
                        getDatas("caijing","财经");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    case R.id.menu_navigation_shishang:
                        getDatas("shishang","时尚");
                        mDrawerLayout.closeDrawer(Gravity.LEFT,true);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initToolBars()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_black_24dp);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
       switch (item.getItemId())
       {
           case android.R.id.home:
               mDrawerLayout.openDrawer(Gravity.LEFT,true);
               break;
           case R.id.action_settings:
               Toast.makeText(this, "有问题或者想法，小主可以联系我哦！1070138445@qq.com!", Toast.LENGTH_LONG).show();
           default:
               break;
       }

        return super.onOptionsItemSelected(item);
    }

}
