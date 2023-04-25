package tn.enicarthage.biblio.jwt;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

	@Autowired
	BiblioUsersDetailsService biblioUserDetailsService;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
	  @Bean
	    public AuthenticationManager authenticationManager(
	            AuthenticationConfiguration auth) throws Exception {
	        return auth.getAuthenticationManager();
	    }
/*new BCryptPasswordEncoder()*/
	  
	@Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
	
	/*
	@Bean(name= BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationManagerBean();	
	}*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
				.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/user/login","/user/signup","/user/get","/devoir/getAll","/devoir/delete/**","/devoir/ajouter","/devoir/update","/livre/get","/livre/ajouter")
				.permitAll()
				.and()
				.authorizeHttpRequests()
				.anyRequest()
				.authenticated()
				.and().exceptionHandling()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}


	@Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8081"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        return config;
    }

	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").allowedMethods("*");
	    }

	
	
}
