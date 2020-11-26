package cn.lvhaosir.app.service;

import cn.lvhaosir.core.pojo.po.Department;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/12.
 */
public interface DepartmentService {

    /**
     *  获取全部 部门
     * @return
     */
    List<Department> queryAll();

    /**
     *  根据id获取部门名称
     * @param id
     * @return 不存在则返回 无
     */
    String queryNameById(String id);

}
