package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/19.
 */
public interface RoleDao extends JpaRepository<Role,String> {

    @Query(nativeQuery = true , value = " SELECT tr.* FROM tb_role tr LEFT JOIN tb_user_role tur ON tr.id = tur.role_id WHERE tur.user_id = :userId  ")
    List<Role> findByUserId(@Param("userId") String userId);

}
