package cn.lvhaosir.core.pojo.po;

import cn.lvhaosir.core.pojo.base.BaseEntity;
import cn.lvhaosir.core.utils.ObjectUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

/**
 * Created by lvhaosir on 2018/10/7.
 *  日志记录
 */
@Data
@Entity
@Table(name = "tb_log")
public class Log extends BaseEntity {

    @ApiModelProperty(value = "方法操作名称")
    private String name;

    @ApiModelProperty(value = "请求路径")
    private String requestUrl;

    @ApiModelProperty(value = "请求类型")
    private String requestType;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "请求用户")
    private String username;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "ip信息")
    private String ipInfo;

    @ApiModelProperty(value = "花费时间")
    private Integer costTime;

    /**
     *  转换请求参数为 Json
     * @param paramMap
     */
    public void setMapToParams(Map<String,String[]> paramMap) {
        this.requestParam = ObjectUtil.mapToString(paramMap);
    }

}
