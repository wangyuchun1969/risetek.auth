package com.risetek.auth.client.application.app;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationInfoRecord;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.application.app.editor.DescriptionEditorPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.shared.GetResults;
import com.risetek.auth.shared.AppEntity;
import com.risetek.auth.shared.AppQueryAction;


public class PagePresenter extends Presenter<PagePresenter.MyView, PagePresenter.MyProxy>
	implements PageUiHandlers, AppMaintanceEvent.ReplaceAppHandler {
	public interface MyView extends View, HasUiHandlers<PageUiHandlers> {
		public void setData(List<AppEntity> records);

		public void upDateDatabase(AppEntity entity);

		public int getPageSize();
	}

	@ProxyStandard
	@NameToken(NameTokens.app)
	public interface MyProxy extends ProxyPlace<PagePresenter> {
	}

	private final DispatchAsync dispatcher;
	private final DescriptionEditorPresenter editor;
	
	@Inject
	public PagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, DispatchAsync dispatcher,
			PlaceManager placeManager, DescriptionEditorPresenter editor) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
		this.dispatcher = dispatcher;
		this.editor = editor;
		getView().setUiHandlers(this);
		eventBus.addHandler(DataChangedEvent.getType(), new DataChangedEvent.DataChangedHandler() {

			@Override
			public void onDataChanged() {
				update(0, getView().getPageSize(), null);
			}
		});
/*
		getEventBus().addHandler(YunStatusChangeEvent.getType(), new YunStatusChangeEvent.YunStatusChangeHandler() {

			@Override
			public void onYunStatusChange(YunStatusChangeEvent event) {
				Status status = event.getStatus();
				if (status == Status.DEVICE_STATUS_CHANGED) {
					update(0, getView().getPageSize(), null);
				}
			}
		});
*/
	}

	@Override
	public void update(int offset, int limit, String key) {
//		DeviceGroupEntityAction action = new DeviceGroupEntityAction(1, 100);
		AppQueryAction action = new AppQueryAction(offset, limit);

		dispatcher.execute(action, new AsyncCallback<GetResults<AppEntity>>() {
			@Override
			public void onSuccess(GetResults<AppEntity> result) {
				getView().setData(result.getResults());
			}

			@Override
			public void onFailure(Throwable caught) {
				// Convenient way to find out which exception was thrown.
				try {
					throw caught;
				} catch (StatusCodeException e) {
					// Response.SC_MOVED_TEMPORARILY
					// getEventBus().fireEvent(new AuthorityChangedEvent());
				} catch (IncompatibleRemoteServiceException e) {
					Window.alert("This client is not compatible with the server;\r\n Cleanup and refresh the browser.");
				} catch (InvocationException e) {
					// the call didn't complete cleanly
					Window.alert("2" + e.toString());
				} catch (RuntimeException e) {
					Window.alert("Error:" + e);
				} catch (Exception e) {
					Window.alert("Error:" + e);
				} catch (Throwable e) {
					// last resort -- a very unexpected exception
					Window.alert("Error:" + e);
				}
			}
		});
	}

	@ProxyEvent
	@Override
	public void onReplace(AppMaintanceEvent event) {
		editor(event.entity);
	}

	@Override
	public void editor(AppEntity entity) {
		editor.onEdit(entity);
	}
}
