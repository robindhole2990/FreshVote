package com.codenetworkz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("robindhole@gmail.com").password(getPasswordEncoder().encode("redhat")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests().antMatchers("/").permitAll()
			.anyRequest().hasRole("USER").and()
			.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/dashboard")
				.and()
				.logout()
					.logoutUrl("/logout").permitAll()
					.logoutSuccessUrl("/");
				
	}

	
	
}