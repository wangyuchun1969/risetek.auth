package com.risetek.auth.server.dispatch;

import java.util.Arrays;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.shared.AuthorityAction;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.GetResult;

public class AuthorityActionHandler implements ActionHandler<AuthorityAction, GetResult<AuthorityInfo>> {
	private String[] checkRoles = {"admin", "maintenance", "operator", "visitor", "developer"};

	@Override
	public GetResult<AuthorityInfo> execute(AuthorityAction action,
			ExecutionContext context) throws ActionException {
		Subject subject = SecurityUtils.getSubject();
		// TODO: authority with current resources.

		PrincipalCollection principas = subject.getPrincipals();
		if( principas != null ) {
			Set<String> realmNames = principas.getRealmNames();
			for( String s:realmNames)
				System.out.println("RealmName: " + s);
		}
		
		AuthorityInfo info = new AuthorityInfo();
		info.setLogin(subject.isAuthenticated());
		info.setCurrentAccountName((String)subject.getSession().getAttribute("currentUserName"));
		try {
			boolean[] roleResult = subject.hasRoles(Arrays.asList(checkRoles));
			for (int i = 0; i < checkRoles.length; i++)
				info.getRoles().put(checkRoles[i], roleResult[i]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new GetResult<AuthorityInfo>(info);
	}

	@Override
	public Class<AuthorityAction> getActionType() {
		return AuthorityAction.class;
	}

	@Override
	public void undo(AuthorityAction action, GetResult<AuthorityInfo> result,
			ExecutionContext context) throws ActionException {
		// nothing to do.
	}
}
