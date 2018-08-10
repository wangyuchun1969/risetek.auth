package com.risetek.auth.client.application.menu;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.risetek.auth.client.security.CurrentUser;
import com.gwtplatform.dispatch.rpc.shared.Result;

public abstract class MenuWidget {
	public final ArrayList<MenuWidget> menus = new ArrayList<MenuWidget>();

    private MenuUiHandlers menuUiHandlers;
    
    void setUiHandlers(MenuUiHandlers menuUiHandlers) {
    	this.menuUiHandlers = menuUiHandlers;
    }
    
	public abstract String getLabel();
	public abstract boolean canReveal();
	public abstract void onClick();
	
	protected void Goto(String place) {
		menuUiHandlers.Goto(place);
	}
	
	protected CurrentUser getMyCurrentUser() {
		return menuUiHandlers.getCurrentUser();
	}
	
	public void fireEvent(GwtEvent<?> event) {
		 menuUiHandlers.fireEvent(event);
	}
	
    protected <A extends Action<R>, R extends Result> DispatchRequest exeCute(A action, AsyncCallback<R> callback) {
    	return menuUiHandlers.exeCute(action, callback);
    }
}
