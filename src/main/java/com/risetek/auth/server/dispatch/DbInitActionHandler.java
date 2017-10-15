package com.risetek.auth.server.dispatch;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.DbInitAction;
import com.risetek.auth.shared.GetNoResult;

public class DbInitActionHandler implements ActionHandler<DbInitAction, GetNoResult> {

	@Inject
	private DbManagement dbManagement;
	
	@Override
	public GetNoResult execute(DbInitAction action,
			ExecutionContext context) throws ActionException {
		try {
			dbManagement.development_init_db();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionException("db init failed:" + e.getMessage());
		}
		return new GetNoResult();
	}

	@Override
	public Class<DbInitAction> getActionType() {
		return DbInitAction.class;
	}

	@Override
	public void undo(DbInitAction action, GetNoResult result,
			ExecutionContext context) throws ActionException {
		// nothing to do.
	}
}
