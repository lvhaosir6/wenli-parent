package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.pojo.vo.RoomVo;
import cn.lvhaosir.app.service.*;
import cn.lvhaosir.core.dao.BuildDao;
import cn.lvhaosir.core.dao.RoomDao;
import cn.lvhaosir.core.pojo.po.Room;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/14.
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Cacheable(key = "'rooms:'+#buildId")
    @Override
    public List<Room> queryByBuildId(String buildId) {
        return roomDao.findByBuildId(buildId);
    }

    @Override
    public Room queryById(String id) {
        return roomDao.findById(id).get();
    }

    @Override
    public RoomVo queryVoById(String id) {
        return changeVo(this.queryById(id));
    }

    @Transactional
    @Override
    public void updateStudentId(String id, String studentId) {
        roomDao.updateStudentId(id, studentId);
    }

    @Override
    public List<RoomVo> queryByClassId(String classId) {
        return changeVoList(roomDao.findByClassId(classId));
    }

    private List<RoomVo> changeVoList(List<Room> list) {
        List<RoomVo> roomVos = new ArrayList<>();
        for (Room room : list) {
            roomVos.add(changeVo(room));
        }
        return roomVos;
    }

    @Autowired
    private BuildService buildService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private UserService userService;


    private RoomVo changeVo(Room room) {
        RoomVo roomVo = new RoomVo(room);
        // 楼栋名称
        roomVo.setBuildName(buildService.queryNameById(room.getBuildId()));
        // 宿舍名称
        roomVo.setRoomName(this.queryNameById(room.getId()));
        // 系部名称
        roomVo.setDepartmentName(departmentService.queryNameById(room.getDepartmentId()));
        // 班级名称
        roomVo.setClassName(clazzService.queryNameById(room.getClassId()));
        // 教师名称
        roomVo.setTeacherName(userService.queryNameById(room.getTeacherId()));
        return roomVo;
    }

    @Transactional
    @Override
    public void updateRegisterById(String id,  String departmentId, String classId, String teacherId, String studentId) {
        roomDao.updateRegisterById(id, departmentId, classId, teacherId, studentId);
    }

    @Override
    public List<Room> queryByTeacherId(String teacherId) {
        return roomDao.findByTeacherId(teacherId);
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
           return roomDao.findById(id).get().getName();
        }
    }
}
