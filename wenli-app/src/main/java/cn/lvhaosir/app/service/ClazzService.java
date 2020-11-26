package cn.lvhaosir.app.service;

import cn.lvhaosir.app.pojo.vo.ClazzRooms;
import cn.lvhaosir.core.pojo.po.Clazz;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public interface ClazzService {

    /**
     *  根据系部ID查询所属所有班级
     * @param departmentId
     * @return
     */
    List<Clazz> queryByDepartmentId(String departmentId);

    /**
     *  根据教师ID查询其管理的所有班级
     * @param teacherId
     * @return
     */
    List<Clazz> queryByTeacherId(String teacherId);
    /**
     *  根据教师ID 获取该教师管理的所有班级的所有宿舍
     */
    List<ClazzRooms> queryClassRooms(String teacherId);

    /**
     *  根据id查询名称
     * @param id
     * @return 如果不存在则返回：无
     */
    String queryNameById(String id);

}
