package cn.lvhaosir.core.pojo.po;

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
@Table(name = "tb_message")
public class Message extends BaseEntity {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "消息类型 0公告 1提醒 2私信")
    private Integer type;

    @ApiModelProperty(value = "新创建账号也推送")
    private Boolean createSend;


}
