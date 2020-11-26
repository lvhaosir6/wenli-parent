package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.manage.DepartmentVo;
import cn.lvhaosir.core.pojo.po.Department;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
public interface DepartmentService {

    /**
     *  根据 parentId ， 查询出 Dto List
     * @param parentId
     * @return
     */
    List<DepartmentVo> queryListVoByParentId(String parentId);


    Department saveOrUpdate(Department department);

    /**
     *  通过id批量删除
     * @param ids
     * @return
     */
    boolean deleteByIds(String[] ids);

    /**
     *  根据id获取部门名称
     * @param id
     * @return 不存在则返回 无
     */
    String queryNameById(String id);

    Department queryById(String id);

}
