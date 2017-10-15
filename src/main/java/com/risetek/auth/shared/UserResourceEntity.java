package com.risetek.auth.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserResourceEntity implements IsSerializable {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSecurityId() {
		return securityId;
	}
	public void setSecurityId(int securityId) {
		this.securityId = securityId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	private int id;
	private int securityId;
	private int appId;
	private String json;
}
