package com.risetek.auth.client.application.selfAccount;

import com.risetek.auth.client.application.menu.MenuWidget;
import com.risetek.auth.client.place.NameTokens;

public class ManageSelfAccountMenu extends MenuWidget {

	@Override
	public String getLabel() {
		return "管理账户";
	}

	@Override
	public void onClick() {
		Goto(NameTokens.manageSelfAccount);
	}

	@Override
	public boolean canReveal() {
		return getMyCurrentUser().getAuthorityInfo().isLogin() &&
			!getMyCurrentUser().getAuthorityInfo().hasRole("admin");
	}
}
