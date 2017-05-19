package com.risetek.auth.client.application.auth;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.shared.AuthToken;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.GetNoResult;
import com.risetek.auth.shared.LogInOutAction;

public class AuthPresenter extends
		Presenter<AuthPresenter.MyView, AuthPresenter.MyProxy>
		implements MyUiHandlers {

	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
		public void setStatus(String status);
	}

	@Inject
	private CurrentUser user;
	
	@ProxyStandard
	@NameToken(NameTokens.auth)
    @NoGatekeeper
	public interface MyProxy extends ProxyPlace<AuthPresenter> {
	}

	private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;

	@Inject
	public AuthPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, DispatchAsync dispatcher,
			PlaceManager placeManager) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		Window.alert(Location.getQueryString());
		getView().setUiHandlers(this);
	}

	@Override
	public void Login(String username, String password) {

		AuthToken token = new AuthToken();
		token.setUsername(username);
		// TODO: encrypt it!
		token.setPassword(password);
		token.setRememberMe(true);

		LogInOutAction action = new LogInOutAction(token);
		getView().setStatus("获取用户权限...");

		dispatcher.execute(action, new AsyncCallback<GetNoResult>() {
			@Override
			public void onFailure(Throwable caught) {
				getView().setStatus("用户名或密码错误");
			}

			@Override
			public void onSuccess(GetNoResult result) {
				user.forceSync();
			}
		});
	}
	
	@Override
	public void onReset() {
		super.onReset();
	}
}
