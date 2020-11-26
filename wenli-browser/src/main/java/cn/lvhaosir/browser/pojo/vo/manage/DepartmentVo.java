package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.dao.DepartmentDao;
import cn.lvhaosir.core.pojo.po.Department;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Data
public class DepartmentVo extends Department {

    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    public DepartmentVo(Department department) {
        if (department != null) {
            // BaseEntity
            this.setId(department.getId());
            this.setCreateTime(department.getCreateTime());
            this.setCreateBy(department.getCreateBy());
            this.setUpdateTime(department.getUpdateTime());
            this.setUpdateBy(department.getUpdateBy());
            this.setDelFlag(department.getDelFlag());
            // department
            this.setTitle(department.getTitle());
            this.setIsParent(department.getIsParent());
            this.setParentId(department.getParentId());
            this.setSortOrder(department.getSortOrder());
            this.setStatus(department.getStatus());

        }
    }

}
