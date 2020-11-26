package cn.lvhaosir.app.service.impl;

import cn.lvhaosir.app.service.DepartmentService;
import cn.lvhaosir.core.dao.DepartmentDao;
import cn.lvhaosir.core.pojo.po.Department;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> queryAll() {
        return departmentDao.findAll();
    }

    @Override
    public String queryNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "无";
        } else {
            return departmentDao.findById(id).get().getTitle();
        }
    }
}
