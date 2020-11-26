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
@Table(name = "tb_role_permission")
public class RolePermission extends BaseEntity{

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    private String permissionId;
}
