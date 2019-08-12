package cn.itcast.security;

import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(username, password, grantedAuthorityList);
        return userDetails;
    }
}
