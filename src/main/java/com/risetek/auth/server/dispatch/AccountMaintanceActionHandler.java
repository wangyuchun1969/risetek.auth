package com.risetek.auth.server.dispatch;

import java.sql.SQLException;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.AccountMaintanceAction;
import com.risetek.auth.shared.GetNoResult;

public class AccountMaintanceActionHandler
		implements ActionHandler<AccountMaintanceAction, GetNoResult> {

	@Inject
	private DbManagement dbManagement;
	
	@Override
	public GetNoResult execute(AccountMaintanceAction action, ExecutionContext context)
			throws ActionException {

		if("delete".equals(action.operator)) {
			try {
				dbManagement.deleteAccount(action.account);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("insert".equals(action.operator)) {
			try {
				dbManagement.addAccount(action.account);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		} else if("update".equals(action.operator)) {
			try {
				dbManagement.updateAccount(action.account);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActionException(e.toString());
			}
		}
		return new GetNoResult();
	}

	@Override
	public Class<AccountMaintanceAction> getActionType() {
		return AccountMaintanceAction.class;
	}

	@Override
	public void undo(AccountMaintanceAction action, GetNoResult result, ExecutionContext context)
			throws ActionException {
	}
}
