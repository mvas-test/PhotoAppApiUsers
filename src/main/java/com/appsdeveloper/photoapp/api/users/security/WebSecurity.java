package com.appsdeveloper.photoapp.api.users.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloper.photoapp.api.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
		
	@Autowired
	public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.environment = environment;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/users/**").permitAll().and().addFilter(getAuthenticationFilter());
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
//		http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"));
		http.headers().frameOptions().disable();
	}

	private Filter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		
		// add custom login path (optional) default = /login
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		
		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
