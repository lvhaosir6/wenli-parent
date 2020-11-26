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

/**
 * Created by lvhaosir on 2018/9/14.
 *
 *  宿舍 实体类
 */
@Data
@Entity
@Table(name = "tb_room")
public class Room extends BaseEntity{

    /**
     *  宿舍名称
     */
    private String name;

    /**
     *  对应楼栋编号
     */
    private String buildId;
    /**
     *  对应系部编号
     */
    private String departmentId;
    /**
     *  对应班级编号
     */
    private String classId;
    /**
     *  对应教师编号
     */
    private String teacherId;
    /**
     *  对应寝室长编号
     */
    private String studentId;

}
