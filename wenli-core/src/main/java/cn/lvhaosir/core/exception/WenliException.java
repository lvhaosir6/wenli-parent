package cn.lvhaosir.core.exception;

import lombok.Data;

/**
 * Created by lvhaosir on 2018/9/20.
 */
@Data
public class WenliException extends RuntimeException {
    private String msg;

    public WenliException(String msg){
        super(msg);
        this.msg = msg;
    }
}
