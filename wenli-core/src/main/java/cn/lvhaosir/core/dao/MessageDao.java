package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lvhaosir on 2018/9/27.
 */
public interface MessageDao extends JpaRepository<Message,String>,JpaSpecificationExecutor<Message> {


}
