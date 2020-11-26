package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.pojo.vo.ClazzRooms;
import cn.lvhaosir.app.service.ClazzService;
import cn.lvhaosir.core.pojo.po.Clazz;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@RequestMapping("/clazz")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;


    @GetMapping("/queryByDepartmentId/{departmentId}")
    public Result<List<Clazz>> queryByDepartmentId(@PathVariable("departmentId") String departmentId) {
        List<Clazz> clazzs = clazzService.queryByDepartmentId(departmentId);
        return new ResultUtil<List<Clazz>>().setData(clazzs);
    }

    @GetMapping("/queryByTeacherId/{teacherId}")
    public Result<List<Clazz>> queryByTeacherId(@PathVariable("teacherId") String teacherId) {
        List<Clazz> clazzs = clazzService.queryByTeacherId(teacherId);
        return new ResultUtil<List<Clazz>>().setData(clazzs);
    }


    @GetMapping("/queryClassRooms/{teacherId}")
    public Result<List<ClazzRooms>> queryClazzRooms(@PathVariable("teacherId") String teacherId) {
        List<ClazzRooms> clazzRoomses = clazzService.queryClassRooms(teacherId);
        return new ResultUtil<List<ClazzRooms>>().setData(clazzRoomses);
    }


}
