package com.risetek.auth.client.application.security;

import com.gwtplatform.mvp.client.UiHandlers;

interface MyUiHandlers extends UiHandlers {
	public void ListUsers();
	public void onPager(int pages);
	public void refreshPages(boolean isResized, boolean forceLoad);
}
