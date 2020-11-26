package cn.lvhaosir.app.service;

import cn.lvhaosir.core.pojo.po.Build;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/12.
 */
public interface BuildService {

    /**
     *  查询全部
     * @return
     */
    List<Build> queryAll();

    /**
     *  根据id查询出楼栋名称
     * @param id
     * @return 如果不存在则返回为 ：无
     */
    String queryNameById(String id);
}
