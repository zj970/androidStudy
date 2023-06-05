package com.example.goodweather.service;

import com.example.goodweather.bean.*;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.goodweather.util.Constant.API_KEY;

/**
 * @auther zj970
 * @create 2023-05-20 下午4:46
 */
public interface ApiService {

    /**
     * 搜索城市  模糊搜索，国内范围 返回10条数据
     *
     * @param location 城市名
     * @return NewSearchCityResponse 搜索城市数据返回
     */
    @GET("/v2/city/lookup?key=" + API_KEY + "&range=cn")
    Observable<SearchCityResponse> searchCity(@Query("location") String location);

    /**
     * 实况天气
     *
     * @param location 城市ID
     * @return 返回实况天气数据 NowResponse
     */
    @GET("/v7/weather/now?key=" + API_KEY)
    Observable<NowResponse> nowWeather(@Query("location") String location);

    /**
     * 未来7天的天气
     * @param location
     * @return
     */
    @GET("/v7/weather/7d?key=" + API_KEY)
    Observable<DailyResponse> dailyWeather(@Query("location") String location);

    /**
     * 生活指数
     * @param type
     * @param location
     * @return
     */
    @GET("/v7/indices/1d?key=" + API_KEY)
    Observable<LifestyleResponse> lifestyle(@Query("type") String type, @Query("location") String location);


    /**
     * 每日一图
     * @return
     */
    @GET("/HPImageArchive.aspx?format=js&idx=0&n=1")
    Observable<BingResponse> bing();

    /**
     * 逐小时
     * @param location
     * @return
     */
    @GET("/v7/weather/24h?key=" + API_KEY)
    Observable<HourlyResponse> hourlyWeather(@Query("location") String location);

}
