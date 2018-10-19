package com.risetek.auth.client.application.security.editor;

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
import com.risetek.auth.client.AuthorityChangedEvent;
import com.risetek.auth.client.application.security.DataChangedEvent;
import com.risetek.auth.shared.DatabaseSecurityMaintanceAction;
import com.risetek.auth.shared.GetNoResult;
import com.risetek.auth.shared.UserSecurityEntity;

public class EditorPresenter extends PresenterWidget<EditorPresenter.MyView>
		implements PageUiHandlers {
	public interface MyView extends PopupView, HasUiHandlers<PageUiHandlers> {
		void showPassword(UserSecurityEntity entity);
		void showMail(UserSecurityEntity entity);
		void showNote(UserSecurityEntity entity);
		void showNew(UserSecurityEntity entity);
	}

	private final DispatchAsync dispatcher;

	public enum Field {ALL, PASSWD, NOTES, EMAIL}
	
	@Inject
	public EditorPresenter(final EventBus eventBus, final MyView view, DispatchAsync dispatcher) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	public void editor(UserSecurityEntity entity, Field field) {
		switch(field) {
		case ALL:
			getView().showNew(entity);
			break;
		case EMAIL:
			getView().showMail(entity);
			break;
		case NOTES:
			getView().showNote(entity);
			break;
		case PASSWD:
			getView().showPassword(entity);
			break;
		default:
			break;
		}
		RevealRootPopupContentEvent.fire(this, this);
	}
	
	@Override
	public void onSave(UserSecurityEntity entity) {
		DatabaseSecurityMaintanceAction action = new DatabaseSecurityMaintanceAction(entity, (entity.getId() < 0) ? "insert":"update");
		
		dispatcher.execute(action, new AsyncCallback<GetNoResult>() {
			@Override
			public void onSuccess(GetNoResult result) {
				// Hide editor
				getView().hide();
				// Update parent list.
				getEventBus().fireEvent(new DataChangedEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				// Convenient way to find out which exception was thrown.
				try {
					throw caught;
				} catch (StatusCodeException e) {
					// Response.SC_MOVED_TEMPORARILY
					getEventBus().fireEvent(new AuthorityChangedEvent());
				} catch (IncompatibleRemoteServiceException e) {
					Window.alert("This client is not compatible with the server;\r\n Cleanup and refresh the browser.");
				} catch (InvocationException e) {
					// the call didn't complete cleanly
					Window.alert("2" + e.toString());
				} catch (RuntimeException e) {
					Window.alert("RuntimeException:" + e);
				} catch (Exception e) {
					Window.alert("Exception:" + e);
				} catch (Throwable e) {
					// last resort -- a very unexpected exception
					Window.alert("Throwable:" + e);
				}
			}
		});
		}

	@Override
	public void onEdit(UserSecurityEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDelete(UserSecurityEntity entity) {
		
		DatabaseSecurityMaintanceAction action = new DatabaseSecurityMaintanceAction(entity, "delete");
		
		dispatcher.execute(action, new AsyncCallback<GetNoResult>() {
			@Override
			public void onSuccess(GetNoResult result) {
				// Update parent list.
				getEventBus().fireEvent(new DataChangedEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				// Convenient way to find out which exception was thrown.
				try {
					throw caught;
				} catch (StatusCodeException e) {
					// Response.SC_MOVED_TEMPORARILY
					getEventBus().fireEvent(new AuthorityChangedEvent());
				} catch (IncompatibleRemoteServiceException e) {
					Window.alert("This client is not compatible with the server;\r\n Cleanup and refresh the browser.");
				} catch (InvocationException e) {
					// the call didn't complete cleanly
					Window.alert("2" + e.toString());
				} catch (RuntimeException e) {
					Window.alert("RuntimeException:" + e);
				} catch (Exception e) {
					Window.alert("Exception:" + e);
				} catch (Throwable e) {
					// last resort -- a very unexpected exception
					Window.alert("Throwable:" + e);
				}
			}
		});
	}

	@Override
	public void onCancle() {
		getView().hide();
	}
}
