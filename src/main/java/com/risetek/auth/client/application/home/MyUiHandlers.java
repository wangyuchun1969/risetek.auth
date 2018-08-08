package com.risetek.auth.client.application.home;

import com.gwtplatform.mvp.client.UiHandlers;

interface MyUiHandlers extends UiHandlers {
	void upDateUser();
	void gotoSecurity();
	void gotoResources();
	void dbInit();
	void logout();
}
