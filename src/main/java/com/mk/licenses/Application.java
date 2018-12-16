package com.mk.licenses;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.mk.license.util.UserContextInterceptor;
import com.mk.licenses.controllers.UserContextFilter;


@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class Application {
	  
@LoadBalanced
  @Bean
  public RestTemplate getRestTemplate(){
	RestTemplate restTemplate=new RestTemplate(); 
	List<ClientHttpRequestInterceptor> interceptors=restTemplate.getInterceptors();
	if(interceptors==null)
	{
		restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
	}
	else {
		interceptors.add(new UserContextInterceptor());
	}
	return restTemplate;
}


/*@LoadBalanced
@Bean
public RestTemplate getRestTemplate(){
      return new RestTemplate();
  }*/
	  
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
