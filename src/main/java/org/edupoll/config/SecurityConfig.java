package org.edupoll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	
	
	
//	@Bean
//	public UserDetailsService users(DataSource dataSource) {
//		var userDetailService = new JdbcUserDetailsManager(dataSource);
//		
//		// 디폴트 스키마와 테이블 구성이 다르면 쿼리를 바꿔주면 된다.
//		userDetailService.setUsersByUsernameQuery("SELECT ID, PASSWORD, ENABLED FROM USERS WHERE ID=?");
//		userDetailService.setAuthoritiesByUsernameQuery("SELECT USER_ID, ROLE FROM USER_ROLES WHERE USER_ID=?");
//		
//		return userDetailService;
//	}
	
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
//		http.csrf(t -> t.disable());
		
		http.authorizeHttpRequests(t-> t
				.requestMatchers("/", "/login", "login-task", "join", "join-task").permitAll()
				.requestMatchers("/admin/**").hasRole("admin")
				.requestMatchers("/manager/**").hasAnyRole("manager", "admin")
				.anyRequest().authenticated()
			);
						
		http.formLogin(form -> form
				.usernameParameter("loginId") // 파라미터 바꾸기
				.passwordParameter("loginPass") // 파라미터 바꾸기
				.loginPage("/login") // 로그인 페이지 커스텀 (컨트롤러)
				.loginProcessingUrl("/login-task") // 로그인 처리 페이지 주소변경(만드는건X)
				.defaultSuccessUrl("/moim/list") // 로그인 성공시 보내는곳
			);
		
		http.logout(t -> t
				.logoutSuccessUrl("/login"));
		
		http.exceptionHandling(t -> t
				.accessDeniedPage("/access/dinied"));
		
		return http.build();
	}
}
