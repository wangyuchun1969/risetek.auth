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
import com.risetek.auth.shared.UserSecurityEntity;

public class DatabaseSecurityQueryActionHandler implements ActionHandler<DatabaseSecurityQueryAction, GetResults<UserSecurityEntity>> {

	@Inject
	private DbManagement dbManagement;

	@Override
	public GetResults<UserSecurityEntity> execute(DatabaseSecurityQueryAction action, ExecutionContext context)
			throws ActionException {
		List<UserSecurityEntity> users = null;
		try {
			users = dbManagement.getAllUserSecurity(action.offset, action.limit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new GetResults<UserSecurityEntity>(users);
	}

	@Override
	public Class<DatabaseSecurityQueryAction> getActionType() {
		return DatabaseSecurityQueryAction.class;
	}

	@Override
	public void undo(DatabaseSecurityQueryAction action, GetResults<UserSecurityEntity> result, ExecutionContext context)
			throws ActionException {
	}
	
}
