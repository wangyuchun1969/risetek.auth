package com.risetek.auth.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.risetek.auth.server.dispatch.AuthorityActionHandler;
import com.risetek.auth.server.dispatch.DatabaseSecurityMaintanceActionHandler;
import com.risetek.auth.server.dispatch.DatabaseSecurityQueryActionHandler;
import com.risetek.auth.server.dispatch.LogInOutActionHandler;
import com.risetek.auth.shared.AuthorityAction;
import com.risetek.auth.shared.DatabaseSecurityMaintanceAction;
import com.risetek.auth.shared.DatabaseSecurityQueryAction;
import com.risetek.auth.shared.LogInOutAction;

public class MyHandlerModule extends HandlerModule {
	@Override
	protected void configureHandlers() {
		bindHandler(LogInOutAction.class, LogInOutActionHandler.class);
		bindHandler(AuthorityAction.class, AuthorityActionHandler.class);
		bindHandler(DatabaseSecurityMaintanceAction.class, DatabaseSecurityMaintanceActionHandler.class);
		bindHandler(DatabaseSecurityQueryAction.class, DatabaseSecurityQueryActionHandler.class);
	}
}
