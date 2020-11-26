package cn.lvhaosir.browser.pojo.vo.condition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Data
public class PageVo implements Serializable {

    @ApiModelProperty(value = "页号")
    private int pageNumber;

    @ApiModelProperty(value = "页面大小")
    private int pageSize;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;

}
