package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/14.
 */
public interface StudentDao extends JpaRepository<Student,String>,JpaSpecificationExecutor<Student> {

    /**
     *  根据 cardId 查询
     * @param cardId
     * @return
     */
    Student findByCardId(String cardId);

    /**
     *  根据 classId 查询
     * @param classId
     * @return
     */
    List<Student> findByClassId(String classId);

    /**
     *  根据 roomId 查询
     * @param roomId
     * @return
     */
    List<Student> findByRoomId(String roomId);

    /**
     *  修改学生的信息，并将 status 设置为 1 （在校）
     * @param id
     * @param departmentId
     * @param buildId
     * @param roomId
     * @param classId
     */
    @Modifying
    @Query("update Student s set s.departmentId=:departmentId , s.buildId=:buildId, s.roomId=:roomId , s.classId=:classId , s.status=1 where s.id=:id ")
    void updateByRegister(@Param("id") String id, @Param("departmentId") String departmentId,@Param("buildId") String buildId, @Param("roomId") String roomId, @Param("classId") String classId);

    /**
     *  修改学生的信息，并将 status 设置为 1 （在校）
     * @param id
     * @param cardId
     * @param name
     * @param departmentId
     * @param classId
     * @param buildId
     * @param roomId
     */
    @Modifying
    @Query("update Student s set s.cardId=:cardId , s.name=:name, s.departmentId=:departmentId , s.buildId=:buildId, s.roomId=:roomId , s.classId=:classId , s.status=1 where s.id=:id ")
    void updateByRegister(@Param("id") String id, @Param("cardId") String cardId, @Param("name") String name, @Param("departmentId") String departmentId,@Param("classId") String classId, @Param("buildId") String buildId, @Param("roomId") String roomId );

    /**
     *  修改学生的status（状态）
     * @param id
     * @param status -1（禁用） 0（未注册）1（在校）
     */
    @Modifying
    @Query("update Student s set s.status=:status where s.id=:id")
    void updateStatus(@Param("id") String id, @Param("status") Integer status);

    /**
     *  修改学生密码
     * @param id
     * @param password
     */
    @Modifying
    @Query("update Student s set s.password=:password where s.id=:id ")
    void updatePassword(@Param("id") String id,@Param("password") String password);
}
