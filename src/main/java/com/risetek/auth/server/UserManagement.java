package com.risetek.auth.server;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.OpenAuthInfo;
import com.risetek.auth.shared.UserSecurityEntity;

@Singleton
public class UserManagement {
	
	@Inject
	private DbManagement dbManagement;
	
	/*
	 * Roles:
	 * admin
	 * operator
	 * visitor
	 * maintenance
	 */

	public UserManagement() {
	}
	
	public boolean isValid(String username, char[] password) {
		if(null == username || null == password)
			return false;
		try {
			List<AccountEntity> list = dbManagement.getAccount(username);
			if(list.size() > 0 && Arrays.equals(password, list.get(0).getPassword().toCharArray()))
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isValid2(String username, char[] password) {
		if(null == username || null == password)
			return false;
		try {
			List<UserSecurityEntity> list = dbManagement.getUserSecurity(username);
			if(list.size() > 0 && Arrays.equals(password, list.get(0).getPasswd().toCharArray()))
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getRoles(String username) {
		if(null == username)
			return null;
		try {
			List<AccountEntity> list = dbManagement.getAccount(username);
			if(list.size() > 0)
				return list.get(0).getRoles();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	public String getTeams(String username) {
		if(null == username)
			return null;
		try {
			List<AccountEntity> list = dbManagement.getAccount(username);
			if(list.size() > 0)
				return list.get(0).getTeams();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return null;
	}

	private Map<String, OpenAuthInfo> tokenInfo = new HashMap<>();
	public void setInfoByToken(String token, OpenAuthInfo info) {
		tokenInfo.put(token, info);
	}
	
	public OpenAuthInfo getInfoByToken(String token) {
		return tokenInfo.get(token);
	}

	private Map<String, OpenAuthInfo> accessInfo = new HashMap<>();
	public void setInfoByAccessToken(String token, OpenAuthInfo info) {
		accessInfo.put(token, info);
	}
	public OpenAuthInfo getInfoByAccessToken(String token) {
		return accessInfo.get(token);
	}
}
