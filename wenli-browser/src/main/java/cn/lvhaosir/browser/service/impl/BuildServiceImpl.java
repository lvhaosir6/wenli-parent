package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.BuildService;
import cn.lvhaosir.browser.service.RoomService;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.BuildDao;
import cn.lvhaosir.core.pojo.po.Build;
import cn.lvhaosir.core.pojo.po.Room;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by lvhaosir on 2018/10/18.
 */
@Service
@CacheConfig(cacheNames = "buildCache")
public class BuildServiceImpl implements BuildService {

    @Autowired
    private BuildDao buildDao;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Cacheable(key = "'builds'")
    @Override
    public List<Build> queryAllList() {
        return buildDao.findAll();
    }

    @Cacheable(key = "'buildsPage:'+#pageVo.pageNumber+':'+#pageVo.pageSize+':'+#pageVo.sort+':'+#pageVo.order")
    @Override
    public Page<Build> queryPageBuildList(PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Build> all = buildDao.findAll(pageable);
        return all;
    }

    @CacheEvict(key = "'builds'")
    @Transactional
    @Override
    public Build updateOrSave(Build build) {
        // 手动删除,page页面
        deleteCache();
        Build b = null;
        if (StringUtils.isBlank(build.getId())) {
            // 如果为空，添加
            b = buildDao.save(build);
        } else {
            b = buildDao.saveAndFlush(build);
        }
        return b;
    }

    @Autowired
    private RoomService roomService;

    @CacheEvict(key = "'builds'")
    @Transactional
    @Override
    public boolean delByIds(String[] ids) {
        // 需要判断是否还有人使用这栋楼
        for (String id : ids) {
            List<Room> rooms = roomService.queryByBuildId(id);
            if (rooms != null && rooms.size() > 0) {
                return false;
            }
        }
        for (String id : ids) {
            buildDao.deleteById(id);
        }
        // 如果删除了，就要更新缓存。
        deleteCache();
        return true;
    }

    private void deleteCache() {
        this.deletePageCache();
    }

    /**
     *  手动删除页面缓
     */
    private void deletePageCache() {
        // 手动删除,page页面
        Set<String> keys = redisTemplate.keys("buildCache::buildsPage:*");
        redisTemplate.delete(keys);
    }

    @Cacheable(key = "'builds:'+#id")
    @Override
    public Build queryById(String id) {
        return buildDao.findById(id).get();
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return queryById(id).getName();
        }
    }
}
