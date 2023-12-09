package com.accenture.auth.spring.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

// below 2 are achieved using AuthorizationServerEndpointsConfigurer
// Generates the access/bearer and refresh tokens
// enables the security end points and customizes them if required

// Below 2 are achieved using ClientDetailsServiceConfigurer
// client registration and providing the client credentials: username: my-trusted-client, password:secret
// defines the authorization scopes of the client

// The server issuing access tokens to the client after successfully authenticating the resource owner and obtaining authorization.
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	private static String REALM = "MY_OAUTH_REALM";

	@Autowired
	private AuthenticationManager authenticationManager;

	// Registers a client with client-id: my-trusted-client, password: secret, roles
	// & scope allowed.
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		System.out.println("***Method1 Authserver: configure(ClientDetailsServiceConfigurer clients)");
		clients.inMemory().withClient("my-trusted-client").secret("secret")

				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust")

				// Access token is only valid for 2 minutes.
				.accessTokenValiditySeconds(120).
				// Refresh token is only valid for 10 minutes.
				refreshTokenValiditySeconds(600);
	}

	// AuthorizationServerEndpointsConfigurer: defines the authorization and token
	// endpoints and the token services.
	// configures the token store token store can be of three types JDBC, Inmemory
	// and JWT
	// here in our example it is Inmemory TokenStore
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		System.out.println("***Method2 Authserver: configure(AuthorizationServerEndpointsConfigurer endpoints)");
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
	}

	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	// The only goal of this method is to define the realm in the sense of
	// the HTTP/1.1
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM + "/client");
	}

}
