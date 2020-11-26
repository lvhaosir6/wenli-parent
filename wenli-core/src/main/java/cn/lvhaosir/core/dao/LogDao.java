package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lvhaosir on 2018/10/7.
 */
public interface LogDao extends JpaRepository<Log,String>,JpaSpecificationExecutor<Log> {

}
