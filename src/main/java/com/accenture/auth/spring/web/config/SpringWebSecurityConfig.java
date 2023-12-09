package com.accenture.auth.spring.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//These annotations are used to declare this file as the Security File
@Configuration
@EnableWebSecurity 
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {	
	
	//Responsibility to define the authorizationRules is of ResourceServer
	//hence nothing written here .
	
	//Responsibility of validating client and generating the tokens is of
	//AuthorizationServer. 
	
	//Responsibility of validating the access token is collective responsibility 
	//of ResourceSever and Authorization server.
	
	// This interaction is not standardized for various grant types and 
	// for various Token strategies: InMemory, JDBC and JWT
	
	//In the Following method it is told to enable OAuth 2.0 URLs with anonymous access
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("***Main Security Config: configure(HttpSecurity http)");
	       http
	        .csrf().disable()
	        .anonymous().disable()
	        .authorizeRequests()
	         //disabling the authentication for oauth endpoints
	         //TokenEndpoint is used to service requests for access tokens. Default URL: /oauth/token.
	        .antMatchers("/oauth/token").permitAll();	
	}
    //AuthenticationProviderManager is used to configure resource owners.
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("priya").password("priya123").roles("DUMMY_ADMIN")
        .and()
        .withUser("shreya").password("shreya123").roles("USER");
    }
    
  //Use builder pattern only to define user, don't use any other way otherwise it will not work
}
