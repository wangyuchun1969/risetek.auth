package com.risetek.auth.server;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.inject.Inject;
import com.google.inject.Singleton;
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
	public class UserInformation {
		String password;
		public List<Integer> teams = new Vector<Integer>();
		List<String> roles = new Vector<String>();
	}

	private Map<String, UserInformation> users = new HashMap<>();

	public UserManagement() {
		UserInformation wangyc = new UserInformation();
		wangyc.password = "gamelan";
		wangyc.roles.add("admin");
		wangyc.roles.add("developer");
		wangyc.roles.add("maintenance");
		wangyc.roles.add("operator");
		wangyc.roles.add("visitor");
		wangyc.teams.add(new Integer(-1));
		users.put("wangyc@risetek.com", wangyc);
	}
	
	public UserInformation getUserInfomation(String username) {
		UserInformation user = users.get(username);
		return user;
	}
	
	public boolean isValid(String username, char[] password) {
		UserInformation user = users.get(username);
		if(null == user || null == password || null == user.password)
			return false;
		
		if(Arrays.equals(password, user.password.toCharArray()))
			return true;

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
	
	public List<String> getRoles(String username) {
		UserInformation user = users.get(username);
		if(null != user)
			return user.roles;
		return null;
	}
	
	public List<Integer> getTeams(String username) {
		UserInformation user = users.get(username);
		if(null != user)
			return user.teams;
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
