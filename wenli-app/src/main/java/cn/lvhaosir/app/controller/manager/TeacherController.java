package cn.lvhaosir.app.controller.manager;

import cn.lvhaosir.app.pojo.vo.TeacherVo;
import cn.lvhaosir.app.service.UserService;
import cn.lvhaosir.core.pojo.po.User;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lvhaosir on 2018/10/15.
 */
@RequestMapping("/teacher")
@RestController
public class TeacherController {

    @Autowired
    private UserService userService;


    @PutMapping("/updatePassword")
    public Result<Object> updatePassword(@RequestParam("id") String id,
                                         @RequestParam("password") String password,
                                         @RequestParam("newPassword") String newPassword) {
        User user = userService.updatePasswordById(id, password, newPassword);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMessage(202,"旧密码错误");
        }
        return new ResultUtil<Object>().setSuccessMessage("修改成功");
    }


    @GetMapping("/queryByUsername/{username}")
    public Result<TeacherVo> queryByUsername(@PathVariable("username") String username) {
        TeacherVo teacherVo = userService.queryTeacherVoByUsername(username);
        return new ResultUtil<TeacherVo>().setData(teacherVo);
    }


}
