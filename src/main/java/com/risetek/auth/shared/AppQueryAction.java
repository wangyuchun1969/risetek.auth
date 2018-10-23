package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class AppQueryAction extends UnsecuredActionImpl<GetResults<AppEntity>> {
	public AppQueryAction(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public int offset;
	public int limit;
	
	// for type signature of class
	protected AppQueryAction(){}
}
