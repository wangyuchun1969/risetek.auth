package com.risetek.auth.server.dispatch;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.AppEntity;
import com.risetek.auth.shared.AppQueryAction;
import com.risetek.auth.shared.GetResults;

public class AppQueryActionHandler implements ActionHandler<AppQueryAction, GetResults<AppEntity>> {

	@Inject
	private DbManagement dbManagement;

	@Override
	public GetResults<AppEntity> execute(AppQueryAction action, ExecutionContext context)
			throws ActionException {
		List<AppEntity> apps = null;
		System.out.println("----AppQueryActionHandler：GetResults--------------");
		try {
			apps = dbManagement.getAllApplications(action.offset, action.limit);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
		return new GetResults<AppEntity>(apps);
	}

	@Override
	public Class<AppQueryAction> getActionType() {
		return AppQueryAction.class;
	}

	@Override
	public void undo(AppQueryAction action, GetResults<AppEntity> result, ExecutionContext context)
			throws ActionException {
	}
	
}
