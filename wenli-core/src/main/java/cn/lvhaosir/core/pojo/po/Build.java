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
 *  宿舍楼栋 实体类
 *
 */
@Data
@Entity
@Table(name = "tb_build")
public class Build extends BaseEntity{


    /**
     *  楼栋名称
     */
    private String name;

    /**
     *  最高层数
     */
    private Integer maxLayer;


}
