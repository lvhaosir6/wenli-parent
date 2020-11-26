package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.pojo.vo.ClazzRooms;
import cn.lvhaosir.app.service.ClazzService;
import cn.lvhaosir.app.service.RoomService;
import cn.lvhaosir.core.dao.ClazzDao;
import cn.lvhaosir.core.dao.RoomDao;
import cn.lvhaosir.core.pojo.po.Clazz;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/14.
 */
@Service
public class ClazzServiceImpl implements ClazzService {


    @Autowired
    private ClazzDao clazzDao;
    @Autowired
    private RoomService roomService;

    @Cacheable(key = "'clazzs:'+#departmentId")
    @Override
    public List<Clazz> queryByDepartmentId(String departmentId) {
        return clazzDao.findByDepartmentId(departmentId);
    }

    @Override
    public List<Clazz> queryByTeacherId(String teacherId) {
        return clazzDao.findByTeacherId(teacherId);
    }

    @Override
    public List<ClazzRooms> queryClassRooms(String teacherId) {
        List<ClazzRooms> list = new ArrayList<>();
        // 根据教师ID获取所有管理的班级
        List<Clazz> clazzs = clazzDao.findByTeacherId(teacherId);
        for (Clazz clazz : clazzs) {
            ClazzRooms clazzRooms = new ClazzRooms();
            String classId = clazz.getId();
            clazzRooms.setClassId(classId);
            clazzRooms.setClassName(clazz.getName());
            clazzRooms.setTeacherId(teacherId);
            clazzRooms.setRoomVoList(roomService.queryByClassId(classId));
            list.add(clazzRooms);
        }
        return list;
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return clazzDao.findById(id).get().getName();
        }
    }
}
