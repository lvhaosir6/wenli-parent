package cn.lvhaosir.browser.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Created by lvhaosir on 2018/9/17.
 *  登录失败 超过限制次数，抛出此异常
 */
@Setter
@Getter
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private String message;

    public LoginFailLimitException(String message) {
        super(message);
        this.message = message;
    }


}
