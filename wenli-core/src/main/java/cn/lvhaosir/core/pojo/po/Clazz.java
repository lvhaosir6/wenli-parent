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
 *  班级实体类
 *
 */
@Data
@Entity
@Table(name = "tb_class")
public class Clazz extends BaseEntity {

    /**
     * 班级名称
     */
    private String name;
    /**
     * 对应系部编号
     */
    private String departmentId;
    /**
     * 所属教师编号（用户编号）
     */
    private String teacherId;
}