package cn.lvhaosir.core.utils;

import cn.lvhaosir.core.pojo.vo.Result;

/**
 * Created by lvhaosir on 2018/9/16.
 * 结果返回集工具类
 */
public class ResultUtil<T> {

    private Result<T> result;

    /**
     *  请求成功，默认消息返回 success 无数据返回
     */
    public ResultUtil() {
        result = new Result<T>();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode(200);
    }

    /**
     *  设置数据
     * @param t 返回的数据
     * @return
     */
    public Result<T> setData(T t) {
        this.result.setResult(t);
        this.result.setCode(200);
        return this.result;
    }

    /**
     *  请求成功，返回相应消息
     * @param message
     * @return
     */
    public Result<T> setSuccessMessage(String message) {
        this.result.setSuccess(true);
        this.result.setMessage(message);
        this.result.setCode(200);
        this.result.setResult(null);
        return this.result;
    }

    /**
     *  请求失败，返回相应消息 code:500
     * @param message
     * @return
     */
    public Result<T> setErrorMessage(String message){
        this.result.setSuccess(false);
        this.result.setMessage(message);
        this.result.setCode(500);
        return this.result;
    }

    /**
     *  请求失败，返回相应消息和相应 code
     * @param code
     * @param message
     * @return
     */
    public Result<T> setErrorMessage(Integer code, String message){
        this.result.setSuccess(false);
        this.result.setMessage(message);
        this.result.setCode(code);
        return this.result;
    }

}
