package cn.lvhaosir.app.controller;

import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @RequestMapping("/needLogin")
    public Result<Object> needLogin() {
        return new ResultUtil().setErrorMessage(401,"您还未登录");
    }


}
