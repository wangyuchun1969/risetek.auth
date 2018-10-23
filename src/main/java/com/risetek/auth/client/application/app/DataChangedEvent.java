package com.risetek.auth.client.application.app;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DataChangedEvent extends GwtEvent<DataChangedEvent.DataChangedHandler> {
	public interface DataChangedHandler extends EventHandler {
		public void onDataChanged();
	}

	private static Type<DataChangedHandler> TYPE;
	
	public static Type<DataChangedHandler> getType() {
		if( TYPE == null )
			TYPE = new Type<DataChangedHandler>();
		
		return TYPE;
	}
	
	@Override
	public Type<DataChangedHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(DataChangedHandler handler) {
		handler.onDataChanged();
	}

}
