package com.ppk.config.feign;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.Retryer;

@Configuration
public class FeignConfiguration {

	@Value("${feign.maxAttempts:1}")
	private int		feignMaxAttempts;

	@Bean
	Retryer feignRetryer() {
		return new Retryer.Default(100, SECONDS.toMillis(1), feignMaxAttempts);
	}

	@Bean
	public Contract feignContract() {
		return new feign.Contract.Default();
	}

}