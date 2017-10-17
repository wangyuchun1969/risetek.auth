package com.risetek.auth.server.dispatch;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.DatabaseSecurityMaintanceAction;
import com.risetek.auth.shared.GetNoResult;

public class DatabaseSecurityMaintanceActionHandler implements ActionHandler<DatabaseSecurityMaintanceAction, GetNoResult> {

	@Inject
	private DbManagement dbManagement;
	
	@Override
	public GetNoResult execute(DatabaseSecurityMaintanceAction action, ExecutionContext context)
			throws ActionException {

		if("delete".equals(action.operator)) {
			try {
				dbManagement.deleteUserSecurity(action.security);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("insert".equals(action.operator)) {
			try {
				dbManagement.addUserSecurity(action.security);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("update".equals(action.operator)) {
			try {
				dbManagement.updateUserSecurity(action.security);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		}
		return null;
	}

	@Override
	public Class<DatabaseSecurityMaintanceAction> getActionType() {
		return DatabaseSecurityMaintanceAction.class;
	}

	@Override
	public void undo(DatabaseSecurityMaintanceAction action, GetNoResult result, ExecutionContext context)
			throws ActionException {
	}
}
