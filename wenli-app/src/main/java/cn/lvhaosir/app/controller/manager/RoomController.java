package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.service.RoomService;
import cn.lvhaosir.core.pojo.po.Room;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javafx.scene.input.KeyCode.R;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@RequestMapping("/room")
@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;


    @GetMapping("/queryByBuildId/{buildId}")
    public Result<List<Room>> queryByBuildId(@PathVariable("buildId") String buildId) {
        List<Room> rooms = roomService.queryByBuildId(buildId);
        return new ResultUtil<List<Room>>().setData(rooms);
    }

    @GetMapping("/queryById/{id}")
    public Result<Room> queryById(@PathVariable("id") String id) {
        Room room = roomService.queryById(id);
        return new ResultUtil<Room>().setData(room);
    }


    @PutMapping("/updateStudentId")
    public Result<Object> updateStudentId(@RequestParam("id") String id,
                                          @RequestParam("studentId") String studentId) {
        roomService.updateStudentId(id, studentId);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    @PutMapping("/updateRegisterById")
    public Result<Object> updateRegisterById(@RequestParam("id") String id,
                                             @RequestParam("departmentId") String departmentId,
                                             @RequestParam("classId") String classId,
                                             @RequestParam("teacherId") String teacherId,
                                             @RequestParam("studentId") String studentId) {
        roomService.updateRegisterById(id, departmentId, classId, teacherId, studentId);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }




}
