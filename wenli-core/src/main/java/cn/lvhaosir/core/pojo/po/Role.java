package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.pojo.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lvhaosir on 2018/9/19.
 */
@Data
@Entity
@Table(name = "tb_role")
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名 以ROLE_开头")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "备注")
    private String description;

}
