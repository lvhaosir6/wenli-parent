package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.WeekText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/14.
 */
public interface WeekTextDao extends JpaRepository<WeekText,String>,JpaSpecificationExecutor<WeekText> {

    /**
     *  根据 roomId 查询
     * @param roomId
     * @return
     */
    List<WeekText> findByRoomId(String roomId);

    /**
     *  roomId 查询 和 status 查询
     * @param roomId
     * @param status
     * @return
     */
    List<WeekText> findByRoomIdAndStatus(String roomId,Integer status);

    @Modifying
    @Query("update WeekText wt set wt.teacherReturnText=:teacherReturnText , wt.returnTime=now() , wt.status=:status where wt.id=:id")
    void updateTeacherReturnText(@Param("id") String id, @Param("teacherReturnText") String teacherReturnText, @Param("status") Integer status);


    /**
     *  根据id修改status
     * @param id
     * @param status
     */
    @Modifying
    @Query("update WeekText wt set wt.status=:status where wt.id=:id")
    void updateStatus(@Param("id") String id, @Param("status") Integer status);

}
