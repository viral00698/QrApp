package com.QrApplication.SocketConfig;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.QrApplication.Filter.JwtSocketHandshakeInterceptor;
import com.QrApplication.SecurityConstant.SecurityConstent;


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
        .setAllowedOrigins("http://192.168.155.204:4201",
        		"http://"+SecurityConstent.IP_ADDRESS +":8080",
        		"http://"+SecurityConstent.IP_ADDRESS,
        		"http://"+SecurityConstent.IP_ADDRESS +":81",
        		"http://"+SecurityConstent.IP_ADDRESS +":80",
        		"https://qr.vitts.in",
        		"https://table.vitts.in",
        		"https://back.vitts.in",
        		"https://dash.vitts.in",
        		"http://10.105.240.204:4202",
        		"https://qr.vitts.in",
        		"https://table.vitts.in",
        		"https://back.vitts.in",
        		"https://dash.vitts.in",
        		"https://accept.vitts.in",
        		"https://back.vitts.in/app",
        		"http://"+SecurityConstent.IP_ADDRESS +":4202",
        		"http://"+SecurityConstent.IP_ADDRESS +":4202",
        		"http://vitts.in",
        		"https://vitts.in",
        		"http://localhost:80",
        		"http://127.0.0.1:80",
        		"http://localhost:4201",
        		"http://127.0.0.1:4202",
        		"http://"+SecurityConstent.IP_ADDRESS+":4201" )
        .withSockJS();
//        ArrayList<String> urls = new ArrayList<>();
//        urls.add("http://localhost:4200");
    }
    
//    .setAllowedOrigins("http://"+SecurityConstent.IP_ADDRESS +":4202" , "http://localhost:4201","http://"+SecurityConstent.IP_ADDRESS+":4201")
	
}
