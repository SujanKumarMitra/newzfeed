package com.herokuapp.newsfeedapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.herokuapp.newsfeedapp.service.OAuth2UserServiceImpl;
import com.herokuapp.newsfeedapp.service.OidcUserServiceImpl;
import com.herokuapp.newsfeedapp.service.UserDetailsServiceImpl;

/**
 * This class contains the security configuration of Spring Security
 * 
 * @since 2020-05-15
 * @author Sujan Kumar Mitra
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * implementation of {@link UserDetailsService}
	 * 
	 * @see UserDetailsServiceImpl
	 */
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * implementation of OpenID Connect 1.0 authorization
	 * 
	 * @see OidcUserServiceImpl
	 */
	@Autowired
	private OidcUserServiceImpl oidcUserServiceImpl;

	/**
	 * implementation of OAuth2 authorization
	 * 
	 * @see OAuth2UserServiceImpl
	 */
	@Autowired
	private OAuth2UserServiceImpl oAuth2UserServiceImpl;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/headlines**", "/category/**", "/search**", "/signup", "/about")
					.permitAll()
				.antMatchers("/**/*.css", "/**/*.js", "/**/*.png")
					.permitAll()
				.antMatchers("/actuator/**")
					.hasRole("ADMIN")
				.antMatchers("/profile/**","/custom**")
					.authenticated()
			.and()
				.formLogin()
					.permitAll()
					.loginPage("/login")
			.and()
				.logout()
					.logoutSuccessUrl("/")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
			.and()
				.oauth2Login()
					.loginPage("/login").permitAll()
						.authorizationEndpoint()
							.baseUri("/login/oauth/")
				.and()
					.userInfoEndpoint()
						.oidcUserService(oidcUserServiceImpl)
						.userService(oAuth2UserServiceImpl);
	}

	/**
	 * The application uses Bcrypt hashing algorithm.
	 * 
	 * @return instance of {@link BCryptPasswordEncoder}
	 * @see BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
