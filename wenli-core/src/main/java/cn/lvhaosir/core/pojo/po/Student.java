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
 *  学生 实体类
 */
@Data
@Entity
@Table(name = "tb_student")
public class Student extends BaseEntity{

    /**
     *  学号
     */
    private String cardId;
    /**
     *  姓名
     */
    private String name;
    /**
     *  密码
     */
    private String password;
    /**
     *  系部编号
     */
    private String departmentId;
    /**
     *  班级编号
     */
    private String classId;
    /**
     *  楼栋编号
     */
    private String buildId;
    /**
     *  宿舍编号
     */
    private String roomId;

    /**
     *  学生状态
     */
    private Integer status;



}
