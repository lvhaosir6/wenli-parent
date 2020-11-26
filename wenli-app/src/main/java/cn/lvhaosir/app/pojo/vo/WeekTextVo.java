package cn.lvhaosir.app.pojo.vo;

import cn.lvhaosir.core.pojo.po.WeekText;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@Data
public class WeekTextVo extends WeekText {

    private String buildName;

    private String roomName;

    public WeekTextVo(WeekText weekText) {
        if (weekText != null) {
            // BaseEntity
            this.setId(weekText.getId());
            this.setCreateBy(weekText.getCreateBy());
            this.setCreateTime(weekText.getCreateTime());
            this.setUpdateBy(weekText.getUpdateBy());
            this.setUpdateTime(weekText.getUpdateTime());
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
        }

    }


}
