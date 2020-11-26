package cn.lvhaosir.browser.security.handler;

import cn.lvhaosir.browser.constant.BrowserConstant;
import cn.lvhaosir.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static cn.lvhaosir.core.utils.ResponseUtil.resultMap;

/**
 * Created by lvhaosir on 2018/9/16.
 */
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            recordLoginTime(username);
            // 获取记录错误次数 key
            String key = BrowserConstant.LOGIN_ERROR_COUNT + username;
            // 获取登录已错误的次数
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(value)) {
                value = "0";
            }
            int loginFailCount = Integer.parseInt(value);
            // 剩余错误次数
            int laveLoginCount = BrowserConstant.loginErrorMaxCount - loginFailCount;
            if (laveLoginCount <= BrowserConstant.loginErrorWarnCount && laveLoginCount > 0 ) {
                ResponseUtil.out(response,resultMap(false,500,"用户名或密码错误，还有"+laveLoginCount+"次尝试机会!"));
            } else if (laveLoginCount <= 0) {
                ResponseUtil.out(response, resultMap(false,500,"登录错误次数超过限制，请"+BrowserConstant.loginErrorAfterTime+"分钟后再试!"));
            } else {
                ResponseUtil.out(response, resultMap(false,500,"用户名或密码错误"));
            }

        } else if (exception instanceof DisabledException) {
            // 账号禁用异常
            ResponseUtil.out(response, resultMap(false,500,"账户被禁用，请联系管理员"));
        } else if (exception instanceof InternalAuthenticationServiceException){
            ResponseUtil.out(response, resultMap(false,500,(exception.getMessage())));
        } else {
            ResponseUtil.out(response, resultMap(false,500,"登录失败，其他内部错误"));
        }
    }

    /**
     *  判断用户登录错误次数
     * @param username
     * @return
     */
    public boolean recordLoginTime(String username) {
        // 记录错误次数 key
        String key = BrowserConstant.LOGIN_ERROR_COUNT + username;
        // 记录是否已经达到最大错误 key
        String flagKey = BrowserConstant.LOGIN_ERROR_MAX_COUNT_FLAG + username;
        // 获取已经错误次数
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            // 如果没有值，设置为 0
            value = "0";
        }
        // 获取已经错误次数
        int loginFailTime = Integer.parseInt(value) + 1;
        // 将错误次数存储，并设置过期时间
        stringRedisTemplate.opsForValue().set(key,String.valueOf(loginFailTime), BrowserConstant.loginErrorAfterTime, TimeUnit.MINUTES);
        if (loginFailTime >= BrowserConstant.loginErrorMaxCount) {
            // 如果错误次数 大于或等于 设置的最大错误值
            // 设置 标记值 ，并设置过期时间
            stringRedisTemplate.opsForValue().set(flagKey,"fail", BrowserConstant.loginErrorAfterTime, TimeUnit.MINUTES);
            return false;
        }
        return true;
    }


}
