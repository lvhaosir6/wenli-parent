package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.manage.UserVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.service.UserService;
import cn.lvhaosir.core.annotation.SystemControllerLog;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.pojo.po.User;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;


    @GetMapping("/info")
    public Result<UserVo> getUserInfo() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo userVo = userService.queryByUsername(user.getUsername());
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        return new ResultUtil<UserVo>().setData(userVo);
    }

    @GetMapping("/getByCondition")
    public Result<Page<UserVo>> getByCondition(@ModelAttribute User user,
                                               @ModelAttribute DateSearchVo dateSearchVo,
                                               @ModelAttribute PageVo pageVo){
        Page<UserVo> userDtoPage = userService.queryByCondition(user, dateSearchVo, pageVo);
        return new ResultUtil<Page<UserVo>>().setData(userDtoPage);
    }


    @PostMapping("/admin/add")
    @ApiOperation(value = "添加用户")
    public Result<Object> addUser(@ModelAttribute User u ,
                                  @RequestParam(required = false) String[] roles ) {
        String username = u.getUsername();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(u.getPassword())) {
            return new ResultUtil<Object>().setErrorMessage("缺少必需表单字段");
        }

        if (userService.findByUsername(username) != null) {
            return new ResultUtil<Object>().setErrorMessage("该用户名已被注册");
        }
        User user = userService.addUser(u, roles);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMessage("注册失败");
        }

        return new ResultUtil<Object>().setData(user);
    }

    @PostMapping("/admin/update")
    @ApiOperation(value = "修改资料")
    public Result<Object> updateUser(@ModelAttribute User u,
                                     @RequestParam(required = false) String[] roles) {
        Integer flag = userService.updateUser(u, roles);
        if (flag == -1) {
            return new ResultUtil<Object>().setErrorMessage("修改失败");
        } else if (flag == -2) {
            return new ResultUtil<Object>().setErrorMessage("该用户名已被存在");
        } else {
            return new ResultUtil<Object>().setSuccessMessage("修改成功");
        }
    }

    @SystemControllerLog(description = "启用用户")
    @PostMapping("/admin/enable/{userId}")
    @ApiOperation(value = "后台启用用户")
    public Result<Object> enable(@PathVariable String userId) {

        boolean flag = userService.updateUser(userId, EntityConstant.USER_STATUS_NORMAL);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("开启成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("开启失败");
        }
    }

    @SystemControllerLog(description = "禁用用户")
    @PostMapping("/admin/disable/{userId}")
    @ApiOperation(value = "后台禁用用户")
    public Result<Object> disable(@PathVariable String userId) {
        boolean flag = userService.updateUser(userId, EntityConstant.USER_STATUS_LOCK);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("禁用成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("禁用失败");
        }
    }

    @DeleteMapping("/delByIds/{ids}")
    @ApiOperation(value = "通过id删除用户")
    public Result<Object> delByIds(@PathVariable String[] ids) {
        userService.delByIds(ids);
        return new ResultUtil<Object>().setSuccessMessage("删除成功");
    }

    @PostMapping("/unlock")
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password) {

        boolean flag = userService.unLock(password);
        if (flag) {
            return new ResultUtil<Object>().setData(null);
        } else {
            return new ResultUtil<Object>().setErrorMessage("密码错误");
        }
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改用户自己资料",notes = "用户名密码不会修改 需要通过id获取原用户信息")
    public Result<Object> updateOwn(@ModelAttribute User user) {
        boolean flag = userService.updateUser(user);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("修改成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("修改失败");
        }
    }


    @PostMapping("/modifyPass")
    @ApiOperation(value = "修改密码")
    public Result<Object> modifyPass(@RequestParam String id,@RequestParam String password,
                                     @RequestParam String newPass) {
        User user = userService.modifyPass(id, password, newPass);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMessage("旧密码不正确");
        }
        return new ResultUtil<Object>().setData(user);
    }

    @GetMapping("/queryAll")
    public Result<List<User>> queryAll() {
        List<User> users = userService.queryAll();
        return new ResultUtil<List<User>>().setData(users);
    }

    @GetMapping("/queryByDepartmentId/{departmentId}")
    public Result<List<User>> queryByTeacherId(@PathVariable("departmentId") String departmentId) {
        List<User> users = userService.queryByDepartmentId(departmentId);
        return new ResultUtil<List<User>>().setData(users);
    }

}
