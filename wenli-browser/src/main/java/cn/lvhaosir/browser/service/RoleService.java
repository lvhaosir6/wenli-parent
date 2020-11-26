package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.manage.RoleVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.core.pojo.po.Role;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
public interface RoleService {

    /**
     *  获取全部角色，没有包含拥有权限数据
     * @return
     */
    List<Role> getAllList();

    /**
     * 分页获取角色 Dto
     * @param pageVo
     * @return
     */
    Page<RoleVo> queryPageRoleList(PageVo pageVo);

    /**
     *  新增
     * @param role
     * @return
     */
    Role addRole(Role role);

    /**
     *  更新
     * @param role
     * @return
     */
    Role updateRole(Role role);

    /**
     *  设置 或 取消默认角色
     * @param id
     * @param isDefault
     * @return
     */
    boolean updateDefult(String id,Boolean isDefault);

    /**
     *  更新 角色 所用户的权限
     * @param roleId
     * @param permIds
     * @return
     */
    boolean updateRolePermissions(String roleId,String[] permIds);


    boolean delByIds(String ids[]);

}

