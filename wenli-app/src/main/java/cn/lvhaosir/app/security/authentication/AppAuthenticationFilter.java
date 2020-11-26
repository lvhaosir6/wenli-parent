package cn.lvhaosir.app.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@Slf4j
public class AppAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String WENLI_FORM_USERNAME_KEY = "username";
    public static final String WENLI_FORM_PASSWORD_KEY = "password";
    public static final String WENLI_FORM_LOGIN_TYPE_KEY = "loginType";

    // 表示只处理post请求
    private boolean postOnly = true;

    private String usernameParameter = WENLI_FORM_USERNAME_KEY;
    private String passwordParameter = WENLI_FORM_PASSWORD_KEY;
    private String logintypeParamter = WENLI_FORM_LOGIN_TYPE_KEY;



    public AppAuthenticationFilter() {
        super(new AntPathRequestMatcher("/appLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String loginType = obtainLoginType(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if (loginType == null) {
            loginType = "";
        }




        username = username.trim();
        password = password.trim();
        loginType = loginType.trim();

        AppAuthenticationToken authRequest = new AppAuthenticationToken(username,password,loginType);

        setDetails(request,authRequest);

        AuthenticationManager authenticationManager = this.getAuthenticationManager();

        if (authenticationManager == null) {
            log.error("AppAuthenticationFilter 中 authenticationManager 为空");
        }

        return authenticationManager.authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     *  获取用户名
     * @param request
     * @return
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    /**
     *  获取密码
     * @param request
     * @return
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    /**
     *  获取登录类型
     * @param request
     * @return
     */
    protected String obtainLoginType(HttpServletRequest request) {
        return request.getParameter(logintypeParamter);
    }




}
