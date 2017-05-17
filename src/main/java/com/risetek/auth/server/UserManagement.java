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
	public class UserInfomation {
		String password;
		public List<Integer> teams = new Vector<Integer>();
		List<String> roles = new Vector<String>();
	}

	private Map<String, UserInfomation> users = new HashMap<>();;

	public UserManagement() {
		UserInfomation wangyc = new UserInfomation();
		wangyc.password = "gamelan";
		wangyc.roles.add("admin");
		wangyc.roles.add("developer");
		wangyc.roles.add("maintenance");
		wangyc.roles.add("operator");
		wangyc.roles.add("visitor");
		wangyc.teams.add(new Integer(0));
		wangyc.teams.add(new Integer(1));
		wangyc.teams.add(new Integer(2));
		wangyc.teams.add(new Integer(13));
		wangyc.teams.add(new Integer(17));
		wangyc.teams.add(new Integer(19));
		
		UserInfomation wangyc_visitor = new UserInfomation();
		wangyc_visitor.password = "gamelan";
		wangyc_visitor.roles.add("visitor");

		UserInfomation tester = new UserInfomation();
		tester.password = "test";
		tester.roles.add("visitor");
		tester.teams.add(new Integer(0));
		tester.teams.add(new Integer(1));

		UserInfomation tester1 = new UserInfomation();
		tester1.password = "szw";
		tester1.roles.add("visitor");
		tester1.teams.add(new Integer(17));

		UserInfomation tester13 = new UserInfomation();
		tester13.password = "sdy";
		tester13.roles.add("visitor");
		tester13.teams.add(new Integer(13));
		
		UserInfomation zhangl = new UserInfomation();
		zhangl.password = "zhangl";
		zhangl.roles.add("admin");
		zhangl.roles.add("maintenance");
		zhangl.roles.add("operator");
		zhangl.roles.add("visitor");
		zhangl.teams.add(new Integer(0));
		zhangl.teams.add(new Integer(1));
		zhangl.teams.add(new Integer(2));
		zhangl.teams.add(new Integer(13));
		zhangl.teams.add(new Integer(17));
		zhangl.teams.add(new Integer(19));
		
		users.put("wangyc", wangyc_visitor);
		users.put("wangyc@risetek.com", wangyc);
		users.put("test@risetek.com", tester);
		users.put("szw@risetek.com", tester1);
		users.put("sdy@risetek.com", tester13);
		users.put("zhangl@risetek.com", zhangl);
	}
	
	public UserInfomation getUserInfomation(String username) {
		UserInfomation user = users.get(username);
		return user;
	}
	
	public boolean isValid(String username, char[] password) {
		UserInfomation user = users.get(username);
		if(null == user || null == password)
			return false;
		
		if(Arrays.equals(password, user.password.toCharArray()))
			return true;

		return false;
	}
	
	public List<String> getRoles(String username) {
		UserInfomation user = users.get(username);
		if(null != user)
			return user.roles;
		return null;
	}
	
	public List<Integer> getTeams(String username) {
		UserInfomation user = users.get(username);
		if(null != user)
			return user.teams;
		return null;
	}
}
