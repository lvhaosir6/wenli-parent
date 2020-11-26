package cn.lvhaosir.browser.constant;

/**
 * Created by lvhaosir on 2018/9/17.
 */
public interface BrowserConstant {

    /**
     *  限制用户登录错误次数（最大错误次数）
     */
    Integer loginErrorMaxCount = 10;

    /**
     *  超过错误次数后，多少分钟才能进行重试
     */
    Integer loginErrorAfterTime = 10;

    /**
     *  登录错误剩余次数为此值时，提示错误次数警告
     */
    Integer loginErrorWarnCount = 3;


    /**
     * ====================== 以上为可配置项 ======================
     */

    /**
     *  记录错误次数的key
     */
    String LOGIN_ERROR_COUNT = "loginErrorCount:";

    /**
     *  记录超时时间的 key
     */
    String LOGIN_ERROR_AFTER_TIME = "loginErrorAfterTime:";
    /**
     *  记录是否超过最大错误次数的key
     */
    String LOGIN_ERROR_MAX_COUNT_FLAG = "loginErrorMaxCountFlag:";





}
