package com.risetek.auth.server.guice;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.risetek.auth.server.dispatch.AppMaintanceActionHandler;
import com.risetek.auth.server.dispatch.AppQueryActionHandler;
import com.risetek.auth.server.dispatch.AuthorityActionHandler;
import com.risetek.auth.server.dispatch.DatabaseResourceMaintanceActionHandler;
import com.risetek.auth.server.dispatch.DatabaseResourcesQueryActionHandler;
import com.risetek.auth.server.dispatch.DatabaseSecurityMaintanceActionHandler;
import com.risetek.auth.server.dispatch.DatabaseSecurityQueryActionHandler;
import com.risetek.auth.server.dispatch.DbInitActionHandler;
import com.risetek.auth.server.dispatch.LogInOutActionHandler;
import com.risetek.auth.server.dispatch.OpenAuthActionHandler;
import com.risetek.auth.shared.AppMaintanceAction;
import com.risetek.auth.shared.AppQueryAction;
import com.risetek.auth.shared.AuthorityAction;
import com.risetek.auth.shared.DatabaseResourceMaintanceAction;
import com.risetek.auth.shared.DatabaseResourcesQueryAction;
import com.risetek.auth.shared.DatabaseSecurityMaintanceAction;
import com.risetek.auth.shared.DatabaseSecurityQueryAction;
import com.risetek.auth.shared.DbInitAction;
import com.risetek.auth.shared.LogInOutAction;
import com.risetek.auth.shared.OpenAuthAction;

public class MyHandlerModule extends HandlerModule {
	@Override
	protected void configureHandlers() {
		bindHandler(LogInOutAction.class, LogInOutActionHandler.class);
		bindHandler(AuthorityAction.class, AuthorityActionHandler.class);
		bindHandler(OpenAuthAction.class, OpenAuthActionHandler.class);
		bindHandler(DbInitAction.class, DbInitActionHandler.class);
		bindHandler(DatabaseResourcesQueryAction.class, DatabaseResourcesQueryActionHandler.class);
		bindHandler(DatabaseResourceMaintanceAction.class, DatabaseResourceMaintanceActionHandler.class);
		bindHandler(DatabaseSecurityMaintanceAction.class, DatabaseSecurityMaintanceActionHandler.class);
		bindHandler(DatabaseSecurityQueryAction.class, DatabaseSecurityQueryActionHandler.class);
		bindHandler(AppMaintanceAction.class, AppMaintanceActionHandler.class);
		bindHandler(AppQueryAction.class, AppQueryActionHandler.class);
	}
}
