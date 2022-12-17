package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.WeatherInfo;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.util.HttpURLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteDao routeDao;


/*    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);


        //1、查询总条数
        Map map = new HashMap();
        map.put("cid", cid);
        int totalCount = routeDao.findTotalCount(map);
        if (totalCount == 0) {
            pb.setList(new ArrayList<>());
            pb.setTotalCount(0);
            pb.setTotalPage(0);
            return pb;
        }
        pb.setTotalCount(totalCount);

        //2、分页查询
        int start = (currentPage - 1) * pageSize;//开始的记录数
        map.put("start", start);
        map.put("pageSize", pageSize);
        List<Route> list = routeDao.findByPage(map);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }*/

    @Override
    public PageBean<Route> pageQuery(Integer cid, Integer currentPage, Integer size) {

        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(size);

        Page page = PageHelper.startPage(currentPage, size);
        List<Route> routes = routeDao.findAll(cid);
        //System.out.println(routes.size());


        pageBean.setTotalCount((int)page.getTotal());
        pageBean.setTotalPage((int)page.getPages());
        pageBean.setList(page.getResult());

        List result = page.getResult();
        System.out.println(result);

        return pageBean;
    }

    @Override
    public Route findOneById(int rid) {
        return routeDao.findOneById(rid);
    }

    private static String ak = "6kq5SmQWjDbACdKkIyG5tn4nwToSKv8A";

    @Override
    public WeatherInfo getWeatherInfo(int districtId) {
        String url = "http://api.map.baidu.com/weather/v1/?district_id=" + districtId + "&data_type=all&ak=" + ak;
        String s = HttpURLUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(s, JSONObject.class);
        if (jsonObject.getInteger("status") != null && jsonObject.getInteger("status") == 0) {
            JSONObject result = jsonObject.getJSONObject("result");

            JSONObject now = result.getJSONObject("now");
            JSONArray forecasts = result.getJSONArray("forecasts");
            return new WeatherInfo(now, forecasts);
        }
        return null;
    }

    @Override
    public void updateCount(String rid) {
        routeDao.updateCount(rid);
    }

    @Override
    public List<Route> findList() {
        List<Route> routeList = routeDao.findList();
        return routeList;
    }
}
