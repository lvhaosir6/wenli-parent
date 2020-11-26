package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.core.pojo.po.Build;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/18.
 */
public interface BuildService {

    /**
     *  分页查询所有
     * @param pageVo
     * @return
     */
    Page<Build> queryPageBuildList(PageVo pageVo);

    /**
     *  更新或新增
     * @param build
     * @return
     */
    Build updateOrSave(Build build);

    /**
     *  根据 id 删除
     * @param ids
     * @return
     */
    boolean delByIds(String[] ids);

    /**
     *  查询全部
     * @return
     */
    List<Build> queryAllList();

    Build queryById(String id);

    /**
     *  根据id查询出楼栋名称
     * @param id
     * @return 如果不存在则返回为 ：无
     */
    String queryNameById(String id);

}
