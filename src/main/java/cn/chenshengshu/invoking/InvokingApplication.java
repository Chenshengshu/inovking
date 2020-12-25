package cn.chenshengshu.invoking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("cn.chenshengshu.invoking.models.**.dao")
public class InvokingApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvokingApplication.class, args);
    }

}
