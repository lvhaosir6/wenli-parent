package cn.lvhaosir.browser.security;

import cn.lvhaosir.browser.constant.BrowserConstant;
import cn.lvhaosir.browser.exception.LoginFailLimitException;
import cn.lvhaosir.browser.pojo.vo.manage.UserVo;
import cn.lvhaosir.browser.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by lvhaosir on 2018/9/16.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取记录是否超过最大错误次数的key
        String flagKey = BrowserConstant.LOGIN_ERROR_MAX_COUNT_FLAG + username;
        String value = redisTemplate.opsForValue().get(flagKey);
        // 获取过期剩余时间
        Long timeLave = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        // 当获取到登录失败错误过多时，抛出异常
        if (StringUtils.isNotBlank(value)) {
            //超过限制次数
            throw new LoginFailLimitException("登录错误次数超过限制，请"+timeLave+"分钟后再试");
        }
        UserVo userVo = userService.queryByUsername(username);

        return new BrowserUserDetails(userVo);
    }

}
