package com.risetek.auth.client.application.resources.editor;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.UserResourceEntity;

interface PageUiHandlers extends UiHandlers {
	public void onEdit(UserResourceEntity entity);
	public void onSave(UserResourceEntity entity);
	public void onDelete(UserResourceEntity entity);
	public void onCancle();
}
