package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.pojo.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by lvhaosir on 2018/9/19.
 */
@Data
@Entity
@Table(name = "tb_permission")
public class Permission extends BaseEntity {

    @ApiModelProperty(value = "菜单/权限名称")
    private String name;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "类型 0页面 1具体操作")
    private Integer type;

    @ApiModelProperty(value = "前端组件")
    private String component;


    /**
     *  页面路径/资源链接url
     *  不可为空
     */
    @ApiModelProperty(value = "页面路径/资源链接url")
    @Column(nullable = false)
    private String path;

    @ApiModelProperty(value = "网页链接")
    private String url;

    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status = EntityConstant.STATUS_NORMAL;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "说明备注")
    private String description;


}
