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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
		    cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
		    @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
         
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                
                List<String> ss = new ArrayList<>();
                ss.add("GET");
                ss.add("POST");
                ss.add("PUT");
                ss.add("DELETE");
                ss.add("HEAD");
                ss.add("OPTIONS");
                config.setAllowedMethods(ss);
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*")); // set required header in feture
                config.setExposedHeaders(Arrays.asList("Authorization")); // 
                config.setMaxAge(3600L);
                           
                return config;
             } }))
		   
		    .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		    .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		    .addFilterAfter(new JWTTokenGanrateFilter(),BasicAuthenticationFilter.class)
		    .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
		   
			.sessionManagement(ss->ss. sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf->csrf 
			.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
			.csrfTokenRepository(cookieCsrfTokenRepository)
	
			.ignoringRequestMatchers("/auth/signup","login","/custom-login","user","/ws/**" , "ws://localhost:8080")  )
			.authorizeHttpRequests(request->request
					.requestMatchers("/testSecureAdmin/**").hasAnyRole("USER","ADMIN")
					.requestMatchers("/testSecureAdmin1/**").hasRole("USER")
					.requestMatchers("test", "/auth/signup", "user","login","test/**" , "ws/**").permitAll()
				    .anyRequest().authenticated())
			
			.httpBasic(Customizer.withDefaults())
			.exceptionHandling( ex->ex .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
		return http.build();
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	.formLogin(
//			formLogin -> 
//            formLogin
//                .loginPage("/custom-login")
//                .successHandler(new CustomAuthenticationSuccessHandler())
//                .permitAll()
//	)
	
}
