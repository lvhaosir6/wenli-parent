package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.manage.MessageSendVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.core.pojo.po.MessageSend;
import org.springframework.data.domain.Page;

/**
 * Created by lvhaosir on 2018/9/28.
 */
public interface MessageSendService {
    /**
     *  分页查询
     * @param messageSend
     * @param pageVo
     * @return
     */
    Page<MessageSendVo> queryByCondition(MessageSend messageSend, PageVo pageVo);

    /**
     *  修改 消息状态
     * @param messageSend
     * @return
     */
    boolean updateStatus(MessageSend messageSend);

}
