package cn.lvhaosir.browser.pojo.vo.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class DateSearchVo implements Serializable {

    private String startDate;

    private String endDate;
}