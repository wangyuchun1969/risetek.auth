package com.risetek.auth.server.shiro;

import org.apache.shiro.authc.AuthenticationToken;

@SuppressWarnings("serial")
public class OAuthToken implements AuthenticationToken {
    private String authCode;
    private String principal;
    public OAuthToken(String authCode) {
        this.authCode = authCode;
    }
	@Override
	public Object getPrincipal() {
		return principal;
	}
	@Override
	public Object getCredentials() {
		return authCode;
	}
	
	public String getAuthCode() {
		return authCode;
	}
}