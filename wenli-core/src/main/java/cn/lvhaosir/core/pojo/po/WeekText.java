package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.pojo.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by lvhaosir on 2018/9/14.
 *  周记实体类
 */
@Data
@Entity
@Table(name = "tb_week_text")
public class WeekText extends BaseEntity {

    /**
     *  宿舍编号
     */
    private String roomId;
    /**
     *  学习任务完成情况（1：很好 2：一般 3：很差）
     */
    private Integer studyStatus;
    /**
     *  情绪情况（1：很好 2：一般 3：很差）
     */
    private Integer moodStatus;
    /**
     *  身体状态（1：很好 2：一般 3：很差）
     */
    private Integer healthStatus;
    /**
     *  彻夜未归情况（0：没有 4：很少 5：很多）
     */
    private Integer returnRoomStatus;
    /**
     *  休息情况（1：很好 2：一般 3：很差）
     */
    private Integer sleepStatus;
    /**
     *  消费情况（0：没有 4：很少 5：很多）
     */
    private Integer consumeStatus;
    /**
     *  失恋情况（0：没有 6：有且情绪低落 7：有且情绪正常）
     */
    private Integer loveStatus;
    /**
     *  备注返回信息
     */
    private String conditionText;
    /**
     *  教师回复信息
     */
    private String teacherReturnText;
    /**
     *  教师回复时间
     */
    private Date returnTime;

    /**
     *  该周记状态（0：未读 1：已读 2：异常周记）
     */
    private Integer status;

    private String buildId;

    private String departmentId;

    private String classId;

}
