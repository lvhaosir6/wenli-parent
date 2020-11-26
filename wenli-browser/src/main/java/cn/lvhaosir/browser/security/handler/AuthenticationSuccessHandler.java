package cn.lvhaosir.browser.security.handler;

import cn.lvhaosir.browser.constant.BrowserConstant;
import cn.lvhaosir.core.annotation.SystemControllerLog;
import cn.lvhaosir.core.constant.CommonConstant;
import cn.lvhaosir.core.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/17.
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SystemControllerLog(description = "系统登录")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Integer tokenExpireTime = CommonConstant.tokenExpireTime;

        Integer saveLoginTime = CommonConstant.saveLoginTime;

        // 判断用户是否勾选 自动登录
        String saveLogin = request.getParameter(CommonConstant.SAVE_LOGIN);
        if (StringUtils.isNotBlank(saveLogin) && Boolean.valueOf(saveLogin)) {
            // 如果勾选，token过期时间变为 7 天
            tokenExpireTime = saveLoginTime * 60 * 24;
        }
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> list = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority g : list){
            authorities.add(g.getAuthority());
        }
        // 生成JWT
        String token = Jwts.builder()
                // 放入用户名
                .setSubject(username)
                // 自定义属性 放入用户拥有请求权限
                .claim(CommonConstant.AUTHORITIES, objectMapper.writeValueAsString(authorities))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512,CommonConstant.JWT_SIGN_KEY)
                .compact();
        token = CommonConstant.TOKEN_SPLIT + token;

        ResponseUtil.out(response, ResponseUtil.resultMap(true,200,"登录成功", token));

    }
}
