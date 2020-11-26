package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Room;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/11/10.
 */
@Data
public class RoomVo extends Room {

    private String buildName;

    private String departmentName;

    private String className;

    private String studentName;
    
    public RoomVo(Room room) {
        if (room != null) {
            // BaseEntity
            this.setId(room.getId());
            this.setCreateTime(room.getCreateTime());
            this.setCreateBy(room.getCreateBy());
            this.setUpdateTime(room.getUpdateTime());
            this.setUpdateBy(room.getUpdateBy());
            this.setDelFlag(room.getDelFlag());
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
