package cn.lvhaosir.core.dao;

import cn.lvhaosir.core.pojo.po.MessageSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lvhaosir on 2018/9/27.
 */
public interface MessageSendDao extends JpaRepository<MessageSend,String>,JpaSpecificationExecutor<MessageSend> {

    /**
     *  根据 messageId 删除
     * @param messageId
     */
    void deleteByMessageId(String messageId);

}
