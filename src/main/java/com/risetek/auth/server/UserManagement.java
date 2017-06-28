package com.risetek.auth.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.inject.Singleton;

@Singleton
public class UserManagement {
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
	
	private Map<String, String> tokens = new HashMap<>();
	private Map<String, String> accessTokens = new HashMap<>();

	public UserManagement() {
		UserInformation wangyc = new UserInformation();
		wangyc.password = "gamelan";
		wangyc.roles.add("admin");
		wangyc.roles.add("developer");
		wangyc.roles.add("maintenance");
		wangyc.roles.add("operator");
		wangyc.roles.add("visitor");
		wangyc.teams.add(new Integer(-1));
		
		UserInformation wangyc_visitor = new UserInformation();
		wangyc_visitor.password = "gamelan";
		wangyc_visitor.roles.add("visitor");

		UserInformation tester = new UserInformation();
		tester.password = "test";
		tester.roles.add("visitor");
		tester.teams.add(new Integer(0));

		UserInformation tester1 = new UserInformation();
		tester1.password = "szw";
		tester1.roles.add("visitor");
		tester1.teams.add(new Integer(17));

		UserInformation tester13 = new UserInformation();
		tester13.password = "sdy";
		tester13.roles.add("visitor");
		tester13.teams.add(new Integer(13));
		
		UserInformation zhangl = new UserInformation();
		zhangl.password = "zhangl";
		zhangl.roles.add("admin");
		zhangl.roles.add("maintenance");
		zhangl.roles.add("operator");
		zhangl.roles.add("visitor");
		zhangl.teams.add(new Integer(-1));

		UserInformation wangx = new UserInformation();
		wangx.password = "wangxu";
		wangx.roles.add("admin");
		wangx.roles.add("maintenance");
		wangx.roles.add("operator");
		wangx.roles.add("visitor");
		wangx.teams.add(new Integer(-1));
		
		users.put("wangyc", wangyc_visitor);
		users.put("wangyc@risetek.com", wangyc);
		users.put("test@risetek.com", tester);
		users.put("szw@risetek.com", tester1);
		users.put("sdy@risetek.com", tester13);
		users.put("zhangl@risetek.com", zhangl);
		users.put("wangxu@risetek.com", wangx);
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
	
	public void setToken(String username, String token) {
		tokens.put(token, username);
	}

	public String getUsernameByToken(String token) {
		return tokens.get(token);
	}
	
	public void setAccessToken(String username, String token) {
		accessTokens.put(token, username);
	}

	public String getUsernameByAccessToken(String token) {
		return accessTokens.get(token);
	}

}
