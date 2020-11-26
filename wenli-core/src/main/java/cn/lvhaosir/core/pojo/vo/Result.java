package cn.lvhaosir.core.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by lvhaosir on 2018/9/16.
 * 前后端交互数据标准
 */
@Getter
@Setter
@ToString
public class Result<T> implements Serializable {
    /**
     *  成功或失败
     */
    private boolean success;
    /**
     *  失败消息
     */
    private String message;
    /**
     *  返回代码
     */
    private Integer code;
    /**
     *  时间戳
     */
    private long timestamp = System.currentTimeMillis();
    /**
     *  数据
     */
    private T result;

}
