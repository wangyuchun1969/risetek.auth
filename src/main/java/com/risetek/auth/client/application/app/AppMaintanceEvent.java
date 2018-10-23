package com.risetek.auth.client.application.app;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.risetek.auth.shared.AppEntity;

public class AppMaintanceEvent extends GwtEvent<AppMaintanceEvent.ReplaceAppHandler> {
	public interface ReplaceAppHandler extends EventHandler {
		public void onReplace(AppMaintanceEvent event);
	}

	private static Type<ReplaceAppHandler> TYPE;
	public AppEntity entity;
	
	public AppMaintanceEvent(AppEntity entity) {
		this.entity = entity;
	}
	
	public static Type<ReplaceAppHandler> getType() {
		if( TYPE == null )
			TYPE = new Type<ReplaceAppHandler>();
		
		return TYPE;
	}
	
	@Override
	public Type<ReplaceAppHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(ReplaceAppHandler handler) {
		handler.onReplace(this);
	}
}
