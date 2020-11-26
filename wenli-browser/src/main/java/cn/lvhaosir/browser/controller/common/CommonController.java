package cn.lvhaosir.browser.controller.common;

import cn.lvhaosir.core.utils.IpInfoUtil;
import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @GetMapping("/ip/info")
    public Result<Object> ipInfo(HttpServletRequest request) {
        String result = ipInfoUtil.getIpWeatherInfo(ipInfoUtil.getIpAddr(request));
        return new ResultUtil<Object>().setData(result);
    }

}
