package com.erpapp.erpapp;

import com.erpapp.erpapp.filter.Auth;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class ErpAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpAppApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		source.registerCorsConfiguration("/**", config);
		registrationBean.setFilter(new CorsFilter(source));
		registrationBean.setOrder(0);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<Auth> filterRegistrationBean() {
		FilterRegistrationBean<Auth> registrationBean = new FilterRegistrationBean<>();
		Auth auth = new Auth();
		registrationBean.setFilter(auth);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
	}

}
