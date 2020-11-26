package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.manage.DepartmentVo;
import cn.lvhaosir.browser.service.DepartmentService;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.dao.DepartmentDao;
import cn.lvhaosir.core.dao.UserDao;
import cn.lvhaosir.core.pojo.po.Department;
import cn.lvhaosir.core.pojo.po.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Service
@CacheConfig(cacheNames = "departmentCache")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserDao userDao;


    @Override
    public List<DepartmentVo> queryListVoByParentId(String parentId) {
        // 根据 parentId 查询
        List<Department> list = departmentDao.findByParentIdOrderBySortOrder(parentId);
        // 获取 listvo
        return changeToListVo(list);
    }

    @Override
    public Department saveOrUpdate(Department department) {
        if (StringUtils.isBlank(department.getId())) {
            // 如果为空，则为添加
            Department d = departmentDao.save(department);
            // 判断添加的是否为 一级部门
            if (!EntityConstant.PARENT_ID.equals(d.getParentId())) {
                // 不是一级部门，则需要修改。父部门的信息
                Department parentDepartment = queryById(d.getParentId());
                if (parentDepartment.getIsParent() == null || !parentDepartment.getIsParent()) {
                    parentDepartment.setIsParent(true);
                    // 更新父部门的信息
                    departmentDao.saveAndFlush(parentDepartment);
                }
            }
            return d;
        } else {
            // 否则更新
            return departmentDao.saveAndFlush(department);
        }
    }


    @Transactional
    @Override
    public boolean deleteByIds(String[] ids) {

        for (String id : ids) {
            List<User> byDepartmentId = userDao.findByDepartmentId(id);
            if (byDepartmentId != null && byDepartmentId.size() > 0) {
                return false;
            }
        }
        for (String id : ids){
            departmentDao.deleteById(id);
        }
        return true;
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return queryById(id).getTitle();
        }
    }

    @Override
    public Department queryById(String id) {
        return departmentDao.findById(id).get();
    }

    /**
     *  传入 po 添加 parentTitle 传出 dto
     * @param list
     * @return
     */
    private List<DepartmentVo> changeToListVo(List<Department> list) {
        List<DepartmentVo> all = new ArrayList<>();
        for (Department d : list) {
            all.add(changeToVo(d));
        }
        return all;
    }

    private DepartmentVo changeToVo(Department department) {
        DepartmentVo dd = new DepartmentVo(department);
        // 如果是一级部门，添加标题
        if (EntityConstant.PARENT_ID.equals(department.getParentId())) {
            dd.setParentTitle("一级部门");
        } else {
            // 查询出 parentTitle 父标题，添加进 Dto
            dd.setParentTitle(queryById(department.getId()).getTitle());
        }
        return dd;
    }
}
