package cn.lvhaosir.core.constant;

/**
 * Created by lvhaosir on 2018/9/19.
 */
public interface EntityConstant {

    /**
     * 正常状态
     */
    Integer STATUS_NORMAL = 0;
    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "https://s1.ax1x.com/2018/05/19/CcdVQP.png";
    /**
     * 普通用户
     */
    Integer USER_TYPE_NORMAL = 0;
    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 0;
    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = -1;

    /**
     * 页面类型权限
     */
    Integer PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 1;

    /**
     * 1级菜单
     */
    Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    Integer LEVEL_THREE = 3;

    /**
     * 1级菜单父id
     */
    String PARENT_ID = "0";

    /**
     * 未读
     */
    Integer MESSAGE_STATUS_UNREAD = 0;

    /**
     * 已读
     */
    Integer MESSAGE_STATUS_READ = 1;


    /**
     * 消息发送范围 （发送给全体）
     */
    Integer MESSAGE_RANGE_ALL = 0;

}
