package com.risetek.auth.client.application.app;

import com.gwtplatform.mvp.client.UiHandlers;
import com.risetek.auth.shared.AppEntity;

interface PageUiHandlers extends UiHandlers {
	public void update(int offset, int limit, String key);
	public void editor(AppEntity entity);
}
