package com.accenture.auth.spring.web.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
 
@Configuration
//Configures OAuth2AuthenticationProcessingFilter in Spring Security Filter chain.
//This filter will be used to service the requests protected by access token.
@EnableResourceServer 
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
 
	//Note: It is optional to configure the resource id for the resource server. 
	//Same can be configured using below code:
    private static final String RESOURCE_ID = "my_rest_api";
     
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }	 
	//It recommended to configure the resource server with the Id even though it is optional.
	//This id, if present, will be internally referred by the Authorization server during 
	//the interaction with Resource server.

    //Configuring Access Levels. These are given as input to the OAuth2AuthenticationProcessingFilter
    //configured by @EnableResourceServer 
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	System.out.println("***Resource Server: configure(HttpSecurity http)");
        http.
        anonymous().disable()
        .requestMatchers().antMatchers("/emp/**")
        .and()
        .authorizeRequests()
        .antMatchers("/emp/controller/getDetails/**").access("hasRole('DUMMY_ADMIN') or hasRole('USER') ")
        .antMatchers("/emp/controller/getDetailsById/**").access("hasRole('DUMMY_ADMIN') or hasRole('USER')")
        .antMatchers("/emp/controller/addEmp/**").access("hasRole('DUMMY_ADMIN')")
        .antMatchers("/emp/controller/updateEmp/**").access("hasRole('DUMMY_ADMIN')")
        .antMatchers("/emp/controller/deleteEmp/**").access("hasRole('DUMMY_ADMIN')")
        
        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
    
}

/*
 * @EnableResourceServer is a convenient annotation for OAuth2 Resource Servers, it enables a Spring Security filter that authenticates requests via an incoming OAuth2 token.

	The configure(HttpSecurity http) method configures the access rules and request matchers (path) for protected resources using the HttpSecurity class. 
	We secure the URL paths /emp/*, only allowing authenticated users who have DUMMY_ADMIN role to access it.
	The server hosting the protected resources, capable of accepting and responding to protected resource requests using access tokens
 * */
