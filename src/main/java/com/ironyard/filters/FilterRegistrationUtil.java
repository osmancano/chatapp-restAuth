package com.ironyard.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jasonskipper on 2/7/17.
 */
@Configuration
public class FilterRegistrationUtil {
    @Bean
    public FilterRegistrationBean mvcSecutiryFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AuthFilter());
        registration.addUrlPatterns("/secure/*");
        return registration;
    }
    @Bean
    public FilterRegistrationBean restFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RestFilter());
        registration.addUrlPatterns("/rest/chatapp/*");
        return registration;
    }
}