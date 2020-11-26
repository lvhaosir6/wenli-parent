package cn.lvhaosir.browser.controller.manage;

import cn.lvhaosir.browser.pojo.vo.manage.MessageVo;
import cn.lvhaosir.browser.pojo.vo.condition.PageVo;
import cn.lvhaosir.browser.pojo.vo.condition.DateSearchVo;
import cn.lvhaosir.browser.service.MessageService;
import cn.lvhaosir.core.pojo.po.Message;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lvhaosir on 2018/9/27.
 */
@RestController
@RequestMapping("/message")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @GetMapping("/getByCondition")
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Message>> getByCondition(@ModelAttribute Message message ,
                                                  @ModelAttribute DateSearchVo dateSearchVo,
                                                  @ModelAttribute PageVo pageVo) {
        Page<Message> page = messageService.queryByCondition(message, dateSearchVo, pageVo);
        return new ResultUtil<Page<Message>>().setData(page);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加（发送）消息")
    public Result<Object> addMessage(@ModelAttribute MessageVo messageVo) {
        boolean flag = messageService.addMessage(messageVo);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("添加成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("添加失败");
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改消息")
    public Result<Object> updateMessage(@ModelAttribute Message message) {
        boolean flag = messageService.updateMessage(message);
        if (flag) {
            return new ResultUtil<Object>().setSuccessMessage("修改成功");
        } else {
            return new ResultUtil<Object>().setErrorMessage("修改失败");
        }
    }

    @DeleteMapping("/delByIds/{ids}")
    @ApiOperation(value = "删除消息（撤回消息）")
    public Result<Object> deleteByIds(@PathVariable String[] ids) {
        messageService.deleteByIds(ids);
        return new ResultUtil<Object>().setSuccessMessage("删除成功");
    }



}
