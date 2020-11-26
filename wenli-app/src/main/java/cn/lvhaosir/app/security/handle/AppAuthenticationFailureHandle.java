package cn.lvhaosir.app.security.handle;

import cn.lvhaosir.core.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.lvhaosir.core.utils.ResponseUtil.resultMap;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@Slf4j
@Component("appAuthenticationFailureHandle")
public class AppAuthenticationFailureHandle extends SimpleUrlAuthenticationFailureHandler {


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("登录失败");

        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            ResponseUtil.out(response, resultMap(false,500,"用户名或密码错误"));
        } else if (exception instanceof DisabledException) {
            // 账号禁用异常
            ResponseUtil.out(response, resultMap(false,500,"账户被禁用，请联系管理员"));
        } else {
            ResponseUtil.out(response, resultMap(false,500,"登录失败，其他内部错误"));
        }

    }
}
