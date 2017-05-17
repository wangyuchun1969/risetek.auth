package com.risetek.auth.client.application.home;

import java.util.Map.Entry;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.client.security.LoggedInGatekeeper;
import com.risetek.auth.shared.AuthorityInfo;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy>
	implements MyUiHandlers {
	
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    	void showUserInfo(String info);
    }

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
            MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
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
	
}
