package com.risetek.auth.client.application.menu;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.client.security.CurrentUser;

interface MenuUiHandlers extends UiHandlers {
	public void Goto(String place);
	public CurrentUser getCurrentUser();
    public void fireEvent(GwtEvent<?> event);
    <A extends Action<R>, R extends Result> DispatchRequest exeCute(A action, AsyncCallback<R> callback);
}
