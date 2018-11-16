package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class CurrentAccountQueryAction extends UnsecuredActionImpl<GetResults<AccountEntity>> {
	public CurrentAccountQueryAction(String accountName) {
		this.accountName = accountName;
	}

	public String accountName;
	// for type signature of class
	protected CurrentAccountQueryAction(){}
}
