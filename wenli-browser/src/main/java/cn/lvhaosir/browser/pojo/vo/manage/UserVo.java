package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Permission;
import cn.lvhaosir.core.pojo.po.Role;
import cn.lvhaosir.core.pojo.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@Data
public class UserVo extends User {

    @ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;

    @ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles;

    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions;


    public UserVo() {}

    public UserVo(User user) {
        if (user != null) {
            // BaseEntity
            this.setId(user.getId());
            this.setCreateTime(user.getCreateTime());
            this.setCreateBy(user.getCreateBy());
            this.setUpdateTime(user.getUpdateTime());
            this.setUpdateBy(user.getUpdateBy());
            this.setDelFlag(user.getDelFlag());
            // user
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setNickName(user.getNickName());
            this.setSex(user.getSex());
            this.setEmail(user.getEmail());
            this.setMobile(user.getMobile());
            this.setAddress(user.getAddress());
            this.setAvatar(user.getAvatar());
            this.setDescription(user.getDescription());
            this.setStatus(user.getStatus());
            this.setType(user.getType());
            this.setDepartmentId(user.getDepartmentId());
        }
    }

}
