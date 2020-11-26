package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/19.
 */
public interface PermissionDao extends JpaRepository<Permission,String> {

    /**
     *  根据 用户id, 用户状态为正常,并根据 sort_order 从小到大排序,去重
     *  查询所有permission
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value = " select p.* from tb_user u " +
            "left join tb_user_role ur on u.id = ur.user_id " +
            "left join tb_role r on r.id = ur.role_id " +
            "left join tb_role_permission rp on rp.role_id = r.id " +
            "left join tb_permission p on p.id = rp.permission_id " +
            "where u.id =:userId AND p.status = 0 " +
            "ORDER BY p.sort_order ASC ")
    List<Permission> queryByUserId(@Param("userId") String userId);


    /**
     *  根据层级查找
     *  默认升序
     * @param leave
     * @return
     */
    List<Permission> findByLevelOrderBySortOrder(Integer leave);

    /**
     *  根据parentId查找
     * @param parentId
     * @return
     */
    List<Permission> findByParentIdOrderBySortOrder(String parentId);

    /**
     *  根据类型和状态获取权限
     * @param type
     * @param status
     * @return
     */
    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    /**
     *  根据 roleId 查询出所有权限
     * @param roleId
     * @return
     */
    @Query(nativeQuery = true , value = " SELECT p.* FROM tb_role r " +
            "LEFT JOIN  tb_role_permission rp ON rp.role_id = r.id " +
            "LEFT JOIN tb_permission p ON rp.permission_id = p.id " +
            "WHERE r.id = :roleId and p.status = 0 " +
            " order by p.sort_order asc")
    List<Permission> queryByRoleId(@Param("roleId") String roleId);

    /**
     *  根据 title 查找
     * @param title
     * @return
     */
    List<Permission> findByTitle(String title);

}
