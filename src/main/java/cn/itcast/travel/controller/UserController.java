package cn.itcast.travel.controller;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.SmsResult;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 发送手机短信验证验证码
     */
    @GetMapping(value = "/sendSmsCheckCode")
    @ResponseBody
    public SmsResult sendSmsCheckCode(String telephone) {
        return userService.sendSmsCheckCode(telephone);
    }

    //注册
    @PostMapping(value = "/registered")
    @ResponseBody
    public ResultInfo registered(String check, User user)
            throws InvocationTargetException, IllegalAccessException {
        //第一步：校验验证码是否合法
        boolean flag = userService.jySmsCheckCode(user.getTelephone(), check);
        if(!flag){//校验失败
            return new ResultInfo(false,"验证码校验失败");
        }
        //第二步：校验用户名是否存在
        boolean userFlag = userService.jyUserByUserName(user.getUsername());
        if(!userFlag){//校验失败
            return new ResultInfo(false,"此用户名己被注册");
        }
        //第三步：保存用户信息到数据库
        int num = userService.insertUser(user);
        if(num<=0){
            return new ResultInfo(false,"注册失败");
        }
        return new ResultInfo(true);
    }

    //登录
    @PostMapping(value = "/login")
    @ResponseBody
    public ResultInfo login(String check, User user, HttpSession session){
        //校验验证码是否正确
        String check_code = (String) session.getAttribute("CHECK_CODE");
        if(!(check!=null && check.equals(check_code))){
            return new ResultInfo(false, "验证码错误");
        }

        //验证账号密码
        User userNameAndPass = userService.findByUserNameAndPass(user);
        if(userNameAndPass==null){
            return new ResultInfo(false, "账号密码错误");
        }

        //全部成功将用户信息存入session中
        session.setAttribute("user",userNameAndPass);

        return new ResultInfo(true, "登录成功");

    }

    // 展示当前登录用户名
    @GetMapping(value = "/findOne")
    @ResponseBody
    public User findOne(HttpSession session) {
        //从session中查询出用户信息
        User user = (User) session.getAttribute("user");
        return user;
    }

    //安全退出
    @GetMapping(value = "/exit")
    public String exit(HttpSession session) {
        //1、销毁session对象数据
        session.invalidate();

        //2.重定向- 跳转登录页面
        return "redirect:/login.html";
    }
}
