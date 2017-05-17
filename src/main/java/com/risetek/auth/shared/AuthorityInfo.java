package com.risetek.auth.shared;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorityInfo implements IsSerializable {
	public AuthorityInfo(){}
	public boolean isLogin = false;

	private Map<String, Boolean> roles = new HashMap<String, Boolean>();
	
	public Map<String, Boolean> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, Boolean> roles) {
		this.roles = roles;
	}
	
	public boolean isAdmin() {
		return hasRole("admin");
	}

	public boolean hasRole(String roleName) {
		Boolean value = roles.get(roleName);
		return ((null != value) && (value.booleanValue()));
	}
}
