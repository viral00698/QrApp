package com.QrApplication.Config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class MyCustomThreadPool implements AsyncConfigurer{
	
	@Override
	@Bean
	@Primary  // Mark this bean as primary
	public Executor getAsyncExecutor() {
		
		Integer MAX_POOL_SIZE = 10;
		Integer CORE_POOL_SIZE = 5;
		Integer QUEUE_CAPACITY = 20;
		
		String PRIFIX = "MyCustomThred:";
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("My Custom Thred pool");
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		
		taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
		taskExecutor.setThreadNamePrefix(PRIFIX);
		taskExecutor.initialize();
		
		return taskExecutor;
	}
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		 return (throwable, method, obj) -> {
	            System.err.println("Exception in async method '" + method.getName() + "' - " + throwable);
	     };
	}

}
