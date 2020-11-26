package cn.lvhaosir.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
/**
 * Created by lvhaosir on 2018/9/17.
 */
@SpringBootApplication
//启用JPA审计
@EnableJpaAuditing
// 开启缓存
@EnableCaching
@ComponentScan(value = "cn.lvhaosir")
public class WenliBrowser {
    public static void main(String[] args) {
        SpringApplication.run(WenliBrowser.class,args);
    }

}
