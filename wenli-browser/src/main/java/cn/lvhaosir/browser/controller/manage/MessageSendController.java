package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.manage.MessageSendVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.service.MessageSendService;
import cn.lvhaosir.core.pojo.po.MessageSend;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lvhaosir on 2018/9/28.
 */
@RestController
@RequestMapping("/messageSend")
public class MessageSendController {

    @Autowired
    private MessageSendService messageSendService;

    @GetMapping("/getByCondition")
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<MessageSendVo>> getByCondition(@ModelAttribute MessageSend messageSend,
                                                      @ModelAttribute PageVo pageVo) {
        Page<MessageSendVo> page = messageSendService.queryByCondition(messageSend, pageVo);
        return new ResultUtil<Page<MessageSendVo>>().setData(page);
    }

    @PutMapping("/updateStatus")
    @ApiOperation(value = "修改消息状态")
    public Result<Object> updateStatus(@ModelAttribute MessageSend messageSend) {
        boolean flag = messageSendService.updateStatus(messageSend);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("修改成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("修改失败");
        }


    }

}
