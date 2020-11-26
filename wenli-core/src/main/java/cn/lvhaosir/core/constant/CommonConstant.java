package cn.lvhaosir.core.constant;

/**
 * Created by lvhaosir on 2018/10/17.
 */
public interface CommonConstant {

    /**
     *  token过期时间 （分钟）
     */
    Integer tokenExpireTime = 60;
    /**
     *  用户选择报错登录状态对应 token 过期时间（天）
     */
    Integer saveLoginTime = 7;


    /**
     *  JWT签名加密key
     */
    String JWT_SIGN_KEY = "lvhaosir";

    /**
     * ====================== 以上为可配置项 ======================
     */

    /**
     *  用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";
    /**
     *  权限参数头
     */
    String AUTHORITIES = "authorities";
    /**
     *  token分割
     */
    String TOKEN_SPLIT = "Bearer ";
    /**
     * token参数头
     */
    String HEADER = "accessToken";

}
