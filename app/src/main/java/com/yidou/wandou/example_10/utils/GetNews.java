package com.yidou.wandou.example_10.utils;

import com.yidou.wandou.example_10.bean.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/12.
 */

public interface GetNews
{
    @GET("index")
    Observable<News> getNews(@Query("type") String type, @Query("key") String key);
}
