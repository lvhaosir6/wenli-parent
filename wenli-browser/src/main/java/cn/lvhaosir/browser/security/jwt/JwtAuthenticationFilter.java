package cn.lvhaosir.browser.security.jwt;

import cn.lvhaosir.browser.constant.BrowserConstant;
import cn.lvhaosir.core.constant.CommonConstant;
import cn.lvhaosir.core.exception.WenliException;
import cn.lvhaosir.core.utils.ResponseUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter{

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 请求头中 或者是 参数中
        String header = request.getHeader(CommonConstant.HEADER);
        if (StringUtils.isBlank(header)) {
            // 请求头中没有时，看参数中是否有
            header = request.getParameter(CommonConstant.HEADER);
        }
        if (StringUtils.isBlank(header) || !header.startsWith(CommonConstant.TOKEN_SPLIT)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request,response);
        // 存储 Authentication
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request,response);
    }

    /**
     *  解析请求携带的 token
     * @param request
     * @param response
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(CommonConstant.HEADER);
        if (StringUtils.isNotBlank(token)) {
            // 解析token
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey(CommonConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(token.replace(CommonConstant.TOKEN_SPLIT, ""))
                        .getBody();
                //获取用户名
                String username = claims.getSubject();

                //获取权限
                List<GrantedAuthority> authorities = new ArrayList<>();
                String authority = claims.get(CommonConstant.AUTHORITIES).toString();

                if (StringUtils.isNotBlank(authority)) {
                    // json解析
                    List<String> list = new Gson().fromJson(authority, new TypeToken<List<String>>(){}.getType());for (String ga : list) {
                        authorities.add(new SimpleGrantedAuthority(ga));
                    }
                }
                if (StringUtils.isNotBlank(username)) {
                    //  踩坑提醒 此处password不能为null
                    User principal = new User(username, "", authorities);
                    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                }

            } catch (ExpiredJwtException e) {
                throw new WenliException("登录已失效，请重新登录");
            } catch (Exception e) {
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"解析token错误"));
            }
        }
        return null;
    }
}
