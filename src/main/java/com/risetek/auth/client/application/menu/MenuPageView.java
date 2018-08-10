package com.risetek.auth.client.application.menu;

import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.application.ApplicationBundle;
import com.risetek.auth.client.application.ApplicationBundle.ApplicationStyle;
import com.risetek.auth.client.ui.FlatMenu;

class MenuPageView extends ViewWithUiHandlers<MenuUiHandlers>implements MenuPagePresenter.MyView {
	private final ApplicationStyle applicationStyle = ApplicationBundle.resources.style();

	private FlowPanel menuPanel = new FlowPanel();
	
	@Inject
	public MenuPageView() {
		applicationStyle.ensureInjected();
		initWidget(menuPanel);
	}

	@Override
	public void ShowMenuList(List<MenuWidget> menus) {
		menuPanel.clear();

		for(MenuWidget menu : menus) {
			if(!menu.canReveal())
				continue;

			FlatMenu flatMenu = new FlatMenu(menu);
			menuPanel.add(flatMenu);
			
			for(MenuWidget submenu : menu.menus) {
				if(!submenu.canReveal())
					continue;
				flatMenu.appendItem(submenu);
			}
		}
	}
}
