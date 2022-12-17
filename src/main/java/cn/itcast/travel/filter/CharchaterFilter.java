package cn.itcast.travel.filter;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决全站乱码问题，处理所有的请求
 */
@WebFilter("/*")
public class CharchaterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {
        //将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        //获取请求方法
        String method = request.getMethod();
        //解决post请求中文数据乱码问题
        if (method.equalsIgnoreCase("post")) {
            request.setCharacterEncoding("utf-8");
        }

        // 告诉浏览器允许所有的域访问
        // 注意 * 不能满足带有cookie的访问,Origin 必须是全匹配
        // resp.addHeader("Access-Control-Allow-Origin", "*");
        // 解决办法通过获取Origin请求头来动态设置
        String origin = request.getHeader("Origin");
        if (StringUtils.hasText(origin)) {
            response.addHeader("Access-Control-Allow-Origin", origin);
        }
        // 允许带有cookie访问
        response.addHeader("Access-Control-Allow-Credentials", "true");

        // 告诉浏览器允许跨域访问的方法
        response.addHeader("Access-Control-Allow-Methods", "*");

        // 告诉浏览器允许带有Content-Type,header1,header2头的请求访问
        // resp.addHeader("Access-Control-Allow-Headers", "Content-Type,header1,header2");
        // 设置支持所有的自定义请求头
        String headers = request.getHeader("Access-Control-Request-Headers");
        if (StringUtils.hasText(headers)) {
            response.addHeader("Access-Control-Allow-Headers", headers);
        }

        // 告诉浏览器缓存OPTIONS预检请求1小时,避免非简单请求每次发送预检请求,提升性能
        response.addHeader("Access-Control-Max-Age", "3600");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}