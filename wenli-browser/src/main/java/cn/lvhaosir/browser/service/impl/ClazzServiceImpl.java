package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.ClazzVo;
import cn.lvhaosir.browser.service.*;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.ClazzDao;
import cn.lvhaosir.core.pojo.po.Clazz;
import cn.lvhaosir.core.pojo.po.Student;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lvhaosir on 2018/10/20.
 */
@Service
@CacheConfig(cacheNames = "clazzCache")
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Cacheable(key = "'clazzs:'+#departmentId")
    @Override
    public List<Clazz> queryByDepartmentId(String departmentId) {
        return clazzDao.findByDepartmentId(departmentId);
    }

    @Cacheable(key = "'clazzs'")
    @Override
    public List<Clazz> queryAllList() {
        return clazzDao.findAll();
    }

    @Cacheable(key = "'clazzsPage:'+#pageVo.pageNumber+':'+#pageVo.pageSize+':'+#pageVo.sort+':'+#pageVo.order+':'+#clazz.name+':'+#clazz.departmentId+':'+#clazz.teacherId")
    @Override
    public Page<ClazzVo> queryPageList(PageVo pageVo, Clazz clazz) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Clazz> page = clazzDao.findAll(new Specification<Clazz>() {
            @Override
            public Predicate toPredicate(Root<Clazz> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> nameFiled = root.get("name");
                Path<String> departmentIdFiled = root.get("departmentId");
                Path<String> teacherIdFiled = root.get("teacherId");

                List<Predicate> list = new ArrayList<Predicate>();

                // 名称的模糊查询
                if (StringUtils.isNotBlank(clazz.getName())) {
                    list.add(criteriaBuilder.like(nameFiled, clazz.getName() + '%'));
                }
                // 系部
                if (StringUtils.isNotBlank(clazz.getDepartmentId())) {
                    list.add(criteriaBuilder.equal(departmentIdFiled, clazz.getDepartmentId()));
                }
                // 教师
                if (StringUtils.isNotBlank(clazz.getTeacherId())) {
                    list.add(criteriaBuilder.equal(teacherIdFiled, clazz.getTeacherId()));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        Page<ClazzVo> clazzVoPage = new PageImpl<ClazzVo>(changeToListVo(page.getContent()), pageable, page.getTotalElements());

        return clazzVoPage;
    }

    @CacheEvict(key = "'clazzs'")
    @Override
    public void saveOrUpdate(Clazz clazz) {
        // 删除页面缓存
        deleteCache();
        if (StringUtils.isBlank(clazz.getId())) {
            // 如果为空,添加
            clazzDao.save(clazz);
        } else {
            // 更新
            clazzDao.saveAndFlush(clazz);
        }
    }


    private void deleteCache() {
        this.deletePageCache();
        this.deleteCacheFormDepartmentId();
    }

    /**
     *  删除页面缓存
     */
    private void deletePageCache() {
        Set<String> keys = redisTemplate.keys("clazzCache::clazzsPage:*");
        redisTemplate.delete(keys);
    }

    private void deleteCacheFormDepartmentId() {
        Set<String> keys = redisTemplate.keys("clazzCache::clazzs:*");
        redisTemplate.delete(keys);
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return clazzDao.findById(id).get().getName();
        }
    }

    @CacheEvict(key = "'clazzs'")
    @Override
    public boolean delByIds(String[] ids) {
        // 首先检查班级是否被使用
        for (String id : ids) {
            List<Student> students = studentService.queryByClassId(id);
            if (students!= null && students.size() > 0) {
                return false;
            }
        }
        for (String id : ids) {
            clazzDao.deleteById(id);
        }
        // 删除页面缓存
        deleteCache();
        return true;
    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    /**
     *  转换为Vo
     * @param clazz
     * @return
     */
    private ClazzVo changeToVo(Clazz clazz) {
        ClazzVo clazzVo = new ClazzVo(clazz);
        clazzVo.setDepartmentName(departmentService.queryNameById(clazz.getDepartmentId()));
        clazzVo.setTeacherName(userService.queryNameById(clazz.getTeacherId()));
        return clazzVo;
    }

    /**
     *  转换为 ListVo
     * @param clazzs
     * @return
     */
    private List<ClazzVo> changeToListVo(List<Clazz> clazzs) {
        List<ClazzVo> list = new ArrayList<>();
        for(Clazz clazz : clazzs) {
            list.add(changeToVo(clazz));
        }
        return list;
    }

}
