package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

/**
 * Created by lvhaosir on 2018/9/14.
 */
public interface BuildDao extends JpaRepository<Build,String>,JpaSpecificationExecutor<Build> {

}
