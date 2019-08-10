package cn.itcast.service.impl;

import cn.itcast.dao.PermissionDao;
import cn.itcast.dao.RoleDao;
import cn.itcast.dao.UserDao;
import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        if(user == null){
            return null;
        }
        Set<Role> roles = roleDao.findRoleByUserId(user.getId());
        if(roles == null || roles.size() == 0){
            return user;
        }
        user.setRoles(roles);
        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findPermissionByRoleId(role.getId());
            role.setPermissions(permissions);
        }

        return user;
    }
}
