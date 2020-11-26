package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.BuildService;
import cn.lvhaosir.core.pojo.po.Build;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/18.
 */
@RestController
@RequestMapping("/build")
public class BuildController {


    @Autowired
    private BuildService buildService;

    @GetMapping("/queryAll")
    public Result<List<Build>> queryAll() {
        List<Build> builds = buildService.queryAllList();
        return new ResultUtil<List<Build>>().setData(builds);
    }

    @GetMapping("/getAllByPage")
    public Result<Page<Build>> getAllByPage(@ModelAttribute PageVo pageVo) {
        Page<Build> builds = buildService.queryPageBuildList(pageVo);
        return new ResultUtil<Page<Build>>().setData(builds);
    }

    @PostMapping("/add")
    public Result<Build> add(@ModelAttribute Build build) {
        Build b = buildService.updateOrSave(build);
        return new ResultUtil<Build>().setData(b);
    }

    @PutMapping("/update")
    public Result<Build> update(@ModelAttribute Build build) {
        Build b = buildService.updateOrSave(build);
        return new ResultUtil<Build>().setData(b);
    }

    @DeleteMapping("/delByIds/{ids}")
    public Result<Object> deleteById(@PathVariable("ids") String[] ids) {
        boolean flag = buildService.delByIds(ids);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("删除成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("删除失败,该楼栋正在被使用");
        }
    }

}
