package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/19.
 */
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    /**
     *  根据用户名查询
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     *  根据用户名和用户状态
     * @param name 用户名
     * @param status 用户状态
     * @return
     */
    User findByUsernameAndStatus(String name,Integer status);

    /**
     *  根据 departmentId 查询
     * @param departmentId
     * @return
     */
    List<User> findByDepartmentId(String departmentId);

    @Modifying
    @Query("update User u set u.password=:password where id=:id")
    void updatePassword(@Param("id") String id, @Param("password") String password);

}
