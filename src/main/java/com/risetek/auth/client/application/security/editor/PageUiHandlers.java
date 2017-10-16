package com.risetek.auth.client.application.security.editor;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.UserSecurityEntity;

interface PageUiHandlers extends UiHandlers {
	public void onEdit(UserSecurityEntity entity);
	public void onSave(UserSecurityEntity entity);
	public void onDelete(UserSecurityEntity entity);
	public void onCancle();
}
