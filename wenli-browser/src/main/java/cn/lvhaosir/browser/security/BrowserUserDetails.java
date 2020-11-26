package cn.lvhaosir.browser.security;

import cn.lvhaosir.browser.pojo.vo.manage.UserVo;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.pojo.po.Permission;
import cn.lvhaosir.core.pojo.po.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
public class BrowserUserDetails extends UserVo implements UserDetails {

    public BrowserUserDetails(UserVo userVo) {
        if (userVo != null) {
            this.setUsername(userVo.getUsername());
            this.setPassword(userVo.getPassword());
            this.setStatus(userVo.getStatus());
            this.setRoles(userVo.getRoles());
            this.setPermissions(userVo.getPermissions());
        }
    }

    /**
     * 添加用户拥有的权限和角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        List<Permission> permissions = this.getPermissions();
        // 添加请求权限
        if (permissions != null && permissions.size() > 0) {
            for (Permission permission : permissions) {
                String title = permission.getTitle();
                if (EntityConstant.PERMISSION_OPERATION.equals(permission.getType())
                        && StringUtils.isNotBlank(title)
                        && StringUtils.isNotBlank(permission.getPath()) ) {
                    authorityList.add(new SimpleGrantedAuthority(title));
                }

            }
        }
        // 添加角色
        List<Role> roles = this.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role r : roles) {
                if (StringUtils.isNotBlank(r.getName())) {
                    authorityList.add(new SimpleGrantedAuthority(r.getName()));
                }
            }
        }
        return authorityList;
    }

    /**
     * 账户不过期？
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * 不禁用？
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * 密码不过期？
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
