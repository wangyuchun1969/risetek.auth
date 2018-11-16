package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class CurrentAccountChangePasswdAction extends UnsecuredActionImpl<GetNoResult> {

	public CurrentAccountChangePasswdAction(AccountEntity account, String oldPasswd) {
		this.account = account;
		this.oldPasswd = oldPasswd;
	}
	
	public AccountEntity account;
	public String oldPasswd;
	
	// for type signature of class
	protected CurrentAccountChangePasswdAction(){}
}
