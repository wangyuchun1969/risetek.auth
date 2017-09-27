package com.risetek.auth.server.dispatch;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.DatabaseSecurityQueryAction;
import com.risetek.auth.shared.GetResults;
import com.risetek.auth.shared.UserSecurity;

public class DatabaseSecurityQueryActionHandler implements ActionHandler<DatabaseSecurityQueryAction, GetResults<UserSecurity>> {

	@Inject
	private DbManagement dbManagement;

	@Override
	public GetResults<UserSecurity> execute(DatabaseSecurityQueryAction action, ExecutionContext context)
			throws ActionException {
		List<UserSecurity> users = null;
		try {
			users = dbManagement.getAllUserSecurity();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new GetResults<UserSecurity>(users);
	}

	@Override
	public Class<DatabaseSecurityQueryAction> getActionType() {
		return DatabaseSecurityQueryAction.class;
	}

	@Override
	public void undo(DatabaseSecurityQueryAction action, GetResults<UserSecurity> result, ExecutionContext context)
			throws ActionException {
	}
	
}
