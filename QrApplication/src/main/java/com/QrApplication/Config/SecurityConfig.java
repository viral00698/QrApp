package com.QrApplication.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import jakarta.servlet.http.HttpServletRequest;

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
		   
		    .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		    .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		    .addFilterAfter(new JWTTokenGanrateFilter(),BasicAuthenticationFilter.class)
		    .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
		   
			.sessionManagement(ss->ss. sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf->csrf 
			.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
			.csrfTokenRepository(cookieCsrfTokenRepository)
	
			.ignoringRequestMatchers("signup","/login","/custom-login","user","login")  )
			.authorizeHttpRequests(request->request
					.requestMatchers("/s1/**").hasAnyRole("USER","ADMIN")
					.requestMatchers("test", "signup", "user","/login","login").permitAll()
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
		coreConfig.setAllowedOrigins(Arrays.asList("http://localhost:*"));
		coreConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "UPDATE", "OPTIONS"));
		coreConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Accept", "Authorization")); //Authorization
		coreConfig.setExposedHeaders(Arrays.asList("Authorization" , "x-xsrf-token"));
		
		List<String> urls = new ArrayList<>();
		
		urls.add("http://localhost:*");
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
