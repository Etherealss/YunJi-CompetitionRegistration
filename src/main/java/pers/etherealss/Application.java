package pers.etherealss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author 寒洲
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@MapperScan("pers.etherealss.mapper")
@SpringBootApplication()
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(pers.etherealss.Application.class, args);
    }

}
