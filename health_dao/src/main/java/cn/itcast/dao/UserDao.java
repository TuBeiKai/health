package cn.itcast.dao;

import cn.itcast.pojo.User;

public interface UserDao {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

}
