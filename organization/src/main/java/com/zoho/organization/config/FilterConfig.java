package com.zoho.organization.config;

import com.zoho.organization.filter.AuthKeyFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final AuthKeyFilter authKeyFilter;

    @Bean
    public FilterRegistrationBean<AuthKeyFilter> registerAuthFilter(){
        FilterRegistrationBean<AuthKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authKeyFilter);
        registrationBean.addUrlPatterns("/api/org/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}