package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class DatabaseSecurityMaintanceAction extends UnsecuredActionImpl<GetNoResult> {
	public enum Operator {INSERT, DELETE};
	
	public UserSecurity security;
	public Operator operator;
	public DatabaseSecurityMaintanceAction(UserSecurity security, Operator operator){
		this.operator  = operator;
		this.security = security;
	}
}
