package com.risetek.auth.shared;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class OpenAuthAction extends UnsecuredActionImpl<GetResult<OpenAuthInfo>> {
	protected OpenAuthAction(){}
	public String client_id;
	public String username;
	public String password;
	public String redirect_uri;
	public String response_type;
	
	public OpenAuthAction(String id, String user, String passwd, String uri, String type) {
		client_id = id;
		username = user;
		password = passwd;
		redirect_uri = uri;
		response_type = type;
	}
}
