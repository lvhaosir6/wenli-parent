package cn.lvhaosir.app.service;

import cn.lvhaosir.app.pojo.vo.StudentVo;
import cn.lvhaosir.core.pojo.po.Student;
import java.util.List;

/**
 * Created by lvhaosir on 2018/10/13.
 */
public interface StudentService {

    /**
     *  根据学号查询学生信息
     * @param cardId
     * @return
     */
    Student queryByCardId(String cardId);

    /**
     *  根据宿舍Id 查询宿舍成员
     * @param roomId
     * @return
     */
    List<StudentVo> queryByRoomId(String roomId);


    /**
     *  学生注册，修改系部班级宿舍信息
     * @param id
     * @param departmentId
     * @param buildId
     * @param roomId
     * @param classId
     */
    void updateByRegister(String id, String departmentId, String buildId, String roomId,String classId);

    /**
     *  修改密码
     * @param id
     * @param password
     * @param newPass
     * @return
     */
    Student updatePassword(String id, String password, String newPass);

    /**
     *  重置密码
     * @param id
     * @return
     */
    void resetPassword(String id);

    StudentVo queryByUsername(String username);

}
