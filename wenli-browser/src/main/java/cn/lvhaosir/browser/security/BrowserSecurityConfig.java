package cn.lvhaosir.browser.security;

import cn.lvhaosir.browser.security.handler.AuthenticationFailureHandler;
import cn.lvhaosir.browser.security.handler.AuthenticationSuccessHandler;
import cn.lvhaosir.browser.security.jwt.JwtAuthenticationFilter;
import cn.lvhaosir.browser.security.handler.RestAccessDeniedHandler;
import cn.lvhaosir.browser.security.permission.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Created by lvhaosir on 2018/9/16.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                    .loginPage("/needLogin")
                    .loginProcessingUrl("/login")
                    .permitAll()
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)
                    .and()
                .authorizeRequests()
                     .antMatchers("/ws/**","/common/ip/info","/druid/**","/**/*.js","/**/*.css","/**/*.png","/**/*.ico").permitAll()
                    // 任何请求
                    .anyRequest()
                     // 需要身份认证
                    .authenticated()
                    .and()
                // 关闭跨站请求防护
                .csrf().disable()
                // 前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   .and()
                // 自定义权限拒绝处理类
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                    .and()
                // 添加自定义权限过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 添加JWT过滤器
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 声明一个 BCryptPasswordEncoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
