package com.risetek.auth.client.application.app;

import com.risetek.auth.client.application.menu.MenuWidget;
import com.risetek.auth.client.application.app.AppMaintanceEvent;
import com.risetek.auth.shared.AppEntity;

public class AddAppMenu extends MenuWidget {

	@Override
	public String getLabel() {
		return "新增";
	}

	@Override
	public void onClick() {
		AppEntity entity = new AppEntity();
		entity.setId(-1);
		fireEvent(new AppMaintanceEvent(entity));
	}

	@Override
	public boolean canReveal() {
		return getMyCurrentUser().getAuthorityInfo().isLogin();
	}
}