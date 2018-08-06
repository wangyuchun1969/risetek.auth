package com.risetek.auth.client.application.home;

import java.util.Map.Entry;

import com.google.gwt.core.shared.GWT;
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
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.client.security.LoggedInGatekeeper;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.DbInitAction;
import com.risetek.auth.shared.GetNoResult;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy>
	implements MyUiHandlers {
	
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    	void showUserInfo(String info);
    }

    private PlaceManager placeManager;
    private DispatchAsync dispatcher;
    @Inject
    private CurrentUser user;
    
    @ProxyStandard
	@UseGatekeeper(LoggedInGatekeeper.class)
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    @Inject
    HomePresenter(
            EventBus eventBus,
            MyView view,
            PlaceManager placeManager,
            DispatchAsync dispatcher,
            MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

	@Override
	public void upDateUser() {
		StringBuffer bf = new StringBuffer();
		bf.append("hello:").append(null==user?"null":"not null");
		//getView().showUserInfo("hello:" + (null==user?"null":"not null"));
		GWT.log("update user");
        AuthorityInfo info = user.getAuthorityInfo();
        for(Entry<String, Boolean> e:info.getRoles().entrySet()) {
        	bf.append(e.getKey() + " is " + (e.getValue() ? "有权" : "无权"));
        }
        
        getView().showUserInfo(bf.toString());
	}
	
	@Override
    protected void onReset() {
		GWT.log("onReset");
    	super.onReset();
    	upDateUser();
    }

	@Override
	public void gotoSecurity() {
		placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.security).build());
	}

	@Override
	public void gotoResources() {
		placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.resource).build());
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
