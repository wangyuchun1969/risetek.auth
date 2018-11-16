package com.risetek.auth.client.application.selfAccount.editor;

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
import com.risetek.auth.client.application.selfAccount.DataChangedEvent;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.AccountMaintanceAction;
import com.risetek.auth.shared.CurrentAccountChangePasswdAction;
import com.risetek.auth.shared.GetNoResult;


public class SelfAccountEditorPresenter extends PresenterWidget<SelfAccountEditorPresenter.MyView>
		implements PageUiHandlers {
	public interface MyView extends PopupView, HasUiHandlers<PageUiHandlers> {
		void showPassword(AccountEntity entity);
		void showNote(AccountEntity entity);
	}

	private final DispatchAsync dispatcher;

	public enum Field { PASSWD, NOTES}
	
	@Inject
	public SelfAccountEditorPresenter(final EventBus eventBus, final MyView view, DispatchAsync dispatcher) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	public void editor(AccountEntity entity, Field field) {
		switch(field) {
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
	public void onSave(AccountEntity entity) {
	
		AccountMaintanceAction action = new AccountMaintanceAction(entity, (entity.getId() < 0) ? "insert":"update");
		
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
					// getEventBus().fireEvent(new AuthorityChangedEvent());
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
	public void onEdit(AccountEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancle() {
		getView().hide();
	}

	@Override
	public void onChangePasswd(AccountEntity entity, String oldPasswd) {
		CurrentAccountChangePasswdAction action = new CurrentAccountChangePasswdAction(entity, oldPasswd);
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
					// getEventBus().fireEvent(new AuthorityChangedEvent());
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
	
}
