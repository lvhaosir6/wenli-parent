package cn.lvhaosir.browser.exception;

import cn.lvhaosir.core.pojo.vo.Result;
import cn.lvhaosir.core.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by lvhaosir on 2018/10/3.
 *
 * 异常处理
 */
@Slf4j
@RestControllerAdvice
public class RestCtrlExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<Object> handleException(Exception e) {
        String errorMsg = " Exception ";
        if (e != null) {
            errorMsg = e.getMessage();
            log.error(e.toString());
        }
        return new ResultUtil<Object>().setErrorMessage(errorMsg);
    }

}
