package com.risetek.auth.server.dispatch;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.CurrentAccountChangePasswdAction;
import com.risetek.auth.shared.GetNoResult;

public class CurrentAccountChangePasswdActionHandler 
			implements ActionHandler<CurrentAccountChangePasswdAction, GetNoResult> {

	@Inject
	private DbManagement dbManagement;

	@Override
	public GetNoResult execute(CurrentAccountChangePasswdAction action, ExecutionContext context)
			throws ActionException {	
		try {
			System.out.println("----CurrentAccountChangePasswdActionHandler：confirm account-------------");
			List<AccountEntity> accounts = dbManagement.getAccount(action.account.getName());
			if( null == accounts.get(0)) {
				throw new ActionException("用户不存在");
			}
			if( null == action.oldPasswd || !action.oldPasswd.equals(accounts.get(0).getPassword())) {
				throw new ActionException("当前密码输入错误");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionException(e.toString());
		}
		
		try {
			System.out.println("----CurrentAccountChangePasswdActionHandler：change passwd-------------");
			dbManagement.updateAccount(action.account);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionException(e.toString());
		}
		return new GetNoResult();
	}

	@Override
	public Class<CurrentAccountChangePasswdAction> getActionType() {
		return CurrentAccountChangePasswdAction.class;
	}

	@Override
	public void undo(CurrentAccountChangePasswdAction action, GetNoResult result, ExecutionContext context)
			throws ActionException {
	}
	
}
