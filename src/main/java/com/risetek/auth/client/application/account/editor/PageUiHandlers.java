package com.risetek.auth.client.application.account.editor;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.AccountEntity;


interface PageUiHandlers extends UiHandlers {
	public void onEdit(AccountEntity entity);
	public void onSave(AccountEntity entity);
	public void onDelete(AccountEntity entity);
	public void onCancle();
}
