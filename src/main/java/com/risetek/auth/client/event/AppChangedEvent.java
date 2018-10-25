package com.risetek.auth.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AppChangedEvent extends GwtEvent<AppChangedEvent.AppChangedHandler> {
	public interface AppChangedHandler extends EventHandler {
		public void onAppChanged();
	}

	private static Type<AppChangedHandler> TYPE;
	
	public static Type<AppChangedHandler> getType() {
		if( TYPE == null )
			TYPE = new Type<AppChangedHandler>();
		
		return TYPE;
	}
	
	@Override
	public Type<AppChangedHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(AppChangedHandler handler) {
		handler.onAppChanged();
	}
}
