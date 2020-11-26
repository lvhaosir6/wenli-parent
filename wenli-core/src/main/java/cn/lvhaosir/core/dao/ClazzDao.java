package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/14.
 */
public interface ClazzDao extends JpaRepository<Clazz,String>,JpaSpecificationExecutor<Clazz> {

    /**
     *  根据 departmentId 查询
     * @param departmentId
     * @return
     */
    List<Clazz> findByDepartmentId(String departmentId);


    /**
     *  根据 teacherId 查询
     * @param teacherId
     * @return
     */
    List<Clazz> findByTeacherId(String teacherId);


}
