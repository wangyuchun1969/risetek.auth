package com.risetek.auth.client.application.resources;

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
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.risetek.auth.client.AuthorityChangedEvent;
import com.risetek.auth.client.application.ApplicationPresenter;
import com.risetek.auth.client.application.resources.editor.EditorPresenter;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.LoggedInGatekeeper;
import com.risetek.auth.shared.DatabaseResourceMaintanceAction;
import com.risetek.auth.shared.DatabaseResourcesQueryAction;
import com.risetek.auth.shared.GetNoResult;
import com.risetek.auth.shared.GetResults;
import com.risetek.auth.shared.UserResourceEntity;

public class PresenterImpl extends Presenter<PresenterImpl.MyView, PresenterImpl.MyProxy>
	implements MyUiHandlers, DataChangedEvent.DataChangedHandler {
	
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    	void showResults(List<UserResourceEntity> lists);
		public int getPageSize();
    }

    @ProxyStandard
	@UseGatekeeper(LoggedInGatekeeper.class)
    @NameToken(NameTokens.resource)
    interface MyProxy extends ProxyPlace<PresenterImpl> {
    }

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private final EditorPresenter editor;
    @Inject
    PresenterImpl(
            EventBus eventBus,
            MyView view,
            PlaceManager placeManager,
            EditorPresenter editor,
            MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        getView().setUiHandlers(this);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.editor = editor;
        eventBus.addHandler(DataChangedEvent.getType(), this);
    }

	@Override
    protected void onReset() {
    	super.onReset();
    }

	@Override
	public void ListUsers(int keyid, int appid) {
		DatabaseResourcesQueryAction action = new DatabaseResourcesQueryAction(keyid, appid);
		
		dispatcher.execute(action, new AsyncCallback<GetResults<UserResourceEntity>>() {
			@Override
			public void onSuccess(GetResults<UserResourceEntity> result) {
				getView().showResults(result.getResults());
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
		int appid = Integer.parseInt(placeManager.getCurrentPlaceRequest().getParameter("app", "-1"));
		int keyid = Integer.parseInt(placeManager.getCurrentPlaceRequest().getParameter("key", "-1"));
		ListUsers(keyid, appid);
	}

	@Override
	public void onDataChanged() {
		int appid = Integer.parseInt(placeManager.getCurrentPlaceRequest().getParameter("app", "-1"));
		int keyid = Integer.parseInt(placeManager.getCurrentPlaceRequest().getParameter("key", "-1"));
		
		ListUsers(keyid, appid);
	}

	@Override
	public void addResource() {
		UserResourceEntity entity = new UserResourceEntity();
		entity.setId(-1);
		/*
		int appid = Integer.parseInt(placeManager.getCurrentPlaceRequest().getParameter("app", "-1"));
		int keyid = Integer.parseInt(placeManager.getCurrentPlaceRequest().getParameter("key", "-1"));
		if(appid == -1 || keyid == -1) {
			Window.alert("something wrong!");
			return;
		}
		*/
		// TODO: how to get application and key?
		//entity.setAppId(appid);
		//entity.setSecurityId(keyid);
		editor.onEdit(entity);
	}

	@Override
	public void deleteResource(UserResourceEntity entity) {

		DatabaseResourceMaintanceAction action = new DatabaseResourceMaintanceAction(entity, "delete");
		
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
	public void editResources(UserResourceEntity entity) {
		editor.onEdit(entity);
	}
}
