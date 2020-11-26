package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Room;
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
public interface RoomDao extends JpaRepository<Room,String>,JpaSpecificationExecutor<Room> {


    /**
     *  根据 buildId 查询
     * @param buildId
     * @return
     */
    List<Room> findByBuildId(String buildId);

    /**
     *  根据 classId查询
     * @param classId
     * @return
     */
    List<Room> findByClassId(String classId);

    /**
     *  根据 teacherId 查询
     * @param teacherId
     * @return
     */
    List<Room> findByTeacherId(String teacherId);

    @Modifying
    @Query("update Room r set r.studentId=:studentId where r.id=:id")
    void updateStudentId(@Param("id") String id, @Param("studentId") String studentId);

    @Modifying
    @Query("update Room r set  r.departmentId=:departmentId , r.classId=:classId , r.teacherId=:teacherId , r.studentId=:studentId where r.id=:id")
    void updateRegisterById(@Param("id") String id,
                        @Param("departmentId") String departmentId ,
                        @Param("classId") String classId ,
                        @Param("teacherId") String teacherId ,
                        @Param("studentId") String studentId);

}
