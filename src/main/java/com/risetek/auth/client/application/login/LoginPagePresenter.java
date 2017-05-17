package com.risetek.auth.client.application.login;

import com.google.gwt.core.client.GWT;
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
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.shared.AuthToken;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.GetResult;
import com.risetek.auth.shared.LogInOutAction;

public class LoginPagePresenter extends
		Presenter<LoginPagePresenter.MyView, LoginPagePresenter.MyProxy>
		implements LoginUiHandlers {

	public interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
		public void setStatus(String status);
	}

	@Inject
	private CurrentUser user;
	
	@ProxyStandard
	@NameToken(NameTokens.login)
    @NoGatekeeper
	public interface MyProxy extends ProxyPlace<LoginPagePresenter> {
	}

	private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;

	@Inject
	public LoginPagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, DispatchAsync dispatcher,
			PlaceManager placeManager) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
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

		dispatcher.execute(action, new AsyncCallback<GetResult<AuthorityInfo>>() {
			@Override
			public void onFailure(Throwable caught) {
				getView().setStatus("用户名或密码错误");
			}

			@Override
			public void onSuccess(GetResult<AuthorityInfo> result) {
//				getView().setStatus("获取用户权限...");
				user.setAuthorityInfo(result.getResults());
				AuthorityInfo info = user.getAuthorityInfo();
				GWT.log("loged:" + user + "info is:" + (null==info? " nul" : info));
				placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.HOME).build());
			}
		});
	}
	/*
	@Override
	public void onReveal() {
		AuthorityInfo info = user.getAuthorityInfo();
		GWT.log("login on reveal:" + user + "info is:" + (null==info? " nul" : info));
		if(null != info && info.isLogin)
			placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.HOME).build());
	}
	*/
	@Override
	public void onReveal() {
		user.forceSync();
	}
}
