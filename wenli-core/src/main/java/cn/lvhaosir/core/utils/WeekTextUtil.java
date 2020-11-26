package cn.lvhaosir.core.utils;

/**
 * Created by lvhaosir on 2018/10/26.
 */
public class WeekTextUtil {
    /**
     *  数字对应的状态
     */
    private static final int ZERO_NUM = 0;
    private static final String NONE_STATUS = "没有";
    private static final int ONE_NUM = 1;
    private static final String GOOD_STATUS = "很好";
    private static final int TWO_NUM = 2;
    private static final String COMMON_STATUS = "一般";
    private static final int THREE_NUM = 3;
    private static final String BAD_STATUS = "很差";
    private static final int FOUR_NUM = 4;
    private static final String LESS_STATUS = "很少";
    private static final int FIVE_NUM = 5;
    private static final String MORE_STATUS = "很多";
    private static final int SIX_NUM = 6;
    private static final String DOWN_STATUS = "有且情绪低落";
    private static final int SEVEN_NUM = 7;
    private static final String NORMAL_STATUS = "有且情绪正常";

    /**
     *  获取状态名称
     * @return
     */
    public static String getStatusName(Integer status) {
        switch (status) {
            case ZERO_NUM: return NONE_STATUS;
            case ONE_NUM: return GOOD_STATUS;
            case TWO_NUM: return COMMON_STATUS;
            case THREE_NUM: return BAD_STATUS;
            case FOUR_NUM: return LESS_STATUS;
            case FIVE_NUM: return MORE_STATUS;
            case SIX_NUM: return DOWN_STATUS;
            case SEVEN_NUM: return NORMAL_STATUS;
            default:return NONE_STATUS;
        }
    }
}
