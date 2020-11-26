package cn.lvhaosir.app.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@Data
public class ClazzRooms {

    private String classId;

    private String className;

    private String teacherId;
    /**
     *  该班级下的所有宿舍
     */
    private List<RoomVo> roomVoList;

}
