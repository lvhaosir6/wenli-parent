package cn.lvhaosir.core.pojo.vo.ip;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}
