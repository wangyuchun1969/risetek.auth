package com.risetek.auth.client.application.security;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.UserSecurityEntity;

interface MyUiHandlers extends UiHandlers {
	public void ListUsers();
	public void onPager(int pages);
	public void refreshPages(boolean isResized, boolean forceLoad);
	public void editPassword(UserSecurityEntity entity);
	public void editMail(UserSecurityEntity entity);
	public void editNotes(UserSecurityEntity entity);
	public void addUser();
	public void deleteUser(UserSecurityEntity entity);
}
