package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Data
public class RoleVo extends Role {

    @ApiModelProperty(value = "拥有权限")
    private List<PermissionVo> permissions;

    public RoleVo(Role role) {
        if (role != null) {
            // BaseEntity
            this.setId(role.getId());
            this.setCreateTime(role.getCreateTime());
            this.setCreateBy(role.getCreateBy());
            this.setUpdateTime(role.getUpdateTime());
            this.setUpdateBy(role.getUpdateBy());
            this.setDelFlag(role.getDelFlag());
            // role
            this.setName(role.getName());
            this.setDefaultRole(role.getDefaultRole());
            this.setDescription(role.getDescription());

        }
    }


}
