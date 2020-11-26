package cn.lvhaosir.app.service;

import cn.lvhaosir.app.pojo.vo.RoomVo;
import cn.lvhaosir.core.pojo.po.Room;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public interface RoomService {

    /**
     *  根据楼栋ID查询所属宿舍
     * @param buildId
     * @return
     */
    List<Room> queryByBuildId(String buildId);

    /**
     *  根据RoomId查询出宿舍信息
     * @param id
     * @return
     */
    Room queryById(String id);

    /**
     *  根据id查询出 Vo
     * @param id
     * @return
     */
    RoomVo queryVoById(String id);

    /**
     *  修改寝室长
     * @param id
     * @param studentId
     * @return
     */
    void updateStudentId(String id, String studentId);

    /**
     *  根据 classId 查询出 RoomVoList
     * @param classId
     * @return
     */
    List<RoomVo> queryByClassId(String classId);

    /**
     *  根据 宿舍Id  修改 layer departemntId classId teacherId studentId
     * @param id
     */
    void updateRegisterById(String id,
                         String departmentId ,
                         String classId ,
                         String teacherId ,
                         String studentId);

    /**
     *  根据id查询宿舍名称
     * @param id
     * @return 若不存在则返回：无
     */
    String queryNameById(String id);

    List<Room> queryByTeacherId(String teacherId);

}
