package cn.lvhaosir.app.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public interface AppUserDetailService extends UserDetailsService {

    UserDetails loadUserByUsername(String username,String loginType) throws UsernameNotFoundException;

}
