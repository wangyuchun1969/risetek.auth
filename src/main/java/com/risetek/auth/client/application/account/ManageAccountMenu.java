package com.risetek.auth.client.application.account;

import com.risetek.auth.client.application.menu.MenuWidget;
import com.risetek.auth.client.place.NameTokens;

public class ManageAccountMenu extends MenuWidget {

	@Override
	public String getLabel() {
		return "管理账户";
	}

	@Override
	public void onClick() {
		Goto(NameTokens.manageAccount);
	}

	@Override
	public boolean canReveal() {
		return getMyCurrentUser().getAuthorityInfo().isLogin() &&
			getMyCurrentUser().getAuthorityInfo().hasRole("admin");
	}
}
