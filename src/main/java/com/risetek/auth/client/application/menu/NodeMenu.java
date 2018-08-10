package com.risetek.auth.client.application.menu;

public class NodeMenu extends MenuWidget {
	private String name;
	public NodeMenu(String name) {
		this.name = name;
	}
	
	@Override
	public String getLabel() {
		return name;
	}

	@Override
	public void onClick() {
	}

	@Override
	public boolean canReveal() {
		for(MenuWidget submenu : menus) {
			if(submenu.canReveal())
				return true;
		}
		return false;
	}
}
