package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.service.LogService;
import cn.lvhaosir.core.pojo.po.Log;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lvhaosir on 2018/10/7.
 */
@RequestMapping("/log")
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/getAllByPage")
    @ApiOperation(value = "分页获取全部日志信息")
    public Result<Object> getAllByPage(@ModelAttribute PageVo pageVo) {
        // 直接查询数据库的
        Page<Log> all = logService.findAll(pageVo);
        return new ResultUtil<Object>().setData(all);
    }

    @GetMapping("/search")
    @ApiOperation(value = "分页搜索")
    public Result<Object> search(@RequestParam String key ,
                                 @ModelAttribute DateSearchVo dateSearchVo,
                                 @ModelAttribute PageVo pageVo) {
        // 直接在数据库中查询
        Page<Log> logPage = logService.searchLog(key, dateSearchVo, pageVo);
        return new ResultUtil<Object>().setData(logPage);
    }

}
