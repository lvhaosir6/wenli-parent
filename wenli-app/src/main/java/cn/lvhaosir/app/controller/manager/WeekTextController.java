package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.pojo.vo.WeekTextVo;
import cn.lvhaosir.app.service.WeekTextService;
import cn.lvhaosir.core.pojo.po.WeekText;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@RequestMapping("/weektext")
@RestController
public class WeekTextController {

    @Autowired
    private WeekTextService weekTextService;

    @PostMapping("/save")
    public Result<Object> save(@ModelAttribute WeekText weekText) {
        WeekText save = weekTextService.save(weekText);
        if (save == null) {
            return new ResultUtil<Object>().setErrorMessage("添加错误");
        }
        return new ResultUtil<Object>().setSuccessMessage("添加成功");
    }

    @GetMapping("/queryByRoomId/{roomId}")
    public Result<List<WeekText>> queryByRoomId(@PathVariable("roomId") String roomId) {
        List<WeekText> weekTexts = weekTextService.queryByRoomId(roomId);
        return new ResultUtil<List<WeekText>>().setData(weekTexts);
    }


    @GetMapping("/queryById/{id}")
    public Result<WeekText> queryById(@PathVariable("id") String id ) {
        WeekText weekText = weekTextService.queryById(id);
        return new ResultUtil<WeekText>().setData(weekText);
    }

    @PutMapping("/updateReturnText")
    public Result<Object> updateReturnText(@RequestParam("id") String id,
                                           @RequestParam("teacherReturnText") String teacherReturnText) {
        weekTextService.updateReturnTextById(id, teacherReturnText);
        return new ResultUtil<Object>().setSuccessMessage("回复成功");
    }

    @GetMapping("/queryNoReturn/{teacherId}")
    public Result<List<WeekTextVo>> queryNoReturn(@PathVariable("teacherId") String teacherId) {
        List<WeekTextVo> weekTextVos = weekTextService.queryNoReturnByTeacherId(teacherId);
        return new ResultUtil<List<WeekTextVo>>().setData(weekTextVos);
    }

}
