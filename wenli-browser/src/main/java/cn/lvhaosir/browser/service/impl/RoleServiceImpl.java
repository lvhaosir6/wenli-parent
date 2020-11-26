package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.manage.PermissionVo;
import cn.lvhaosir.browser.pojo.vo.manage.RoleVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.PermissionService;
import cn.lvhaosir.browser.service.RoleService;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.RoleDao;
import cn.lvhaosir.core.dao.RolePermissionDao;
import cn.lvhaosir.core.dao.UserRoleDao;
import cn.lvhaosir.core.pojo.po.Role;
import cn.lvhaosir.core.pojo.po.RolePermission;
import cn.lvhaosir.core.pojo.po.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<Role> getAllList() {
        return roleDao.findAll();
    }

    @Override
    public Page<RoleVo> queryPageRoleList(PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Role> page = roleDao.findAll(pageable);
        List<RoleVo> dtoList = new ArrayList<>();

        for (Role role : page.getContent()) {
            RoleVo rd = new RoleVo(role);
            // 根据 roleId 查询出所有权限信息
            List<PermissionVo> permissionVoList = permissionService.queryByRoleId(role.getId());
            rd.setPermissions(permissionVoList);
            dtoList.add(rd);
        }
        Page<RoleVo> dtoPage = new PageImpl<RoleVo>(dtoList,pageable,page.getTotalElements());
        return dtoPage;
    }

    @Override
    public Role addRole(Role role) {
        Role save = roleDao.save(role);
        return save;
    }

    @Override
    public Role updateRole(Role role) {
        Role r = roleDao.saveAndFlush(role);
        return r;
    }

    @Override
    public boolean updateDefult(String id, Boolean isDefault) {
//        Role one = roleDao.findOne(id);
        Role one = roleDao.findById(id).get();
        if (one == null) {
            return false;
        }
        one.setDefaultRole(isDefault);
        roleDao.saveAndFlush(one);
        return true;
    }

    @Transactional
    @Override
    public boolean updateRolePermissions(String roleId, String[] permIds) {
        // 删除之前的权限
        rolePermissionDao.deleteByRoleId(roleId);
        // 分配新权限
        for (String permId : permIds) {
            RolePermission rp = new RolePermission();
            rp.setRoleId(roleId);
            rp.setPermissionId(permId);
            rolePermissionDao.save(rp);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean delByIds(String[] ids) {
        for (String id : ids) {
            // 根据 roleId 查询是否有用户使用该角色
            List<UserRole> byRoleId = userRoleDao.findByRoleId(id);
            if (byRoleId != null && byRoleId.size() > 0) {
                return false;
            }
        }
        // 如果没有用户使用该角色
        for (String id : ids) {
//            roleDao.delete(id);
            roleDao.deleteById(id);
        }
        return true;
    }
}
