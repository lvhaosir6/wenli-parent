package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Clazz;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/30.
 */
@Data
public class ClazzVo extends Clazz{

    /**
     *  系部名称
     */
    private String departmentName;
    /**
     *  教师名称
     */
    private String teacherName;

    public ClazzVo(Clazz clazz) {
        if (clazz != null) {
            // BaseEntity
            this.setId(clazz.getId());
            this.setCreateTime(clazz.getCreateTime());
            this.setCreateBy(clazz.getCreateBy());
            this.setUpdateTime(clazz.getUpdateTime());
            this.setUpdateBy(clazz.getUpdateBy());
            this.setDelFlag(clazz.getDelFlag());
            // clazz
            this.setName(clazz.getName());
            this.setDepartmentId(clazz.getDepartmentId());
            this.setTeacherId(clazz.getTeacherId());
        }
    }

}
