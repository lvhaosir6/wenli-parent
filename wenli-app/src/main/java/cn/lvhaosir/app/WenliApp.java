package cn.lvhaosir.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by lvhaosir on 2018/10/12.
 */
@SpringBootApplication
//启用JPA审计
@EnableJpaAuditing
@ComponentScan(value = "cn.lvhaosir")
public class WenliApp {

    public static void main(String[] args) {
        SpringApplication.run(WenliApp.class,args);
    }

}
