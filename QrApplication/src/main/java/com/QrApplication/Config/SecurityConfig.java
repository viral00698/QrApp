package com.QrApplication.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.QrApplication.AuthService.CustomAuthenticationEntryPoint;
import com.QrApplication.Filter.CsrfCookieFilter;
import com.QrApplication.Filter.JWTTokenGanrateFilter;
import com.QrApplication.Filter.JWTTokenValidatorFilter;
import com.QrApplication.Filter.RequestValidationBeforeFilter;
import com.QrApplication.SecurityConstant.SecurityConstent;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");
		CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		cookieCsrfTokenRepository.setCookieCustomizer(cookie->cookie .maxAge(3600) //set cookie secure for https 
		);
		
		
		http.
		    cors(corsCustomizer -> corsCustomizer.configurationSource(
//		    		new CorsConfigurationSource() 
		    		
		    		this.corsConfiguration()
//		    
//		    {
//		    @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200/**"));
//                config.setAllowedMethods(Collections.singletonList("*"));
//                config.setAllowCredentials(true);
//                config.setAllowedHeaders(Collections.singletonList("*")); // set required header in feture
//                config.setExposedHeaders(Arrays.asList("Authorization")); // 
//                config.setMaxAge(3600L);      
//           
//                return config;
//             } }
		    ))
		   
		    .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		    .addFilterAfter(new JWTTokenGanrateFilter(),BasicAuthenticationFilter.class)
		    .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
//		    .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		   
		   
			.sessionManagement(ss->ss. sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf->csrf.disable()            
//			.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//			.csrfTokenRepository(cookieCsrfTokenRepository)
	
//			.ignoringRequestMatchers("signup","/login","/custom-login","user","login" ,"/ws/**" , "api/v1/qr/order/**" , "saveProduct"  )
			)
			.authorizeHttpRequests(request->request
					.requestMatchers("/s1/**").hasAnyRole("USER","ADMIN")
					.requestMatchers("test","pg", "signup", "user","/login","login","/ws/**","api/v1/qr/order/**", "saveProduct" , "vendor/**" , "productlist/**" , "product/**").permitAll()
				    .anyRequest().authenticated())
			
			.httpBasic(Customizer.withDefaults())
			.exceptionHandling( ex->ex .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	CorsConfigurationSource corsConfiguration() {
		CorsConfiguration coreConfig = new CorsConfiguration();
	
//		coreConfig.setAllowedOrigins(Arrays.asList("http://localhost:*" , "http://localhost:8080/ws" , "ws://localhost:8080/ws","ws://localhost:4200/ws"	));
		coreConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "UPDATE", "OPTIONS"));
		coreConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Accept", "Authorization" , "x-xsrf-token")); //Authorization
		coreConfig.setExposedHeaders(Arrays.asList("Authorization" , "x-xsrf-token"));
		
		List<String> urls = new ArrayList<>();
		urls.add("http://15.207.112.139");
		urls.add("http://192.168.155.204:ws/*");
		urls.add("http://192.168.155.204:*");
		urls.add("http://13.232.231.237");
		urls.add("http://13.232.231.237:80/*");
		urls.add("http://13.232.231.237:8080/*");
		urls.add("http://15.207.112.139:ws/*");
		urls.add("http://65.0.124.230:ws");
		urls.add("http://3.109.202.129:*");
		urls.add("http://192.168.155.204");
		urls.add("http://192.168.52.204:*");
		urls.add("http://localhost:*");	
		urls.add("http://vitts.in:*");	
		urls.add("https://vitts.in:*");	
		
		urls.add("http://localhost:8080/ws");
		urls.add("http://"+SecurityConstent.IP_ADDRESS+":*");
//		urls.add("http://"+SecurityConstent.IP_ADDRESS+":4201/*");
	
		coreConfig.setAllowCredentials(true);
		coreConfig.setAllowedOriginPatterns(urls);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", coreConfig);
		return source;
	}
	
//	.formLogin(
//			formLogin -> 
//            formLogin
//                .loginPage("/custom-login")
//                .successHandler(new CustomAuthenticationSuccessHandler())
//                .permitAll()
//	)
	
}
