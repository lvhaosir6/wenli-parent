package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.core.pojo.po.Log;
import org.springframework.data.domain.Page;

/**
 * Created by lvhaosir on 2018/10/7.
 */
public interface LogService {

    /**
     *  分页获取全部
     * @param pageVo
     * @return
     */
    Page<Log> findAll(PageVo pageVo);

    /**
     *  多条件搜索
     * @param key
     * @param dateSearchVo
     * @param pageVo
     * @return
     */
    Page<Log> searchLog(String key, DateSearchVo dateSearchVo, PageVo pageVo);

}
