package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class DatabaseSecurityQueryAction extends UnsecuredActionImpl<GetResults<UserSecurityEntity>> {
	public int offset;
	public int limit;
	public DatabaseSecurityQueryAction(int offset, int limit){
		this.offset = offset;
		this.limit = limit;
	}
	// for type signature of class
	protected DatabaseSecurityQueryAction(){}
}
