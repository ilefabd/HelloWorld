package com.info;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Autowired
	private SimpleAuthenticationSuccessHandler successHandler;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
		jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
	
	
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		   http.
			authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/prefix/**").permitAll()
			.antMatchers("/technologie/**").permitAll()
			.antMatchers("/home").permitAll()
			.antMatchers("/registration/**").permitAll()
			.antMatchers("/user/**").permitAll()

			.antMatchers("/admin/**").hasAuthority(rolesQuery="ADMIN")
			.and().authorizeRequests().antMatchers("/technical/**").hasAuthority(rolesQuery="technicien")
			
			.and().authorizeRequests().antMatchers("/financier/**").hasAuthority(rolesQuery="Financier")
			.and()
			.formLogin().successHandler(successHandler)
			.loginPage("/login").failureUrl("/index?error=true")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().
			 logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/index").and().exceptionHandling()
			.accessDeniedPage("/default");
			
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
