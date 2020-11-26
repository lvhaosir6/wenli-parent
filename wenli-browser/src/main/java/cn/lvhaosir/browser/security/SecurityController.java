package cn.lvhaosir.browser.security;

import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lvhaosir on 2018/9/17.
 */
@RestController
public class SecurityController {

    @RequestMapping("/needLogin")
    public Result<Object> needLogin() {
        return new ResultUtil().setErrorMessage(401,"您还未登录");
    }


}
