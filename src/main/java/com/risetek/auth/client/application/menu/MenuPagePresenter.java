package com.risetek.auth.client.application.menu;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.auth.client.RevealDefaultLinkColumnHandler;
import com.risetek.auth.client.UserStatusChangeEvent;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.application.resources.ResourcesMenu;
import com.risetek.auth.client.application.security.SecurityMenu;
import com.risetek.auth.client.security.CurrentUser;

public class MenuPagePresenter extends
		Presenter<MenuPagePresenter.MyView, MenuPagePresenter.MyProxy>
		implements MenuUiHandlers, RevealDefaultLinkColumnHandler, UserStatusChangeEvent.UserStatusChangeHandler {
	public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
    	public void ShowMenuList(List<MenuWidget> menus);
	}

	@ProxyStandard
    @NoGatekeeper
	public interface MyProxy extends Proxy<MenuPagePresenter> {
	}

	private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;

	@Inject
	private CurrentUser user;
    
	@ProxyEvent
	@Override
	public void onRevealDefaultLinkColumn( RevealDefaultLinkColumnEvent event) {
		if(!isVisible())
			forceReveal();

		user.forceSync();
	};
    
	private List<MenuWidget> menus = new ArrayList<MenuWidget>();
	
	private MenuWidget instanceMenu(List<MenuWidget> list, MenuWidget menu) {
		list.add(menu);
		menu.setUiHandlers(this);
		return menu;
	}
	
    @Override
    protected void onBind() {
/*    	
    	MenuWidget devices = instanceMenu(menus, new NodeMenu("设备"));
    	instanceMenu(devices.menus, new DeviceStatusMenu());
*/
    	instanceMenu(menus, new ResourcesMenu());
    	instanceMenu(menus, new SecurityMenu());
    	instanceMenu(menus, new LoginMenu());
    }

	@Inject
	public MenuPagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, DispatchAsync dispatcher,
			PlaceManager placeManager) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMenuContent);
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		addRegisteredHandler(UserStatusChangeEvent.getType(), this);
		getView().setUiHandlers(this);
	}

	@Override
	public void Goto(String place) {
		placeManager.revealPlace(new PlaceRequest.Builder().nameToken(place).build());
	}

	@Override
	public CurrentUser getCurrentUser() {
		return user;
	}

	@Override
	public <A extends Action<R>, R extends Result> DispatchRequest exeCute(
			A action, AsyncCallback<R> callback) {
		return dispatcher.execute(action, callback);
	}

	@Override
	public void onUserStatusChange() {
        getView().ShowMenuList(menus);
        placeManager.revealCurrentPlace();
	}
}
