package cn.lvhaosir.browser.service;

import cn.lvhaosir.browser.pojo.vo.manage.MessageVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.core.pojo.po.Message;
import org.springframework.data.domain.Page;

/**
 * Created by lvhaosir on 2018/9/27.
 */
public interface MessageService {

    /**
     *  条件查询
     * @param message
     * @param dateSearchVo
     * @param pageVo
     * @return
     */
    Page<Message> queryByCondition(Message message, DateSearchVo dateSearchVo, PageVo pageVo);

    /**
     *  添加消息
     * @param messageVo
     * @return
     */
    boolean addMessage(MessageVo messageVo);

    /**
     *  修改消息
     * @param message
     * @return
     */
    boolean updateMessage(Message message);

    /**
     *  批量删除，撤回消息
     * @param ids
     */
    void deleteByIds(String[] ids);

}
