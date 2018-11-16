package com.risetek.auth.server.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.inject.Inject;
import com.risetek.auth.server.UserManagement;

public class MyAuthorizingRealm extends AuthorizingRealm {

	@Inject
	private UserManagement userManagement;
	
	public MyAuthorizingRealm() {
		setCacheManager(new MemoryConstrainedCacheManager());
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//System.out.println("In EgAuthorizingRealm.doGetAuthenticationInfo for: " + upToken.getUsername() + "/" + new String(upToken.getPassword()) + " - remember=" + upToken.isRememberMe());

		// TODO: 管理授权服务，是本服务的管理授权
		if(!userManagement.isValid(upToken.getUsername(), upToken.getPassword()))
			return null;

		return new SimpleAuthenticationInfo(upToken.getUsername(), upToken.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("In EgAuthorizingRealm.doGetAuthorizationInfo");
		// Doing nothing just now
		
		if (pc == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		String username = (String) pc.fromRealm(getName()).iterator().next();
		String roles = userManagement.getRoles(username);
		String[] myRoles = roles.split(":");
		for(String role:myRoles)
			authorizationInfo.addRole(role);
		return authorizationInfo;		
	}
}
