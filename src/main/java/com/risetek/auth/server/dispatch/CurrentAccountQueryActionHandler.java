package com.risetek.auth.server.dispatch;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.CurrentAccountQueryAction;
import com.risetek.auth.shared.GetResults;

public class CurrentAccountQueryActionHandler implements ActionHandler<CurrentAccountQueryAction, GetResults<AccountEntity>> {

	@Inject
	private DbManagement dbManagement;

	@Override
	public GetResults<AccountEntity> execute(CurrentAccountQueryAction action, ExecutionContext context)
			throws ActionException {
		List<AccountEntity> accouts = null;
		try {
			System.out.println("----AccountQueryActionHandler：GetResults--------------");
			accouts = dbManagement.getAccount(action.accountName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionException(e.toString());
		}
		return new GetResults<AccountEntity>(accouts);
	}

	@Override
	public Class<CurrentAccountQueryAction> getActionType() {
		return CurrentAccountQueryAction.class;
	}

	@Override
	public void undo(CurrentAccountQueryAction action, GetResults<AccountEntity> result, ExecutionContext context)
			throws ActionException {
	}
	
}
