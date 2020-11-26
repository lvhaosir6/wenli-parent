package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/19.
 */
public interface RolePermissionDao extends JpaRepository<RolePermission,String> {


    /**
     *  根据 roleId 删除相关数据
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     *  根据 permissionId 查询
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(String permissionId);

}
