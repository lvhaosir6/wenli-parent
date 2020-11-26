package cn.lvhaosir.browser.service.impl;

import cn.lvhaosir.browser.pojo.vo.manage.MessageSendVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.MessageSendService;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.dao.MessageDao;
import cn.lvhaosir.core.dao.MessageSendDao;
import cn.lvhaosir.core.dao.UserDao;
import cn.lvhaosir.core.pojo.po.Message;
import cn.lvhaosir.core.pojo.po.MessageSend;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/28.
 */
@Service
public class MessageSendServiveImpl implements MessageSendService {

    @Autowired
    private MessageSendDao messageSendDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Page<MessageSendVo> queryByCondition(MessageSend messageSend, PageVo pageVo) {

        Pageable pageable = PageUtil.initPage(pageVo);

        Page<MessageSend> page = messageSendDao.findAll(new Specification<MessageSend>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<MessageSend> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> messageIdField = root.get("messageId");
                Path<String> userIdField = root.get("userId");
                Path<Integer> statusField = root.get("status");

                List<Predicate> list = new ArrayList<Predicate>();

                String messageId = messageSend.getMessageId();
                if (StringUtils.isNotBlank(messageId)) {
                    list.add(criteriaBuilder.equal(messageIdField, messageId));
                }
                String userId = messageSend.getUserId();
                if (StringUtils.isNotBlank(userId)) {
                    list.add(criteriaBuilder.equal(userIdField, userId));
                }
                Integer status = messageSend.getStatus();
                if (status != null) {
                    list.add(criteriaBuilder.equal(statusField, status));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));

                return null;
            }
        }, pageable);


        List<MessageSendVo> dtoList = new ArrayList<>();
        for (MessageSend ms : page.getContent()) {
            MessageSendVo messageSendVo = new MessageSendVo(ms);

            String messageId = ms.getMessageId();
            String userId = ms.getUserId();
            Message message = messageDao.findById(messageId).get();
            if (message != null) {
                // 消息标题
                messageSendVo.setTitle(message.getTitle());
                // 消息内容
                messageSendVo.setContent(message.getContent());
                // 消息类型
                messageSendVo.setType(message.getType());
                // 用户名
                messageSendVo.setUsername(userDao.findById(userId).get().getUsername());
            }
            dtoList.add(messageSendVo);
        }

        Page<MessageSendVo> dtoPage = new PageImpl<MessageSendVo>(dtoList,pageable,page.getTotalElements());
        return dtoPage;
    }


    @Override
    public boolean updateStatus(MessageSend messageSend) {
        MessageSend ms = messageSendDao.saveAndFlush(messageSend);
        if (ms == null) {
            return false;
        }
        return true;
    }
}
