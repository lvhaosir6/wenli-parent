package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.manage.RoleVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.RoleService;
import cn.lvhaosir.core.pojo.po.Role;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllList")
    @ApiOperation(value = "获取全部角色")
    public Result<Object> getAllList() {
        List<Role> allList = roleService.getAllList();
        return new ResultUtil<Object>().setData(allList);
    }

    @GetMapping("/getAllByPage")
    @ApiOperation(value = "分页获取角色")
    public Result<Page<RoleVo>> getRoleByPage(@ModelAttribute PageVo pageVo) {
        Page<RoleVo> dtoPage = roleService.queryPageRoleList(pageVo);
        return new ResultUtil<Page<RoleVo>>().setData(dtoPage);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存数据")
    public Result<Role> save(@ModelAttribute Role role) {
        Role r = roleService.addRole(role);
        return new ResultUtil<Role>().setData(r);
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新数据")
    public Result<Role> edit(@ModelAttribute Role role) {
        Role r = roleService.updateRole(role);
        return new ResultUtil<Role>().setData(r);
    }

    @PostMapping("/updateDefault")
    @ApiOperation(value = "设置或取消默认角色")
    public Result<Object> updateDefault(@RequestParam String id,@RequestParam Boolean isDefault) {
        boolean flag = roleService.updateDefult(id, isDefault);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("设置成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("设置失败");
        }
    }

    @PostMapping("/updateRolePermissions/{roleId}")
    @ApiOperation(value = "编辑角色分配权限")
    public Result<Object> updateRolePermissions(@PathVariable String roleId,
                                         @RequestParam(required = false) String[] permIds) {
        boolean flag = roleService.updateRolePermissions(roleId, permIds);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("设置成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("设置失败");
        }
    }


    @DeleteMapping("/delByIds/{ids}")
    @ApiOperation(value = "通过id删除角色")
    public Result<Object> delByIds(@PathVariable String[] ids) {
        boolean flag = roleService.delByIds(ids);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("删除成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("删除失败,有用户正在使用该角色");
        }
    }



}
