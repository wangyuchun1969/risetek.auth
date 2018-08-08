package com.risetek.auth.client.application.security;

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
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.application.security.editor.EditorPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.LoggedInGatekeeper;
import com.risetek.auth.shared.DatabaseSecurityQueryAction;
import com.risetek.auth.shared.GetResults;
import com.risetek.auth.shared.UserSecurityEntity;

public class SecurityPresenter extends Presenter<SecurityPresenter.MyView, SecurityPresenter.MyProxy>
	implements MyUiHandlers, DataChangedEvent.DataChangedHandler {
	
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    	void showUsers(List<UserSecurityEntity> users);
		public int getPageSize();
    }

    @ProxyStandard
	@UseGatekeeper(LoggedInGatekeeper.class)
    @NameToken(NameTokens.security)
    interface MyProxy extends ProxyPlace<SecurityPresenter> {
    }

    private final DispatchAsync dispatcher;
    private final EditorPresenter editor;
    private final PlaceManager placeManager;
    
    @Inject
    SecurityPresenter(
            EventBus eventBus,
            MyView view,
            EditorPresenter editor,
            PlaceManager placeManager,
            MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        getView().setUiHandlers(this);
        this.dispatcher = dispatcher;
        this.editor = editor;
        this.placeManager = placeManager;
        eventBus.addHandler(DataChangedEvent.getType(), this);
    }

	@Override
    protected void onReset() {
		GWT.log("SecurityPresenter onReset");
    	super.onReset();
    }

	@Override
	public void ListUsers() {
		DatabaseSecurityQueryAction action = new DatabaseSecurityQueryAction(currentPage * getView().getPageSize(), getView().getPageSize()+1);
		
		dispatcher.execute(action, new AsyncCallback<GetResults<UserSecurityEntity>>() {
			@Override
			public void onSuccess(GetResults<UserSecurityEntity> result) {
				getView().showUsers(result.getResults());
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

	private int currentPage = 0;
	@Override
	public void onPager(int pages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshPages(boolean isResized, boolean forceLoad) {
		ListUsers();
	}

	@Override
	public void editPassword(UserSecurityEntity entity) {
		editor.editor(entity, EditorPresenter.Field.PASSWD);
	}

	@Override
	public void onDataChanged() {
		ListUsers();
	}

	@Override
	public void editMail(UserSecurityEntity entity) {
		editor.editor(entity, EditorPresenter.Field.EMAIL);
	}

	@Override
	public void editNotes(UserSecurityEntity entity) {
		editor.editor(entity, EditorPresenter.Field.NOTES);
	}

	@Override
	public void addUser() {
		UserSecurityEntity entity = new UserSecurityEntity();
		entity.setId(-1);
		editor.editor(entity, EditorPresenter.Field.ALL);
	}

	@Override
	public void deleteUser(UserSecurityEntity entity) {
		// TODO: confirm!!!
	}

	@Override
	public void editResources(UserSecurityEntity entity) {
		placeManager.revealPlace(new PlaceRequest.Builder().with("key", Integer.toString(entity.getId())).with("app", Integer.toString(0)).nameToken(NameTokens.resource).build());
	}
}
