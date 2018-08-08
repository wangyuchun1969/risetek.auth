package com.risetek.auth.server.dispatch;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.DatabaseResourceMaintanceAction;
import com.risetek.auth.shared.GetNoResult;

public class DatabaseResourceMaintanceActionHandler implements ActionHandler<DatabaseResourceMaintanceAction, GetNoResult> {

	@Inject
	private DbManagement dbManagement;
	
	@Override
	public GetNoResult execute(DatabaseResourceMaintanceAction action, ExecutionContext context)
			throws ActionException {

		if("delete".equals(action.operator)) {
			try {
				dbManagement.deleteUserResource(action.resource);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("insert".equals(action.operator)) {
			try {
				dbManagement.addUserResourceIndirctor(action.resource);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("update".equals(action.operator)) {
			try {
				dbManagement.updateUserResource(action.resource);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		}
		return null;
	}

	@Override
	public Class<DatabaseResourceMaintanceAction> getActionType() {
		return DatabaseResourceMaintanceAction.class;
	}

	@Override
	public void undo(DatabaseResourceMaintanceAction action, GetNoResult result, ExecutionContext context)
			throws ActionException {
	}
}
