package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.service.BuildService;
import cn.lvhaosir.core.dao.BuildDao;
import cn.lvhaosir.core.pojo.po.Build;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@Service
public class BuildServiceImpl implements BuildService {

    @Autowired
    private BuildDao buildDao;

    @Cacheable(key = "'builds'")
    @Override
    public List<Build> queryAll() {
        return buildDao.findAll();
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return buildDao.findById(id).get().getName();
        }
    }
}
