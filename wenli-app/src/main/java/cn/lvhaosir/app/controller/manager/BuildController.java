package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.service.BuildService;
import cn.lvhaosir.core.pojo.po.Build;
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
@RequestMapping("/build")
@RestController
public class BuildController {

    @Autowired
    private BuildService buildService;

    @GetMapping("/queryAll")
    public Result<List<Build>> queryAll() {
        List<Build> builds = buildService.queryAll();
        return new ResultUtil<List<Build>>().setData(builds);
    }

}
