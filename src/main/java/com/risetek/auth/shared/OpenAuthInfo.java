package com.risetek.auth.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OpenAuthInfo implements IsSerializable {
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String callback_url;
	private String token;
}
