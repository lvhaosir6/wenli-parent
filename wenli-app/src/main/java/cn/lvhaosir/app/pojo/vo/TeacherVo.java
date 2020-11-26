package cn.lvhaosir.app.pojo.vo;

import cn.lvhaosir.core.pojo.po.User;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/17.
 */
@Data
public class TeacherVo extends User {

    /**
     *  系部名称
     */
    private String departmentName;


    public TeacherVo(User user) {
        if (user != null) {
            // BaseEntity
            this.setId(user.getId());
            this.setCreateBy(user.getCreateBy());
            this.setCreateTime(user.getCreateTime());
            this.setUpdateBy(user.getUpdateBy());
            this.setUpdateTime(user.getUpdateTime());
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
