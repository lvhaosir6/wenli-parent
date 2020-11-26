package cn.lvhaosir.app.controller.common;

import cn.lvhaosir.app.service.common.MailSender;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lvhaosir on 2018/10/23.
 */
@RequestMapping("/common")
@RestController
public class CommonController {

    @Autowired
    private MailSender mailSender;

    /**
     *  意见反馈
     * @param content
     * @param cardId
     * @return
     */
    @PostMapping("/sendFeedback")
    public Result<Object> sendFeedback(@RequestParam("content") String content,
                                       @RequestParam("cardId") String cardId) {
        mailSender.sendFeedback(content, cardId);
        return new ResultUtil<Object>().setSuccessMessage("意见反馈成功!");
    }

    /**
     *  异常周记
     * @param id
     * @return
     */
    @PostMapping("/sendUrgencyWeekText")
    public Result<Object> sendUrgencyWeekText(@RequestParam("id") String id) {
        mailSender.sendUrgencyWeekText(id);
        return new ResultUtil<Object>().setSuccessMessage("意见反馈成功!");
    }

}
