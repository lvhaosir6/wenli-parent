package cn.lvhaosir.browser.pojo.vo.manage;

import cn.lvhaosir.core.pojo.po.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lvhaosir on 2018/9/27.
 */
@Data
public class MessageVo extends Message {

    @ApiModelProperty(value = "发送范围")
    private Integer range;

    @ApiModelProperty(value = "发送指定用户id")
    private String[] userIds;
}
