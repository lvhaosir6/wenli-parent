package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.constant.EntityConstant;
import cn.lvhaosir.core.pojo.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lvhaosir on 2018/9/27.
 */
@Data
@Entity
@Table(name = "tb_message_send")
public class MessageSend extends BaseEntity {

    @ApiModelProperty(value = "关联消息id")
    private String messageId;

    @ApiModelProperty(value = "关联用户id")
    private String userId;

    @ApiModelProperty(value = "状态 0默认未读 1已读 2回收站")
    private Integer status = EntityConstant.MESSAGE_STATUS_UNREAD;

}
