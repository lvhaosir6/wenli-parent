package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.manage.PermissionVo;
import cn.lvhaosir.browser.service.PermissionService;
import cn.lvhaosir.core.pojo.po.Permission;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     *  根据 userId 获取用户页面菜单数据
     * @param userId
     * @return
     */
    @GetMapping("/getMenuList/{userId}")
    @ApiOperation(value = "获取用户页面菜单数据")
    public Result<List<PermissionVo>> getAllMenuList(@PathVariable String userId) {
        List<PermissionVo> list = permissionService.queryAllMenuListByUserId(userId);
        return new ResultUtil<List<PermissionVo>>().setData(list);
    }

    /**
     *  获取所有权限，以整合对应结构
     * @return
     */
    @GetMapping("/getAllList")
    @ApiOperation(value = "获取权限菜单树")
    public Result<List<PermissionVo>> getAllList() {
        List<PermissionVo> list = permissionService.queryAllList();
        return new ResultUtil<List<PermissionVo>>().setData(list);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增权限")
    public Result<Permission> addPermission(@ModelAttribute Permission permission) {
        Permission p = permissionService.addPermission(permission);
        if (p == null) {
            return new ResultUtil<Permission>().setErrorMessage("名称已存在");
        }
        return new ResultUtil<Permission>().setData(p);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改权限")
    public Result<Permission> updatePermission(@ModelAttribute Permission permission) {
        Permission p = permissionService.updatePermission(permission);
        if (p == null) {
            return new ResultUtil<Permission>().setErrorMessage("名称已存在");
        }
        return new ResultUtil<Permission>().setData(p);
    }


    @DeleteMapping("/delByIds/{ids}")
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable String[] ids) {

        boolean flag = permissionService.deleteByIds(ids);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("删除成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("删除失败，包含正被角色使用关联的菜单或权限");
        }
    }



}
