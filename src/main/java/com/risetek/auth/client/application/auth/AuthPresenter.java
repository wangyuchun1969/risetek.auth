package com.risetek.auth.client.application.auth;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;

public class AuthPresenter extends Presenter<AuthPresenter.MyView, AuthPresenter.MyProxy> implements MyUiHandlers {

	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
		public void setClientID(String clientId);
		public void reset();
	}

	@ProxyStandard
	@NameToken(NameTokens.auth)
	@NoGatekeeper
	public interface MyProxy extends ProxyPlace<AuthPresenter> {
	}

	private final String callback_uri;
	private final String client_id;

	@Inject
	public AuthPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
		client_id = Location.getParameter("client_id");
		callback_uri = Location.getParameter("redirect_uri");
		getView().setUiHandlers(this);
	}

	@Override
	public void Login(String username, String password) {
		String url = GWT.getHostPageBaseURL()+"oauth/authorize?client_id=" + client_id + "&response_type=code" +"&redirect_uri=" + callback_uri;
//		Location.assign(url);
		Location.replace(url);
	}

	@Override
	public void onReset() {
		getView().reset();
		getView().setClientID(client_id);
	}
}
