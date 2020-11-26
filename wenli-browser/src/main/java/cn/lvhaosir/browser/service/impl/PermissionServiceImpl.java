package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.manage.PermissionVo;
import cn.lvhaosir.browser.service.PermissionService;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.dao.PermissionDao;
import cn.lvhaosir.core.dao.RolePermissionDao;
import cn.lvhaosir.core.pojo.po.Permission;
import cn.lvhaosir.core.pojo.po.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<PermissionVo> queryAllMenuListByUserId(String userId) {

        List<PermissionVo> menuList = new ArrayList<>();

        // 用户所有权限，已排序去重
        List<Permission> permissionListlist = permissionDao.queryByUserId(userId);
        // Dto List
        List<PermissionVo> list = this.changeToDtoList(permissionListlist);

        // 筛选一级页面
        for (PermissionVo p : list) {
            if (EntityConstant.PERMISSION_PAGE.equals(p.getType())
                    && EntityConstant.LEVEL_ONE.equals(p.getLevel())) {
                menuList.add(p);
            }
        }
        // 筛选二级页面
        List<PermissionVo> secondMenuList = new ArrayList<>();
        for (PermissionVo p : list) {
            if (EntityConstant.PERMISSION_PAGE.equals(p.getType())
                    && EntityConstant.LEVEL_TWO.equals(p.getLevel())) {
                secondMenuList.add(p);
            }
        }
        // 筛选二级页面拥有的按钮权限
        List<PermissionVo> buttonPermissions = new ArrayList<>();
        for (PermissionVo p : list) {
            if (EntityConstant.PERMISSION_OPERATION.equals(p.getType())
                    && EntityConstant.LEVEL_THREE.equals(p.getLevel())) {
                buttonPermissions.add(p);
            }
        }
        // 匹配二级页面拥有权限
        for (PermissionVo p : secondMenuList) {
            List<String> permTypes = new ArrayList<>();
            for(PermissionVo pe : buttonPermissions){
                if(p.getId().equals(pe.getParentId())){
                    permTypes.add(pe.getButtonType());
                }
            }
            p.setPermTypes(permTypes);
        }

        // 匹配一级页面拥有二级页面
        for (PermissionVo p : menuList) {
            List<PermissionVo> secondMenu = new ArrayList<>();
            for (PermissionVo pe : secondMenuList) {
                if (p.getId().equals(pe.getParentId())) {
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }
        return menuList;
    }

    @Override
    public List<PermissionVo> queryAllList() {
        // 一级
        List<Permission> one = permissionDao.findByLevelOrderBySortOrder(EntityConstant.LEVEL_ONE);
        List<PermissionVo> onelist = changeToDtoList(one);
        for (PermissionVo pd1 : onelist) {
            // 二级
            List<Permission> two = permissionDao.findByParentIdOrderBySortOrder(pd1.getId());
            List<PermissionVo> twoList = changeToDtoList(two);
            // 将相应的二级菜单添加到一级菜单
            pd1.setChildren(twoList);
            for (PermissionVo pd2 : twoList) {
                // 三级
                List<Permission> three = permissionDao.findByParentIdOrderBySortOrder(pd2.getId());
                List<PermissionVo> threeList = changeToDtoList(three);
                pd2.setChildren(threeList);
            }
        }
        return onelist;
    }

    @Override
    public List<PermissionVo> queryByRoleId(String roleId) {
        List<Permission> permissionList = permissionDao.queryByRoleId(roleId);
        return changeToDtoList(permissionList);
    }

    @Override
    public Permission addPermission(Permission permission) {

        // 操作权限按钮名  不能重复
        if (EntityConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            List<Permission> byTitle = permissionDao.findByTitle(permission.getTitle());
            if (byTitle != null && byTitle.size() > 0) {
                return null;
            }
        }
        Permission p = permissionDao.save(permission);
        return p;
    }

    @Override
    public Permission updatePermission(Permission permission) {

        // 操作权限按钮名  不能重复
        if (EntityConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            Permission oldPermission = permissionDao.findById(permission.getId()).get();
            // 如果 title 改变
            if (!permission.getTitle().equals(oldPermission.getTitle())) {
                List<Permission> byTitle = permissionDao.findByTitle(permission.getTitle());
                // title 已存在
                if (byTitle != null && byTitle.size() > 0) {
                    return null;
                }
            }
        }
        Permission p = permissionDao.saveAndFlush(permission);
        return p;
    }

    @Transactional
    @Override
    public boolean deleteByIds(String[] ids) {

        for (String id : ids) {
            List<RolePermission> byPermissionId = rolePermissionDao.findByPermissionId(id);
            if (byPermissionId != null && byPermissionId.size() > 0) {
                return false;
            }
        }
        for (String id : ids) {
            permissionDao.deleteById(id);
        }
        return true;
    }

    @Override
    public List<Permission> queryByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {
        return permissionDao.findByTypeAndStatusOrderBySortOrder(type, status);
    }

    /**
     *  将 po List 转换为 Dto List
     * @param list
     * @return
     */
    private List<PermissionVo> changeToDtoList(List<Permission> list) {
        List<PermissionVo> listDto = new ArrayList<>();
        for (Permission p : list) {
            PermissionVo permissionVo = new PermissionVo(p);
            listDto.add(permissionVo);
        }
        return listDto;
    }
}
