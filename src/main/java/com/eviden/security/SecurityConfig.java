package com.eviden.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eviden.service.SocioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthEntryPoint authEntryPoint;
	
	@Autowired
	private RequestFilter requestFilter;
	
	
	 @Bean
	    SocioService userDetailsService() {
			return new SocioService();
		}
	    
		// Método que nos suministrará la codificación
		@Bean
		BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		// Método que autentifica
		@Bean
		DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());

			return authProvider;
		}

		 @Bean
		 AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		    return authConfig.getAuthenticationManager();
		  }
		
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authEntryPoint))
	        .authorizeHttpRequests((requests) -> {
			requests
				.requestMatchers("/signin", "socio", "socioByEmail/**", "socioByDni/**").permitAll()
				.requestMatchers( "/socios/patrones", "socios", "socio/**", "barcos", "barco/**", "salidas/barco/**", "salida/**", "barcoByNumAmarre/**", "barcoByMatricula/**", "salidas", "salidas/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "socio/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "socio/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "barco").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.PUT, "barco/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "barco/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "salida").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.PUT, "salida/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "salida/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().denyAll();
	        })
	        .formLogin((form) -> form.permitAll())
	        .logout((logout) -> logout.permitAll().logoutSuccessUrl("/"));
		
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
