package cn.lvhaosir.app.security.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public class AppUserDetails extends AppUser implements UserDetails {


    public AppUserDetails(AppUser user) {
        if(user!=null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
