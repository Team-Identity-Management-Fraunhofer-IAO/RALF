package de.ralf.threatmasterkitchen.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role from authorities join user_group_association on (user_group_association.groupname = authorities.groupname) where username=?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login*").permitAll().antMatchers("/styles/**")
				.permitAll().antMatchers("/img/**").permitAll().antMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.loginPage("/login.html").loginProcessingUrl("/login").defaultSuccessUrl("/ServiceDefinition", true)
				.failureUrl("/login.html?error=true").and().logout().logoutUrl("/perform_logout")
				.deleteCookies("JSESSIONID");
		
	}
	

}
