package com.risetek.auth.client.application.tools;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.LoggedInGatekeeper;
import com.risetek.auth.shared.DbInitAction;
import com.risetek.auth.shared.GetNoResult;

public class ToolsPresenter extends Presenter<ToolsPresenter.MyView, ToolsPresenter.MyProxy>
	implements MyUiHandlers {
	
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    }

    private DispatchAsync dispatcher;
    
    @ProxyStandard
	@UseGatekeeper(LoggedInGatekeeper.class)
    @NameToken(NameTokens.tools)
    interface MyProxy extends ProxyPlace<ToolsPresenter> {
    }

    @Inject
    ToolsPresenter(
            EventBus eventBus,
            MyView view,
            DispatchAsync dispatcher,
            MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

	@Override
	public void dbInit() {
		DbInitAction action = new DbInitAction();
		dispatcher.execute(action, new AsyncCallback<GetNoResult>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Database Init failed");
			}

			@Override
			public void onSuccess(GetNoResult result) {
				Window.alert("Database Init success");
			}
		});
	}
}
