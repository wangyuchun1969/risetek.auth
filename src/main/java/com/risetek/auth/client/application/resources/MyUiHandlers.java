package com.risetek.auth.client.application.resources;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.UserResourceEntity;

interface MyUiHandlers extends UiHandlers {
	public void ListUsers(int keyid, int appid);
	public void onPager(int pages);
	public void refreshPages(boolean isResized, boolean forceLoad);
	public void addResource();
	public void deleteResource(UserResourceEntity entity);
	public void editResources(UserResourceEntity entity);
}
