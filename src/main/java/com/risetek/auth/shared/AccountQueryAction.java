package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class AccountQueryAction extends UnsecuredActionImpl<GetResults<AccountEntity>> {
	public AccountQueryAction(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public int offset;
	public int limit;
	
	// for type signature of class
	protected AccountQueryAction(){}
}
