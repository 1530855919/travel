package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.WeatherInfo;

import java.util.List;

public interface RouteService {

    PageBean<Route> pageQuery(Integer cid, Integer currentPage, Integer size);

    Route findOneById(int rid);

    /**
     * 查询指定区域的天气预报情况
     * @param districtId
     * @return
     */
    WeatherInfo getWeatherInfo(int districtId);

    void updateCount(String rid);

    List<Route> findList();

}
