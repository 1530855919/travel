package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.SmsResult;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.JedisUtil;
import cn.itcast.travel.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    //生成验证码
    @Override
    public SmsResult sendSmsCheckCode(String telephone) {
        //产生四位随机数
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        //调用工具类发送验证码
        //SMSTestUtil.sendSms(telephone, code.toString());
        System.out.println("短信验证码:"+code);
        //将验证码存储在redis中:有效期5分钟
        JedisUtil.getJedis().setex(telephone, 300, code.toString());
        SmsResult smsResult = new SmsResult(1, "短信发送成功", null);
        return smsResult;
    }

    //校验验证码
    @Override
    public boolean jySmsCheckCode(String telephone,String check) {
        if(telephone!=null&&check!=null){
            String code = JedisUtil.getJedis().get(telephone);
            if(code!=null&&code.equals(check)){
                return true;
            }
        }
        return false;
    }

    @Autowired
    private UserDao userDao;

    //校验用户名和密码
    @Override
    public boolean jyUserByUserName(String userName) {
        User user = userDao.findUserByUserName(userName);
        if (user == null) {
        //用户名不存在 - 返回true
            return true;
        }
        //用户名称存在 - 返回false
        return false;
    }

    //添加用户
    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public User findByUserNameAndPass(User user) {
        return userDao.findByUserNameAndPass(user);
    }
}
