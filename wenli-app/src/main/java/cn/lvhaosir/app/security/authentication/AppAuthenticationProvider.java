package cn.lvhaosir.app.security.authentication;

import cn.lvhaosir.app.security.service.AppUserDetailService;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by lvhaosir on 2018/10/12.
 */
public class AppAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder;

    private AppUserDetailService appUserDetailServiceImpl;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AppAuthenticationToken authenticationToken = (AppAuthenticationToken) authentication;

        String username = (String) authenticationToken.getPrincipal();
        String loginType  = (String) authenticationToken.getType();


        UserDetails userDetails = appUserDetailServiceImpl.loadUserByUsername(username,loginType);

        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名/密码无效");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("用户已被禁用");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }

        // 检查密码
        additionalAuthenticationChecks(userDetails,authenticationToken);

        AppAuthenticationToken authenticationResult = new AppAuthenticationToken(userDetails,userDetails.getPassword(),loginType,userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AppAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public void setAppUserDetailsService(AppUserDetailService appUserDetailsService) {
        this.appUserDetailServiceImpl = appUserDetailsService;
    }

    /**
     *  密码检查
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  AppAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
