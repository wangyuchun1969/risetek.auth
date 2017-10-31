package com.risetek.auth.server.dispatch;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.DatabaseResourcesQueryAction;
import com.risetek.auth.shared.GetResults;
import com.risetek.auth.shared.UserResourceEntity;

public class DatabaseResourcesQueryActionHandler implements ActionHandler<DatabaseResourcesQueryAction, GetResults<UserResourceEntity>> {

	@Inject
	private DbManagement dbManagement;

	@Override
	public GetResults<UserResourceEntity> execute(DatabaseResourcesQueryAction action, ExecutionContext context)
			throws ActionException {
		List<UserResourceEntity> users = null;
		try {
			users = dbManagement.getUserResource(action.userId, action.appId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
		return new GetResults<UserResourceEntity>(users);
	}

	@Override
	public Class<DatabaseResourcesQueryAction> getActionType() {
		return DatabaseResourcesQueryAction.class;
	}

	@Override
	public void undo(DatabaseResourcesQueryAction action, GetResults<UserResourceEntity> result, ExecutionContext context)
			throws ActionException {
	}
	
}
