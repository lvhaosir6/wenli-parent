package cn.lvhaosir.app.pojo.vo;

import cn.lvhaosir.core.pojo.po.Room;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@Data
public class RoomVo extends Room {

    /**
     *  楼栋名称
     */
    private String buildName;
    /**
     *  宿舍名称
     */
    private String roomName;

    /**
     *  系部名称
     */
    private String departmentName;

    /**
     *  班级名称
     */
    private String className;

    /**
     *  教师名称
     */
    private String teacherName;


    public RoomVo(Room room) {
        if (room != null) {
            // BaseEntity
            this.setId(room.getId());
            this.setCreateBy(room.getCreateBy());
            this.setCreateTime(room.getCreateTime());
            this.setUpdateBy(room.getUpdateBy());
            this.setUpdateTime(room.getUpdateTime());
            // room
            this.setName(room.getName());
            this.setBuildId(room.getBuildId());
            this.setDepartmentId(room.getDepartmentId());
            this.setClassId(room.getClassId());
            this.setTeacherId(room.getTeacherId());
            this.setStudentId(room.getStudentId());
        }
    }

}
