package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.pojo.vo.manage.StudentVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.core.pojo.po.Student;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/18.
 */
public interface StudentService {

    /**
     *  分页查询，多条件搜索
     * @param pageVo
     * @return
     */
    Page<StudentVo> queryPageList(PageVo pageVo, Student student, DateSearchVo dateSearchVo);

    /**
     *  获取全部
     * @return
     */
    List<StudentVo> queryAll();

    /**
     *  修改，除status外
     * @param id
     * @param cardId
     * @param name
     * @param departmentId
     * @param classId
     * @param buildId
     * @param roomId
     * @return
     */
    void update(String id, String cardId, String name, String departmentId, String classId, String buildId, String roomId);

    /**
     *  根据学号查询学生信息
     * @param cardId
     * @return
     */
    Student queryByCardId(String cardId);

    /**
     *  新增学生信息 , 将学生信息改为在校
     * @param student
     */
    void save(Student student);

    /**
     *  重置密码为 111
     * @param id
     * @return
     */
    void resetPassword(String id);

    /**
     *  修改学生的状态
     * @param id
     * @param status
     */
    void updateStatus(String id, Integer status);

    /**
     *  根据 班级编号 classId 查询
     * @param classId
     * @return
     */
    List<Student> queryByClassId(String classId);

    /**
     *  根据id查询学生信息
     * @param id
     * @return
     */
    Student queryById(String id);

    /**
     *  修改该学生为该宿舍寝室长
     * @param id 学生id
     */
    void updateRoomChiefById(String id);

    List<Student> queryByRoomId(String roomId);

    /**
     *  根据id查询学生名称
     * @param id
     * @return 有结果则返回 学生名称，没有则返回：无
     */
    String queryNameById(String id);
}
