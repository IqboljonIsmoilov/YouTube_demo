package com.company.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecuredFilterConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBeanRegion() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtFilter);

        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/profile/public/*");
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/tag/adm/*");
        bean.addUrlPatterns("/email/adm/*");
        bean.addUrlPatterns("/channel/adm/*");
        bean.addUrlPatterns("/channel/public/*");
        bean.addUrlPatterns("/playlist/adm/*");
        bean.addUrlPatterns("/playlist/public/*");
        bean.addUrlPatterns("/video/adm/*");
        bean.addUrlPatterns("/video/public/*");


        return bean;
    }

}
