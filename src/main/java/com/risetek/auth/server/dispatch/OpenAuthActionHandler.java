package com.risetek.auth.server.dispatch;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.UserManagement;
import com.risetek.auth.shared.GetResult;
import com.risetek.auth.shared.OpenAuthAction;
import com.risetek.auth.shared.OpenAuthInfo;

public class OpenAuthActionHandler implements ActionHandler<OpenAuthAction, GetResult<OpenAuthInfo>> {

	@Inject
	private UserManagement userManagement;

	@Override
	public GetResult<OpenAuthInfo> execute(OpenAuthAction action, ExecutionContext context) throws ActionException {

		String username = action.username;
		String passwd = action.password;
		System.out.println("access from OpenAuthActionHandler : username: " + username + " passwd:" + passwd + " application:" + action.client_id);
		// TODO: 应用授权服务，应该按照应用ID来提供授权。
		if (null == username || null == passwd || !userManagement.isValid2(username, passwd.toCharArray())) {
			return new GetResult<OpenAuthInfo>(null);
		}

		try {
			// dynamically recognize an OAuth profile based on request
			// characteristic (params, method, content type etc.), perform
			// validation
			OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			String token = oauthIssuerImpl.authorizationCode();
			System.out.println("callback: " + action.redirect_uri + " token: " + token);

			OpenAuthInfo info = new OpenAuthInfo();
			info.setCallback_url(action.redirect_uri);
			info.setToken(token);
			info.setClient_id(action.client_id);
			info.setUsername(username);

			// cache information for callback
			userManagement.setInfoByToken(token, info);
			return new GetResult<OpenAuthInfo>(info);
			// if something goes wrong
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}

		return new GetResult<OpenAuthInfo>(null);
	}

	@Override
	public Class<OpenAuthAction> getActionType() {
		return OpenAuthAction.class;
	}

	@Override
	public void undo(OpenAuthAction action, GetResult<OpenAuthInfo> result, ExecutionContext context)
			throws ActionException {
		// nothing to do.
	}
}
