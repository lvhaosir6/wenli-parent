package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.service.DepartmentService;
import cn.lvhaosir.core.pojo.po.Department;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@RequestMapping("/department")
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/queryAll")
    public Result<List<Department>> queryAll() {
        List<Department> departments = departmentService.queryAll();
        return new ResultUtil<List<Department>>().setData(departments);
    }

}
