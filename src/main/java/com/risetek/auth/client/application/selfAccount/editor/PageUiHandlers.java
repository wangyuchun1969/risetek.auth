package com.risetek.auth.client.application.selfAccount.editor;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.AccountEntity;


interface PageUiHandlers extends UiHandlers {
	public void onEdit(AccountEntity entity);
	public void onSave(AccountEntity entity);
	public void onCancle();
	public void onChangePasswd(AccountEntity entity, String oldPasswd);
}
