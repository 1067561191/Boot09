package edu.cming.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties")
@ComponentScan(value = {"edu.cming"})
public class Myconfig {
}
