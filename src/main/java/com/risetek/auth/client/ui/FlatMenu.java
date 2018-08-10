package com.risetek.auth.client.ui;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.risetek.auth.client.application.menu.MenuWidget;

public class FlatMenu extends SimplePanel {
	private final FlatMenuStyleBundle.Style style = FlatMenuStyleBundle.resources.style();
	private FlowPanel dropContext = null;
	private final FlowPanel dropMenu = new FlowPanel();

	public FlatMenu(final MenuWidget menuWidget) {
		this(menuWidget.getLabel());
		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				menuWidget.onClick();
			}
		}, ClickEvent.getType());
	}

	public FlatMenu(String name) {
		style.ensureInjected();

		Label nameWarp = new Label(name);
		nameWarp.setStyleName(style.namewarp());
		dropMenu.add(nameWarp);
		
		add(dropMenu);
		dropMenu.setStyleName(style.dropdown());
		getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
	}

	public void appendItem(final MenuWidget menuWidget) {
		if (dropContext == null) {
			dropContext = new FlowPanel();
			dropContext.setStyleName(style.dropdownContent());
			dropMenu.add(dropContext);
		}

		Label item = new Label(menuWidget.getLabel());
		item.setStyleName(style.items());

		dropContext.add(item);

		item.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				menuWidget.onClick();
			}
		}, ClickEvent.getType());

	}

}
