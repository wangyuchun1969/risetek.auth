package com.risetek.auth.server.dispatch;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.shared.AuthToken;
import com.risetek.auth.shared.GetNoResult;
import com.risetek.auth.shared.LogInOutAction;

public class LogInOutActionHandler implements
		ActionHandler<LogInOutAction, GetNoResult> {
	
	@Override
	public GetNoResult execute(LogInOutAction action,
			ExecutionContext context) throws ActionException {

		AuthToken token = action.authToken;
		if (token != null) {
			// Login
			UsernamePasswordToken upt = new UsernamePasswordToken(
					token.getUsername(), token.getPassword().toCharArray(),
					token.isRememberMe());
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.login(upt);
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
		return new GetNoResult();
	}

	@Override
	public Class<LogInOutAction> getActionType() {
		return LogInOutAction.class;
	}

	@Override
	public void undo(LogInOutAction action, GetNoResult result, ExecutionContext context)
			throws ActionException {
		// Do nothing
	}
}
