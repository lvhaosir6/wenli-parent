package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.MessageSend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;

/**
 * Created by lvhaosir on 2018/9/28.
 */
@Data
public class MessageSendVo extends MessageSend{

    @ApiModelProperty(value = "发送用户名")
    private String username;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息类型")
    private Integer type;


    public MessageSendVo(MessageSend messageSend) {
        if (messageSend != null) {
            // BaseEntity
            this.setId(messageSend.getId());
            this.setCreateTime(messageSend.getCreateTime());
            this.setCreateBy(messageSend.getCreateBy());
            this.setUpdateTime(messageSend.getUpdateTime());
            this.setUpdateBy(messageSend.getUpdateBy());
            this.setDelFlag(messageSend.getDelFlag());
            // messageSend
            this.setStatus(messageSend.getStatus());
            this.setUserId(messageSend.getUserId());
            this.setMessageId(messageSend.getMessageId());

        }
    }

}
