package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class AccountMaintanceAction extends UnsecuredActionImpl<GetNoResult>{
	protected AccountMaintanceAction() {
	}

	public AccountEntity account;
	public String operator;
	public AccountMaintanceAction(AccountEntity account, String operator){
		this.operator  = operator;
		this.account = account;
	}
}
