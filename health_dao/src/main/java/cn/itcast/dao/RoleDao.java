package cn.itcast.dao;

import cn.itcast.pojo.Role;
import java.util.Set;

public interface RoleDao {

    /**
     * 根据用户id查询角色
     * @param id
     * @return
     */
    Set<Role> findRoleByUserId(Integer id);
}
