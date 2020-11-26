package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.pojo.vo.manage.WeekTextVo;
import org.springframework.data.domain.Page;

/**
 * Created by lvhaosir on 2018/10/26.
 */
public interface WeekTextService {

    Page<WeekTextVo> queryPageList(PageVo pageVo, WeekTextVo weekTextVo, DateSearchVo dateSearchVo);

    /**
     *  根据id删除
     * @param ids
     */
    void delByIds(String[] ids);
}
