package me.jaeuk.hr_module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackageClasses = HrModuleApplication.class)
@SpringBootApplication
public class HrModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrModuleApplication.class, args);
    }

}
