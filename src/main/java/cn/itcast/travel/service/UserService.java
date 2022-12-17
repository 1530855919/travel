package cn.itcast.travel.service;

import cn.itcast.travel.domain.SmsResult;
import cn.itcast.travel.domain.User;

public interface UserService {

    SmsResult sendSmsCheckCode(String telephone);

    boolean jySmsCheckCode(String telephone,String check);

    boolean jyUserByUserName(String userName);

    int insertUser(User user);

    User findByUserNameAndPass(User user);

}
