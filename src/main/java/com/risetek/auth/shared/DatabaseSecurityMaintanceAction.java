package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class DatabaseSecurityMaintanceAction extends UnsecuredActionImpl<GetNoResult> {
	protected DatabaseSecurityMaintanceAction() {}
	public UserSecurityEntity security;
	public String operator;
	public DatabaseSecurityMaintanceAction(UserSecurityEntity security, String operator){
		this.operator  = operator;
		this.security = security;
	}
}
