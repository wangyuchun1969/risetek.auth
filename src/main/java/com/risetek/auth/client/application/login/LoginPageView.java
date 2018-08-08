
package com.risetek.auth.client.application.login;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.application.login.LoginWidget.LoginSubmitHandle;

class LoginPageView extends ViewWithUiHandlers<LoginUiHandlers> implements LoginPagePresenter.MyView {

	private final LoginWidget loginWidget = new LoginWidget(new LoginSubmitHandle(){

		@Override
		public void onSubmit(String username, String password) {
			getUiHandlers().Login(username, password);
		}});
    
	private DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
	
    @Inject
    public LoginPageView() {
		dockPanel.setSize("100%", "100%");
/*		
    	dockPanel.addNorth(new Label("demo use name: test@risetek.com password: test"), 40);
    	dockPanel.addNorth(new Label("demo use name: sdy@risetek.com password: sdy"), 40);
    	dockPanel.addNorth(new Label("demo use name: szw@risetek.com password: szw"), 40);
*/
    	dockPanel.add(loginWidget);
        initWidget(dockPanel);
    }

	@Override
	public void setStatus(String status) {
		loginWidget.setStatus(status);
	}
}
