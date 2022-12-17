package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    User findUserByUserName(String userName);

    int insertUser(User user);

    User findByUserNameAndPass(User user);

}
