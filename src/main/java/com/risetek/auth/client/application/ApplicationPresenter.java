package com.risetek.auth.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.NestedSlot;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> 
	implements ApplicationUiHandlers {
    public interface MyView extends View, HasUiHandlers<ApplicationUiHandlers>{
    	void UpdateSocketStatus(String status);
    	void setTotalUsers(int number);
    }

    public static final NestedSlot SLOT_SetMainContent = new NestedSlot();

    public static final NestedSlot SLOT_SetMenuContent = new NestedSlot();
    
    @ProxyStandard
    @NoGatekeeper
    public interface MyProxy extends Proxy<ApplicationPresenter> {}

    @Inject
    public ApplicationPresenter(EventBus eventBus, MyView view, MyProxy proxy, 
    		DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.Root);
        getView().setUiHandlers(this);

    }

	@Override
	public void onReveal() {
		// To Call menu module.
//        fireEvent(new RevealDefaultLinkColumnEvent());
	}

	@Override
	public void UpdateSocketStatus(String status) {
		getView().UpdateSocketStatus(status);
	}

	@Override
	public void setTotalUsers(int number) {
		getView().setTotalUsers(number);
	}
}
