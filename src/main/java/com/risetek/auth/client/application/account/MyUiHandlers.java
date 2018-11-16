package com.risetek.auth.client.application.account;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.AccountEntity;


interface MyUiHandlers extends UiHandlers {
	//public void ListAccounts();
	public void onPager(int pages);
	public void refreshPages(boolean isResized, boolean forceLoad);
	public void editPassword(AccountEntity entity);
	public void editNotes(AccountEntity entity);
	public void editRoles(AccountEntity entity);
	public void editTeams(AccountEntity entity);
	public void addAccount();
	public void deleteAccount(AccountEntity entity);
	public void update();
}
