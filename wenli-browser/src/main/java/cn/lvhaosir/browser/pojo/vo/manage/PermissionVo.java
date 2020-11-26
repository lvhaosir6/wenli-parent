package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Permission;
import lombok.Data;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@Data
public class PermissionVo extends Permission {

    private List<PermissionVo> children;

    private List<String> permTypes;

    private Boolean expand = true;

    private Boolean checked = false;

    private Boolean selected = false;

    public PermissionVo(Permission permission) {
        if (permission != null) {
            // BaseEntity
            this.setId(permission.getId());
            this.setCreateTime(permission.getCreateTime());
            this.setCreateBy(permission.getCreateBy());
            this.setUpdateTime(permission.getUpdateTime());
            this.setUpdateBy(permission.getUpdateBy());
            this.setDelFlag(permission.getDelFlag());
            // permission
            this.setName(permission.getName());
            this.setTitle(permission.getTitle());
            this.setType(permission.getType());
            this.setComponent(permission.getComponent());
            this.setPath(permission.getPath());
            this.setUrl(permission.getUrl());
            this.setButtonType(permission.getButtonType());
            this.setIcon(permission.getIcon());
            this.setStatus(permission.getStatus());
            this.setParentId(permission.getParentId());
            this.setLevel(permission.getLevel());
            this.setSortOrder(permission.getSortOrder());
            this.setDescription(permission.getDescription());
        }
    }


}
