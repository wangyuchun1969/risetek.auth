package com.risetek.auth.client.application.account;

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
import com.risetek.auth.client.application.account.DataChangedEvent;
import com.risetek.auth.client.application.account.ManageAccountPresenter;
import com.risetek.auth.client.application.account.editor.EditorPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.AccountQueryAction;
import com.risetek.auth.shared.GetResults;


public class ManageAccountPresenter extends Presenter<ManageAccountPresenter.MyView, ManageAccountPresenter.MyProxy>
	implements MyUiHandlers, DataChangedEvent.DataChangedHandler {
	
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    	//public void upDateDatabase(AccountEntity entity);
		public int getPageSize();
		//void setData(List<AccountEntity> results);
		void showAccounts(List<AccountEntity> users);
    }

    @ProxyStandard
    @NameToken(NameTokens.manageAccount)
    public interface MyProxy extends ProxyPlace<ManageAccountPresenter> {
	}

    private final DispatchAsync dispatcher;
    private final EditorPresenter editor;

    @Inject
    ManageAccountPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, DispatchAsync dispatcher,
			PlaceManager placeManager, EditorPresenter editor) {
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
		GWT.log("ManageAccountPresenter onReset");
    	super.onReset();
    }
	
	@Override
	public void update() {
		AccountQueryAction action = new AccountQueryAction(0,100);
		GWT.log("ListAccounts: execute AccountQueryAction");
		dispatcher.execute(action, new AsyncCallback<GetResults<AccountEntity>>() {
			@Override
			public void onSuccess(GetResults<AccountEntity> result) {
				getView().showAccounts(result.getResults());
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
		editor.editor(entity, EditorPresenter.Field.PASSWD);
	}

	@Override
	public void onDataChanged() {
		update();
	}

	@Override
	public void editTeams(AccountEntity entity) {
		editor.editor(entity, EditorPresenter.Field.TEAMS);
	}
	
	@Override
	public void editRoles(AccountEntity entity) {
		editor.editor(entity, EditorPresenter.Field.ROLES);
	}
	
	@Override
	public void editNotes(AccountEntity entity) {
		editor.editor(entity, EditorPresenter.Field.NOTES);
	}

	@Override
	public void addAccount() {
		AccountEntity entity = new AccountEntity();
		entity.setId(-1);
		editor.editor(entity, EditorPresenter.Field.ALL);
	}

	@Override
	public void deleteAccount(AccountEntity entity) {
		// TODO: confirm!!!
		editor.onDelete(entity);
	}
}
