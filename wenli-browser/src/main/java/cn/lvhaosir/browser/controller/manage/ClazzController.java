package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.ClazzVo;
import cn.lvhaosir.browser.service.ClazzService;
import cn.lvhaosir.core.pojo.po.Clazz;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/20.
 */
@RequestMapping("/clazz")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/queryAll")
    public Result<List<Clazz>> queryAll() {
        List<Clazz> clazzs = clazzService.queryAllList();
        return new ResultUtil<List<Clazz>>().setData(clazzs);
    }

    @GetMapping("/loadByDepartmentId/{departmentId}")
    public Result<List<Clazz>> loadByDepartmentId(@PathVariable String departmentId) {
        List<Clazz> clazzs = clazzService.queryByDepartmentId(departmentId);
        return new ResultUtil<List<Clazz>>().setData(clazzs);
    }


    @GetMapping("/getAllByPage")
    public Result<Page<ClazzVo>> getAllByPage(@ModelAttribute PageVo pageVo,
                                              @ModelAttribute Clazz clazz) {
        Page<ClazzVo> clazzVos = clazzService.queryPageList(pageVo, clazz);
        return new ResultUtil<Page<ClazzVo>>().setData(clazzVos);
    }

    @PutMapping("/updateById")
    public Result<Object> updateById(@ModelAttribute Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }


    @PostMapping("/save")
    public Result<Object> save(@ModelAttribute Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return new ResultUtil<Object>().setSuccessMessage("新增成功");
    }

    @DeleteMapping("/delByIds/{ids}")
    public Result<Object> delByIds(@PathVariable("ids") String[] ids) {
        boolean flag = clazzService.delByIds(ids);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("删除成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("删除失败，班级正在被使用");
        }
    }

}
