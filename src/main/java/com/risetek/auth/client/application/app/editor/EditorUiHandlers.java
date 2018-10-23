package com.risetek.auth.client.application.app.editor;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.AppEntity;

interface EditorUiHandlers extends UiHandlers {
	public void onEdit(AppEntity entity);
	public void onSave(AppEntity entity);
	public void onDelete(AppEntity entity);
	public void onCancle();
}
