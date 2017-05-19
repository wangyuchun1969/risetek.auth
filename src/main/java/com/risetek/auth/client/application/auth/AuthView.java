
package com.risetek.auth.client.application.auth;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.application.auth.AuthWidget.AuthSubmitHandle;

class AuthView extends ViewWithUiHandlers<MyUiHandlers> implements AuthPresenter.MyView {

	private final AuthWidget loginWidget = new AuthWidget(new AuthSubmitHandle(){

		@Override
		public void onSubmit(String username, String password) {
			getUiHandlers().Login(username, password);
		}});
    
	private DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
	
    @Inject
    public AuthView() {
		dockPanel.setSize("100%", "100%");
    	dockPanel.add(loginWidget);
        initWidget(dockPanel);
    }

	@Override
	public void setStatus(String status) {
		loginWidget.setStatus(status);
	}
}
