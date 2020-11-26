package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.pojo.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lvhaosir on 2018/9/19.
 *  用户实体类
 */
@Data
@Entity
@Table(name = "tb_user")
public class User extends BaseEntity {

    /**
     *  用户名
     */
    private String username;
    /**
     *  密码
     */
    private String password;
    /**
     *  昵称
     */
    private String nickName;
    /**
     *  性别 0女 1男 2保密
     */
    private Integer sex;
    /**
     *  邮箱
     */
    private String email;
    /**
     *  联系方式
     */
    private String mobile;
    /**
     *  地址
     */
    private String address;
    /**
     *  头像
     */
    private String avatar = EntityConstant.USER_DEFAULT_AVATAR;
    /**
     *  描述
     */
    private String description;

    /**
     *  状态 默认0正常 -1拉黑
     */
    private Integer status = EntityConstant.USER_STATUS_NORMAL;

    /**
     *  用户类型 0普通用户 1管理员
     */
    private Integer type = EntityConstant.USER_TYPE_NORMAL;

    /**
     *  所属部门id
     */
    private String departmentId;
}
