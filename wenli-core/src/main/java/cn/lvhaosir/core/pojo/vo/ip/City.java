package cn.lvhaosir.core.pojo.vo.ip;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}