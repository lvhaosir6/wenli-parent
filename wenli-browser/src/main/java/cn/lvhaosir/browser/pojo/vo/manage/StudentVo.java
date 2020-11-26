package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Student;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/18.
 */
@Data
public class StudentVo extends Student {

    private String departmentName;

    private String className;

    private String roomName;
    /**
     *  楼栋名称
     */
    private String buildName;

    public StudentVo(Student student) {
        if (student != null) {
            // BaseEntity
            this.setId(student.getId());
            this.setCreateTime(student.getCreateTime());
            this.setCreateBy(student.getCreateBy());
            this.setUpdateTime(student.getUpdateTime());
            this.setUpdateBy(student.getUpdateBy());
            this.setDelFlag(student.getDelFlag());
            // student
            this.setCardId(student.getCardId());
            this.setName(student.getName());
            this.setPassword(student.getPassword());
            this.setDepartmentId(student.getDepartmentId());
            this.setClassId(student.getClassId());
            this.setRoomId(student.getRoomId());
            this.setStatus(student.getStatus());
            this.setBuildId(student.getBuildId());
        }
    }

}
