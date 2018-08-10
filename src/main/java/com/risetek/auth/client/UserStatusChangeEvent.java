package com.risetek.auth.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserStatusChangeEvent extends GwtEvent<UserStatusChangeEvent.UserStatusChangeHandler> {
	public interface UserStatusChangeHandler extends EventHandler {
		public void onUserStatusChange();
	}

	private static Type<UserStatusChangeHandler> TYPE;
	
	public static Type<UserStatusChangeHandler> getType() {
		if( TYPE == null )
			TYPE = new Type<UserStatusChangeHandler>();
		
		return TYPE;
	}
	
	@Override
	public Type<UserStatusChangeHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(UserStatusChangeHandler handler) {
		handler.onUserStatusChange();
	}

}
