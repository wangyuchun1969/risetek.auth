package com.risetek.auth.client.application.selfAccount;

import java.util.List;

import com.google.gwt.core.shared.GWT;
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
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.application.selfAccount.DataChangedEvent;
import com.risetek.auth.client.application.selfAccount.editor.SelfAccountEditorPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.util.Util;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.CurrentAccountQueryAction;
import com.risetek.auth.shared.GetResults;


public class ManageSelfAccountPresenter extends Presenter<ManageSelfAccountPresenter.MyView, ManageSelfAccountPresenter.MyProxy>
	implements MyUiHandlers, DataChangedEvent.DataChangedHandler {
	
	 public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
	    	//public void upDateDatabase(AccountEntity entity);
			public int getPageSize();	
			void showCurrentAccount(List<AccountEntity> user);
	    }
	 
	    @ProxyStandard
	    @NameToken(NameTokens.manageSelfAccount)
	    public interface MyProxy extends ProxyPlace<ManageSelfAccountPresenter> {
		}

	    private final DispatchAsync dispatcher;
	    private final SelfAccountEditorPresenter editor;

	    @Inject
	    ManageSelfAccountPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, DispatchAsync dispatcher,
				PlaceManager placeManager, SelfAccountEditorPresenter editor) {
			super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
			this.dispatcher = dispatcher;
			this.editor = editor;
			
			getView().setUiHandlers(this);
			GWT.log("Inject ManageAccountPresenter");
			
			eventBus.addHandler(DataChangedEvent.getType(), new DataChangedEvent.DataChangedHandler() {
				@Override
				public void onDataChanged() {
					update();
				}
			});
		}
	    
		@Override
	    protected void onReset() {
			GWT.log("ManageSelfAccountPresenter onReset");
	    	super.onReset();
	    }
		
		@Override
		public void update() {
			if(null == Util.currentAccountName) {
				Window.alert("current account's name is invalid");
				return;
			}
			
			CurrentAccountQueryAction action = new CurrentAccountQueryAction(Util.currentAccountName);
			GWT.log("ListAccounts: execute AccountQueryAction");
			dispatcher.execute(action, new AsyncCallback<GetResults<AccountEntity>>() {
				@Override
				public void onSuccess(GetResults<AccountEntity> result) {
					getView().showCurrentAccount(result.getResults());
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

		private int currentPage = 0;
		@Override
		public void onPager(int pages) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void refreshPages(boolean isResized, boolean forceLoad) {
			update();
		}

		@Override
		public void editPassword(AccountEntity entity) {
			editor.editor(entity, SelfAccountEditorPresenter.Field.PASSWD);
		}

		@Override
		public void onDataChanged() {
			update();
		}

		@Override
		public void editNotes(AccountEntity entity) {
			editor.editor(entity, SelfAccountEditorPresenter.Field.NOTES);
		}
	}
