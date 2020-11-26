package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.manage.PermissionVo;
import cn.lvhaosir.core.pojo.po.Permission;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
public interface PermissionService {

    /**
     *  根据userId 查询出该用户的所有菜单数据
     * @param userId
     * @return
     */
    List<PermissionVo> queryAllMenuListByUserId(String userId);

    /**
     *  获取所有权限（结构已拼装）
     * @return
     */
    List<PermissionVo> queryAllList();

    /**
     *  根据 roleId 获取所有该权限信息（但并未包含 children 等一些信息）
     * @param roleId
     * @return
     */
    List<PermissionVo> queryByRoleId(String roleId);

    /**
     *  新增
     * @param permission
     * @return
     */
    Permission addPermission(Permission permission);

    /**
     *  修改
     * @param permission
     * @return
     */
    Permission updatePermission(Permission permission);

    /**
     *  根据id删除
     * @param ids
     */
    boolean deleteByIds(String[] ids);


    /**
     *  根据 类型 和 状态  获取权限
     * @param type
     * @param status
     * @return
     */
    List<Permission> queryByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

}
