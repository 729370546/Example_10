package com.yidou.wandou.example_10;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity
{
    private WebView mWebView;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initToolBar();
        mWebView = (WebView) this.findViewById(R.id.detail_webView);
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constances.TAG1);
        String title = intent.getStringExtra(Constances.TAG2);
        mWebView.loadUrl(url);
        setTitle(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_shares:
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_collections:
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void initToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
