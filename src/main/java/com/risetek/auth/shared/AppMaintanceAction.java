package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class AppMaintanceAction extends UnsecuredActionImpl<GetNoResult>{
	protected AppMaintanceAction() {
	}

	public AppEntity resource;
	public String operator;
	public AppMaintanceAction(AppEntity resource, String operator){
		this.operator  = operator;
		this.resource = resource;
	}
}
