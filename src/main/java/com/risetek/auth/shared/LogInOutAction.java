package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class LogInOutAction extends UnsecuredActionImpl<GetResult<AuthorityInfo>> {
	protected LogInOutAction(){}
	public LogInOutAction(AuthToken authToken) {
		this.authToken = authToken;
	}
	public AuthToken authToken;
}
