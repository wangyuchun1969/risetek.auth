package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class DatabaseResourcesQueryAction extends UnsecuredActionImpl<GetResults<UserResourceEntity>> {
	public int userId;
	public int appId;
	public DatabaseResourcesQueryAction(int userId, int appId){
		this.userId = userId;
		this.appId = appId;
	}
	// for type signature of class
	protected DatabaseResourcesQueryAction(){}
}
