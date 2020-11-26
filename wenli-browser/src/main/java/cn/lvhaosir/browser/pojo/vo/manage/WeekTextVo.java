package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.WeekText;
import cn.lvhaosir.core.utils.WeekTextUtil;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/26.
 */
@Data
public class WeekTextVo extends WeekText {


    /**
     *  楼栋名称
     */
    private String buildName;
    /**
     *  宿舍名称
     */
    private String roomName;

    /**
     *  系部名称
     */
    private String departmentName;


    /**
     *  班级名称
     */
    private String className;
    /**
     *  教师名称
     */
    private String teacherName;
    /**
     *  学校任务完成情况
     */
    private String studyStatusName;
    /**
     *  情绪情况
     */
    private String moodStatusName;

    /**
     *  身体情况
     */
    private String healthStatusName;
    /**
     *  彻夜未归情况
     */
    private String returnRoomStatusName;

    /**
     *  休息情况
     */
    private String sleepStatusName;

    /**
     *  消费情况
     */
    private String consumeStatusName;
    /**
     *  失恋情况
     */
    private String loveStatusName;

    /**
     *  该周记状态
     */
    private String statusName;


    public WeekTextVo(WeekText weekText) {
        if (weekText != null) {
            // baseEntity
            this.setId(weekText.getId());
            this.setCreateTime(weekText.getCreateTime());
            this.setCreateBy(weekText.getCreateBy());
            this.setUpdateTime(weekText.getUpdateTime());
            this.setUpdateBy(weekText.getUpdateBy());
            this.setDelFlag(weekText.getDelFlag());
            // weekText
            this.setRoomId(weekText.getRoomId());
            this.setStudyStatus(weekText.getStudyStatus());
            this.setMoodStatus(weekText.getMoodStatus());
            this.setHealthStatus(weekText.getHealthStatus());
            this.setReturnRoomStatus(weekText.getReturnRoomStatus());
            this.setSleepStatus(weekText.getSleepStatus());
            this.setConsumeStatus(weekText.getConsumeStatus());
            this.setLoveStatus(weekText.getLoveStatus());
            this.setConditionText(weekText.getConditionText());
            this.setTeacherReturnText(weekText.getTeacherReturnText());
            this.setReturnTime(weekText.getReturnTime());
            this.setStatus(weekText.getStatus());
            // set Name
            this.setStudyStatusName(WeekTextUtil.getStatusName(weekText.getStudyStatus()));
            this.setMoodStatusName(WeekTextUtil.getStatusName(weekText.getMoodStatus()));
            this.setHealthStatusName(WeekTextUtil.getStatusName(weekText.getHealthStatus()));
            this.setReturnRoomStatusName(WeekTextUtil.getStatusName(weekText.getReturnRoomStatus()));
            this.setSleepStatusName(WeekTextUtil.getStatusName(weekText.getSleepStatus()));
            this.setConsumeStatusName(WeekTextUtil.getStatusName(weekText.getConsumeStatus()));
            this.setLoveStatusName(WeekTextUtil.getStatusName(weekText.getLoveStatus()));
        }
    }

}
