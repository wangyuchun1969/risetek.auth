package com.risetek.auth.shared;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorityInfo implements IsSerializable {
	public Map<String, Boolean> getRoles() {
		return roles;
	}
	public void setRoles(Map<String, Boolean> roles) {
		this.roles = roles;
	}
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public void setCurrentAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCurrentAccountName() {
		return accountName;
	}
	private Map<String, Boolean> roles = new HashMap<String, Boolean>();
	private String realm;
	private boolean isLogin = false;
	private String accountName;
	
	public boolean hasRole(String roleName) {
		Boolean value = roles.get(roleName);
		return ((null != value) && (value.booleanValue()));
	}
}
