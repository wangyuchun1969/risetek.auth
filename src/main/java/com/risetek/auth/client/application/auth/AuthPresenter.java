package com.risetek.auth.client.application.auth;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
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
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.shared.GetResult;
import com.risetek.auth.shared.OpenAuthAction;
import com.risetek.auth.shared.OpenAuthInfo;

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
	private final DispatchAsync dispatcher;

	@Inject
	public AuthPresenter(final EventBus eventBus, 
			DispatchAsync dispatcher,
			final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
		this.dispatcher = dispatcher;
		client_id = Location.getParameter("client_id");
		callback_uri = Location.getParameter("redirect_uri");
		getView().setUiHandlers(this);
	}

	@Override
	public void Login(String username, String password) {
		
		
		/*
		String url = GWT.getHostPageBaseURL()+"oauth/authorize?client_id=" + client_id + "&response_type=code" +"&redirect_uri=" + callback_uri;
		url += "&username=" + username + "&passwd=" + password;
		Location.replace(URL.encode(url));
		*/
		
		OpenAuthAction action = new OpenAuthAction(client_id, username, password, callback_uri, "code");
		dispatcher.execute(action, new AsyncCallback<GetResult<OpenAuthInfo>>() {
			@Override
			public void onFailure(Throwable caught) {
//				getView().setStatus("用户名或密码错误");
				Window.alert("用户名或密码错误" + caught);
			}

			@Override
			public void onSuccess(GetResult<OpenAuthInfo> result) {
				if(null == result.getResults()) {
					Window.alert("用户名或密码错误");
					return;
				}
				Location.replace(URL.encode(result.getResults().getCallback_url()+"?code="+result.getResults().getToken()));
			}
		});
	}

	@Override
	public void onReset() {
		getView().reset();
		getView().setClientID(client_id);
	}
}
