package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.User;
import cn.lvhaosir.core.pojo.po.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/19.
 */
public interface UserRoleDao extends JpaRepository<UserRole,String> {
    /**
     *  根据 userId 删除相关数据
     * @param userId
     */
    void deleteByUserId(String userId);

    /**
     *  根据 roleId 删除相关数据
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     *  根据 roleId 查询
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

}
