package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class DatabaseResourceMaintanceAction extends UnsecuredActionImpl<GetNoResult> {
	protected DatabaseResourceMaintanceAction() {}
	public UserResourceEntity resource;
	public String operator;
	public DatabaseResourceMaintanceAction(UserResourceEntity resource, String operator){
		this.operator  = operator;
		this.resource = resource;
	}
}
