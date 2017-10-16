package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class DatabaseSecurityMaintanceAction extends UnsecuredActionImpl<GetNoResult> {
	public enum Operator {INSERT, DELETE, UPDATE};
	
	public UserSecurityEntity security;
	public Operator operator;
	public DatabaseSecurityMaintanceAction(UserSecurityEntity security, Operator operator){
		this.operator  = operator;
		this.security = security;
	}
}
