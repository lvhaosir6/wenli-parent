package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.ClazzVo;
import cn.lvhaosir.core.pojo.po.Clazz;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/20.
 */
public interface ClazzService {

    /**
     *  根据 系部Id 查询
     * @param departmentId
     * @return
     */
    List<Clazz> queryByDepartmentId(String departmentId);

    /**
     *  查询全部
     * @return
     */
    List<Clazz> queryAllList();

    /**
     *  根据id查询名称
     * @param id
     * @return 如果不存在则返回：无
     */
    String queryNameById(String id);

    /**
     *  分页查询所有
     * @param pageVo
     * @param clazz
     * @return
     */
    Page<ClazzVo> queryPageList(PageVo pageVo, Clazz clazz);

    void saveOrUpdate(Clazz clazz);

    boolean delByIds(String[] ids);

}
