package com.ui.automation.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "com.ui.automation")
@Configuration
@PropertySource("classpath:application.properties")
public class FrameworkSpringConfiguration {
}
