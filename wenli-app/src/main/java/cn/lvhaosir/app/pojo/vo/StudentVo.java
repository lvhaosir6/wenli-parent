package cn.lvhaosir.app.pojo.vo;

import cn.lvhaosir.core.pojo.po.Student;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/13.
 */
@Data
public class StudentVo extends Student {

    /**
     *  系部名称
     */
    private String departmentName;

    /**
     *  班级名称
     */
    private String className;
    /**
     *  宿舍名称
     */
    private String roomName;

    /**
     *  楼栋名称
     */
    private String buildName;

    public StudentVo(Student student) {
        if (student != null) {
            // BaseEntity
            this.setId(student.getId());
            this.setCreateBy(student.getCreateBy());
            this.setCreateTime(student.getCreateTime());
            this.setUpdateBy(student.getUpdateBy());
            this.setUpdateTime(student.getUpdateTime());
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

    public String print() {
        return "【学号:"+this.getCardId()+",名字:"+this.getName()+",系部:"+this.getDepartmentName()+",班级:"+this.getClassName()+",宿舍:"+this.getBuildName()+this.getRoomName()+"】";
    }

}
