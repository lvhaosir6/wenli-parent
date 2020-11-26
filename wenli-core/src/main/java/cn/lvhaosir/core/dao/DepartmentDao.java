package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/14.
 */
public interface DepartmentDao extends JpaRepository<Department,String> {

    /**
     *  根据 parentId 查询，sortOrder 排序
     * @param parentId
     * @return
     */
    List<Department> findByParentIdOrderBySortOrder(String parentId);

    /**
     *  根据 parentId 和 status 查询 ，sortOrder 排序
     * @param parentId
     * @param status
     * @return
     */
    List<Department> findByParentIdAndStatusOrderBySortOrder(String parentId,Integer status);



}
