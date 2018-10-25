package com.risetek.auth.client.application.app.editor;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;
import com.risetek.auth.client.application.app.DataChangedEvent;
import com.risetek.auth.client.event.AppChangedEvent;
import com.risetek.auth.shared.AppEntity;
import com.risetek.auth.shared.AppMaintanceAction;
import com.risetek.auth.shared.GetNoResult;


public class DescriptionEditorPresenter extends PresenterWidget<DescriptionEditorPresenter.MyView>
		implements EditorUiHandlers {
	public interface MyView extends PopupView, HasUiHandlers<EditorUiHandlers> {
		public void edit(AppEntity entity);
	}

	private final DispatchAsync dispatcher;

	@Inject
	public DescriptionEditorPresenter(final EventBus eventBus, final MyView view, DispatchAsync dispatcher) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	public void onEdit(AppEntity entity) {
		getView().edit(entity);
		RevealRootPopupContentEvent.fire(this, this);
	}

	@Override
	public void onSave(AppEntity entity) {
		AppMaintanceAction action = new AppMaintanceAction(entity, (entity.getId() < 0) ? "insert":"update");
		dispatcher.execute(action, new AsyncCallback<GetNoResult>() {
			@Override
			public void onSuccess(GetNoResult result) {
				getEventBus().fireEvent(new DataChangedEvent());
				getEventBus().fireEvent(new AppChangedEvent());
				getView().hide();
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

	@Override
	public void onCancle() {
		getView().hide();
	}

	@Override
	public void onDelete(AppEntity entity) {
		AppMaintanceAction action = new AppMaintanceAction(entity, "delete");
	
		dispatcher.execute(action, new AsyncCallback<GetNoResult>() {
			@Override
			public void onSuccess(GetNoResult result) {
				getEventBus().fireEvent(new DataChangedEvent());
				getEventBus().fireEvent(new AppChangedEvent());
				getView().hide();
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
}
