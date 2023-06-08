package org.edupoll.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public UserDetailsService users(DataSource dataSource) {
		var userDetailService = new JdbcUserDetailsManager(dataSource);
		
		// 디폴트 스키마와 테이블 구성이 다르면 쿼리를 바꿔주면 된다.
		userDetailService.setUsersByUsernameQuery("SELECT ID, PASSWORD, ENABLED FROM USERS WHERE ID=?");
		userDetailService.setAuthoritiesByUsernameQuery("SELECT USER_ID, ROLE FROM USER_ROLES WHERE USER_ID=?");
		
		return userDetailService;
	}
	
//	@Bean
//	public UserDetailsService users() {
//		UserDetails user = User.builder()
//			.username("aaa")
//			.password("{noop}1234")
//			.roles("user")
//			.build();
//		UserDetails admin = User.builder()
//			.username("bbb")
//			.password("{noop}1234")
//			.roles("user", "admin")
//			.build();
//		UserDetails u3 = User.builder()
//			.username("ccc")
//			.password("{noop}1234")
//			.roles("user", "admin", "manager")
//			.build();
//		return new InMemoryUserDetailsManager(user, admin, u3);
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(t -> t.disable());
		
		http.authorizeHttpRequests(t-> t
				.requestMatchers("/login", "login-task").permitAll()
				.requestMatchers("/admin").hasRole("admin")
				.requestMatchers("/manager").hasRole("manager")
				.anyRequest().authenticated()
			);
						
		http.formLogin(form -> form
				.loginPage("/login").loginProcessingUrl("/login-task")
			);
		
		return http.build();
	}
}
