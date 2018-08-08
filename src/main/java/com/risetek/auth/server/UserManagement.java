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
		
		UserInformation wangp = new UserInformation();
		wangp.password = "wangp";
		wangp.roles.add("admin");
		wangp.roles.add("maintenance");
		wangp.roles.add("operator");
		wangp.roles.add("visitor");
		wangp.teams.add(new Integer(-1));

		users.put("wangyc", wangyc_visitor);
		users.put("wangyc@risetek.com", wangyc);
		users.put("test@risetek.com", tester);
		users.put("szw@risetek.com", tester1);
		users.put("sdy@risetek.com", tester13);
		users.put("zhangl@risetek.com", zhangl);
		users.put("wangxu@risetek.com", wangx);
		users.put("wangp@ccs", wangp);
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
			System.out.println("get user list size:" + list.size());
			if(list.size() > 0) {
				if(Arrays.equals(password, list.get(0).getPasswd().toCharArray()))
					return true;
			}
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
/*	
	
	private Map<String, String> tokens = new HashMap<>();
	private Map<String, String> accessTokens = new HashMap<>();

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
*/
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
