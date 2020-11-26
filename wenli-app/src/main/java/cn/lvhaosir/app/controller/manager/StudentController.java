package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.pojo.vo.StudentVo;
import cn.lvhaosir.app.service.StudentService;
import cn.lvhaosir.core.pojo.po.Student;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     *  根据宿舍Id查询宿舍成员
     * @param roomId
     * @return
     */
    @GetMapping("/queryByRoomId/{roomId}")
    public Result<List<StudentVo>> queryByRoomId(@PathVariable("roomId") String roomId) {
        List<StudentVo> list = studentService.queryByRoomId(roomId);
        return new ResultUtil<List<StudentVo>>().setData(list);
    }

    /**
     *  学生注册，修改系部班级宿舍信息
     * @param id
     * @param departmentId
     * @param roomId
     * @param classId
     * @return
     */
    @PutMapping("/updateRegister")
    public Result<Object> updateRegister(@RequestParam("id") String id,
                                         @RequestParam("departmentId") String departmentId,
                                         @RequestParam("buildId") String buildId,
                                         @RequestParam("roomId") String roomId,
                                         @RequestParam("classId") String classId) {
        studentService.updateByRegister(id, departmentId, buildId, roomId, classId);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    /**
     *  修改密码
     * @param id
     * @param password
     * @param newPassword
     * @return
     */
    @PutMapping("/updatePassword")
    public Result<Object> updatePassword(@RequestParam("id") String id,
                                         @RequestParam("password") String password,
                                         @RequestParam("newPassword") String newPassword) {
        Student student = studentService.updatePassword(id, password, newPassword);
        if (student == null) {
            // 旧密码错误
            return new ResultUtil<Object>().setErrorMessage("旧密码错误");
        }
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }


    @PutMapping("/resetPassword")
    public Result<Object> resetPassword(@RequestParam("id") String id) {
        studentService.resetPassword(id);
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }

    @GetMapping("/queryByUsername/{username}")
    public Result<StudentVo> queryByUsername(@PathVariable("username") String username) {
        StudentVo studentVo = studentService.queryByUsername(username);
        return new ResultUtil<StudentVo>().setData(studentVo);
    }

}
