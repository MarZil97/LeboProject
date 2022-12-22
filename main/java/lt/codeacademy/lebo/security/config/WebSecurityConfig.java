package lt.codeacademy.lebo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;
import lt.codeacademy.lebo.appuser.AppUserRole;
import lt.codeacademy.lebo.appuser.AppUserService;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

	private final AppUserService appUserService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf().disable()
			.authorizeRequests()
			//.antMatchers("/filluserprofile/{id}", "/userprofile{id}")
			//.hasRole(AppUserRole.USER.getRole())
			.antMatchers("/index/*", "/css/*", "/js/*", "/images/*", "/signup", "/", "/index", "/signuser", "/signup/successfull", "/login", "/perform_login", "/login.html", "/filluserprofile/{id}", "/userprofile/{id}", "/buyer/index", "/uploadform/profileimage", "/upload", "/editProfile/{id}", "updateProfile/{id}")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/filluserprofile/{id}");
		
		return http.build();
			
			
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(appUserService);
		
		return provider;
	}
	
}
