package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.manage.WeekTextVo;
import cn.lvhaosir.browser.service.WeekTextService;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by lvhaosir on 2018/10/26.
 */
@RequestMapping("/weekText")
@RestController
public class WeekTextController {


    @Autowired
    private WeekTextService weekTextService;


    @GetMapping("/getAllByPage")
    public Result<Page<WeekTextVo>> getAllByPage(@ModelAttribute PageVo pageVo,
                                                 @ModelAttribute WeekTextVo weekTextVo,
                                                 @ModelAttribute DateSearchVo dateSearchVo) {
        Page<WeekTextVo> weekTextVoPage = weekTextService.queryPageList(pageVo, weekTextVo, dateSearchVo);
        return new ResultUtil<Page<WeekTextVo>>().setData(weekTextVoPage);
    }


    @DeleteMapping("/delByIds/{ids}")
    public Result<Object> delByIds(@PathVariable("ids") String[] ids) {
        weekTextService.delByIds(ids);
        return new ResultUtil<Object>().setSuccessMessage("删除成功");
    }

}
