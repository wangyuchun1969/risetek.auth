package com.risetek.auth.server.dispatch;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.shared.AuthToken;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.GetResult;
import com.risetek.auth.shared.LogInOutAction;

public class LogInOutActionHandler implements
		ActionHandler<LogInOutAction, GetResult<AuthorityInfo>> {
	
	@Override
	public GetResult<AuthorityInfo> execute(LogInOutAction action,
			ExecutionContext context) throws ActionException {

		AuthToken token = action.authToken;
		Subject subject = SecurityUtils.getSubject();
		if (token != null) {
			// Login
			UsernamePasswordToken upt = new UsernamePasswordToken(
					token.getUsername(), token.getPassword().toCharArray(),
					token.isRememberMe());
			try {
				subject.login(upt);
				AuthorityInfo info = new AuthorityInfo();
				info.setRealm("todo!");
				info.setLogin(subject.isAuthenticated());

				return new GetResult<AuthorityInfo>(info);
			} catch (AuthenticationException e) {
				throw new ActionException(e.getMessage(), e);
			} finally {
				// For security.
				upt.clear();
			}
		} else {
			// Logout
			SecurityUtils.getSubject().logout();
			SecurityUtils.getSubject().getSession().stop();
		}
		return new GetResult<AuthorityInfo>(null);
	}

	@Override
	public Class<LogInOutAction> getActionType() {
		return LogInOutAction.class;
	}

	@Override
	public void undo(LogInOutAction action, GetResult<AuthorityInfo> result, ExecutionContext context)
			throws ActionException {
		// Do nothing
	}
}
