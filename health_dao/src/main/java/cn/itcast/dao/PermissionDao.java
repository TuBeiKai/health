package cn.itcast.dao;

import cn.itcast.pojo.Permission;
import java.util.Set;

/**
 * 角色权限
 */
public interface PermissionDao {

    /**
     * 根据角色id查询权限集合
     * @param id
     * @return
     */
    Set<Permission> findPermissionByRoleId(Integer id);
}
