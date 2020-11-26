package cn.lvhaosir.app.security;

import cn.lvhaosir.app.security.authentication.AppAuthenticationFilter;
import cn.lvhaosir.app.security.authentication.AppAuthenticationProvider;
import cn.lvhaosir.app.security.jwt.JwtAuthenticationFilter;
import cn.lvhaosir.app.security.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@Configuration
public class AppAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFailureHandler appAuthenticationFailureHandle;

    @Autowired
    private AuthenticationSuccessHandler appAuthenticationSuccessHandle;

    @Autowired
    private AppUserDetailService appUserDetailServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AppAuthenticationFilter appAuthenticationFilter = new AppAuthenticationFilter();
        appAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        appAuthenticationFilter.setAuthenticationSuccessHandler(appAuthenticationSuccessHandle);
        appAuthenticationFilter.setAuthenticationFailureHandler(appAuthenticationFailureHandle);

        AppAuthenticationProvider appAuthenticationProvider = new AppAuthenticationProvider();
        appAuthenticationProvider.setAppUserDetailsService(appUserDetailServiceImpl);
        appAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        http.authenticationProvider(appAuthenticationProvider)
                .addFilterAfter(appAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .loginPage("/needLogin")
                .and()
                .authorizeRequests()
                .antMatchers("/needLogin","/test").permitAll()
                .anyRequest()
                .authenticated()
                .and();
        http.csrf().disable()
                // 前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 添加JWT过滤器
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 声明 BCryptPasswordEncoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
