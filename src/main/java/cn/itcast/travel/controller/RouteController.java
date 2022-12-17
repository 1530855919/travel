package cn.itcast.travel.controller;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteImgService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.SellerService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("route")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private RouteImgService routeImgService;
    @Autowired
    private SellerService sellerService;

    @GetMapping(value = "/pageQuery")
    @ResponseBody
    public PageBean<Route> pageQuery(String cid, String currentPage, String size) {

        //进行参数处理
        int useCurrentPage = 1;
        if (!StringUtils.isEmpty(currentPage)) {
            useCurrentPage = Integer.parseInt(currentPage) >= 1 ? Integer.parseInt(currentPage) : 1;
        }
        int pageSize = 5;
        if (!StringUtils.isEmpty(size)) {
            pageSize = Integer.parseInt(size) >= 1 ? Integer.parseInt(size) : 5;
        }

        //调用service进行分页查询
        PageBean<Route> routePageBean = routeService.pageQuery(Integer.parseInt(cid), useCurrentPage, pageSize);
        return routePageBean;
    }

    @RequestMapping(value = "/findOne",method = RequestMethod.GET)
    @ResponseBody
    public Route findOne(Integer rid) {
        if (rid == null) {
            return new Route();
        }

        //1.根据id去route表中查询route对象
        Route route = routeService.findOneById(rid);
        if (route == null) {
            return new Route();
        }

        //2.根据route的id 查询图片集合信息
        List<RouteImg> routeImg = routeImgService.listRouteImg(rid);
        route.setRouteImgList(routeImg);

        //3.根据route的sid（商家id）查询商家对象
        Seller seller = sellerService.findById(route.getSid());
        route.setSeller(seller);

        //4、获取天气信息
        WeatherInfo weatherInfo = routeService.getWeatherInfo(route.getDistrictid());
        route.setWeatherInfo(weatherInfo);

        //更新本线路的查询次数
        routeService.updateCount(String.valueOf(rid));

        return route;
    }

    //查询查看次数最多的5条数据
    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @ResponseBody
    public List<Route> findList() {
        List<Route> routeList = routeService.findList();
        return routeList;
    }
}
