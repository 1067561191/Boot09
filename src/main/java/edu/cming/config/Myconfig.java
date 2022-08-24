package edu.cming.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties") // 个人信息配置文件 --存在中文乱码问题
@ComponentScan(value = {"edu.cming"}) // 组件包扫描
public class Myconfig {
}
