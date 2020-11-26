package cn.lvhaosir.browser.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.lvhaosir.browser.pojo.vo.manage.MessageVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.service.MessageService;
import cn.lvhaosir.browser.utils.PageUtil;
import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.dao.MessageDao;
import cn.lvhaosir.core.dao.MessageSendDao;
import cn.lvhaosir.core.dao.UserDao;
import cn.lvhaosir.core.pojo.po.Message;
import cn.lvhaosir.core.pojo.po.MessageSend;
import cn.lvhaosir.core.pojo.po.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lvhaosir on 2018/9/27.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageSendDao messageSendDao;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Page<Message> queryByCondition(Message message, DateSearchVo dateSearchVo, PageVo pageVo) {

        Pageable pageable = PageUtil.initPage(pageVo);

        Page<Message> page = messageDao.findAll(new Specification<Message>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<String> titleField = root.get("title");
                Path<String> contentField = root.get("content");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                // 模糊搜索
                String title = message.getTitle();
                if (StringUtils.isNotBlank(title)) {
                    list.add(criteriaBuilder.like(titleField, '%' + title + '%'));
                }
                String content = message.getContent();
                if (StringUtils.isNotBlank(content)) {
                    list.add(criteriaBuilder.like(contentField, '%' + message.getContent() + '%'));
                }
                // 创建时间
                String startDate = dateSearchVo.getStartDate();
                String endDate = dateSearchVo.getEndDate();
                if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                    Date start = DateUtil.parse(startDate);
                    Date end = DateUtil.parse(endDate);
                    list.add(criteriaBuilder.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        return page;
    }

    @Override
    public boolean addMessage(MessageVo messageVo) {
        Message message = convert(messageVo);
        // 保存消息
        Message saveMessage = messageDao.save(message);
        if (saveMessage == null) {
            return false;
        } else {
            // 保存消息发送表
            List<MessageSend> messageSends = new ArrayList<>();
            // 获取发送范围
            Integer range = messageVo.getRange();
            // 如果是发送给全体人员
            if (EntityConstant.MESSAGE_RANGE_ALL.equals(range)) {
                // 获取全部的用户
                List<User> allUser = userDao.findAll();
                for (User user : allUser) {
                    MessageSend ms = new MessageSend();
                    ms.setMessageId(saveMessage.getId());
                    ms.setUserId(user.getId());
                    messageSends.add(ms);
                }
                // 推送
                messagingTemplate.convertAndSend("/topic/subscribe", "您收到了新的系统消息");
            } else {
                // 发送给指定用户
                String[] userIds = messageVo.getUserIds();
                for (String id : userIds) {
                    MessageSend ms = new MessageSend();
                    ms.setMessageId(saveMessage.getId());
                    ms.setUserId(id);
                    messageSends.add(ms);
                    // 指定用户
                    messagingTemplate.convertAndSendToUser(id,"/queue/subscribe", "您收到了新的消息");
                }
            }
            // 保存全部 发送表
            messageSendDao.saveAll(messageSends);
        }
        return true;
    }


    @Override
    public boolean updateMessage(Message message) {
        Message m = messageDao.saveAndFlush(message);
        if (m == null) {
            return false;
        }
        return true;
    }


    @Transactional
    @Override
    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            // 首先删除 信息
            messageDao.deleteById(id);
            // 再删除 信息发送
            messageSendDao.deleteByMessageId(id);
        }
    }

    private Message convert(MessageVo messageVo) {
        Message message = new Message();

        message.setContent(messageVo.getContent());
        message.setCreateSend(messageVo.getCreateSend());
        message.setTitle(messageVo.getTitle());
        message.setType(messageVo.getType());
        // BaseEntity
        message.setId(messageVo.getId());
        message.setCreateBy(messageVo.getCreateBy());
        message.setCreateTime(messageVo.getCreateTime());
        message.setUpdateBy(messageVo.getUpdateBy());
        message.setUpdateTime(messageVo.getUpdateTime());
        return message;
    }


}
