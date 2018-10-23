package com.risetek.auth.server.dispatch;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.AppMaintanceAction;
import com.risetek.auth.shared.GetNoResult;

public class AppMaintanceActionHandler
		implements ActionHandler<AppMaintanceAction, GetNoResult> {

	@Inject
	private DbManagement dbManagement;
	
	@Override
	public GetNoResult execute(AppMaintanceAction action, ExecutionContext context)
			throws ActionException {

		if("delete".equals(action.operator)) {
			try {
				dbManagement.deleteApplication(action.resource);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("insert".equals(action.operator)) {
			try {
				dbManagement.addApplication(action.resource);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("update".equals(action.operator)) {
			try {
				dbManagement.updateApplication(action.resource);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		}
		return null;
	}

	@Override
	public Class<AppMaintanceAction> getActionType() {
		return AppMaintanceAction.class;
	}

	@Override
	public void undo(AppMaintanceAction action, GetNoResult result, ExecutionContext context)
			throws ActionException {
	}
}
