package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.pojo.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by lvhaosir on 2018/9/14.
 *  系部 实体类
 */
@Data
@Entity
@Table(name = "tb_department")
public class Department extends BaseEntity{
    /**
     *  名称
     */
    private String title;
    /**
     *  是否为父节点(含子节点) 默认false
     */
    private Boolean isParent = false;

    /**
     *  父id
     */
    private String parentId;

    /**
     *  排序值
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    /**
     *  是否启用 0启用 -1禁用
     */
    private Integer status = EntityConstant.STATUS_NORMAL;

}
