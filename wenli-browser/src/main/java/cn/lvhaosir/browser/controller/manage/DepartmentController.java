package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.manage.DepartmentVo;
import cn.lvhaosir.browser.service.DepartmentService;
import cn.lvhaosir.core.pojo.po.Department;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     *  根据
     * @param parentId
     * @return
     */
    @GetMapping("/getByParentId/{parentId}")
    @ApiOperation(value = "通过parentId获取")
    public Result<List<DepartmentVo>> getByParentId(@PathVariable String parentId) {
        List<DepartmentVo> departmentVos = departmentService.queryListVoByParentId(parentId);
        return new ResultUtil<List<DepartmentVo>>().setData(departmentVos);
    }

    /**
     *  修改部门信息
     * @param department
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改信息")
    public Result<Department> update(Department department) {
        Department d = departmentService.saveOrUpdate(department);
        return new ResultUtil<Department>().setData(d);

    }

    /**
     *  新增部门信息
     * @param department
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public Result<Department> add(Department department) {
        Department d = departmentService.saveOrUpdate(department);
        return new ResultUtil<Department>().setData(d);
    }

    /**
     *  批量通过id删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delByIds/{ids}")
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable String[] ids) {
        boolean flag = departmentService.deleteByIds(ids);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("批量通过id删除数据成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("删除失败，包含正被用户使用关联的部门");
        }
    }

}
