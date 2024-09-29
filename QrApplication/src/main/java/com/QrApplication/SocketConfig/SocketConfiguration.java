package com.QrApplication.SocketConfig;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.QrApplication.Filter.JwtSocketHandshakeInterceptor;

import io.jsonwebtoken.lang.Arrays;

@Configuration
@EnableWebSocketMessageBroker
public class SocketConfiguration implements WebSocketMessageBrokerConfigurer{

	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic" , "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
        .addInterceptors(new JwtSocketHandshakeInterceptor())   
        .setAllowedOrigins("http://192.168.1.16:4202" , "http://localhost:4201","http://192.168.1.16:4201")
        .withSockJS();
//        ArrayList<String> urls = new ArrayList<>();
//        urls.add("http://localhost:4200");
    }
    
    
	
}
