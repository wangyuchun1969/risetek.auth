package com.risetek.auth.client.application;

import javax.inject.Inject;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.SecurityBootstrapper;
import com.risetek.auth.client.application.ApplicationBundle.ApplicationStyle;
import com.risetek.auth.client.ui.icons.Icons;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView, ResizeHandler {
    private final DockLayoutPanel mainDockPanel = new DockLayoutPanel(Unit.PX);
    private final SimplePanel head_black_wrap = new SimplePanel();
    private final HorizontalPanel head_black = new HorizontalPanel();
    private final SimplePanel menuSlot = new SimplePanel();
    private final ResizeLayoutPanel main = new ResizeLayoutPanel();
    private final ApplicationStyle style = ApplicationBundle.resources.style();

	@Inject
    public ApplicationView() {
		style.ensureInjected();
        initWidget(mainDockPanel);
        mainDockPanel.setHeight(Window.getClientHeight()+"px");

        if(!SecurityBootstrapper.authOnly) {
	        head_black_wrap.setStyleName(style.black_head_wrap());
	        head_black_wrap.getElement().setPropertyString("align", "center");
	        head_black_wrap.add(head_black);
	       	mainDockPanel.addNorth(head_black_wrap, 50);
	        head_black.setStyleName(style.blackTitle());
	        
	        Image logo = new Image(Icons.resources.RisetekLogo());
	        logo.setStyleName(style.logo());
	        if(!SecurityBootstrapper.authOnly)
	        	head_black.add(logo);
	        head_black.setCellWidth(logo, "220px");
	        
	        head_black.add(menuSlot);
	        menuSlot.setStyleName(style.menuSolt());
	        head_black.setCellHorizontalAlignment(menuSlot, HorizontalPanel.ALIGN_RIGHT);
	        
	        // 这个部分的属性修改对DROPDOWN菜单的运用是必须的�??
	        // mainDockPanel.getWidgetContainerElement(head_black).getStyle().setOverflow(Overflow.VISIBLE);
	        mainDockPanel.getWidgetContainerElement(head_black_wrap).getStyle().setOverflow(Overflow.VISIBLE);
        }
        
        main.setStyleName(style.mainSlot());
        mainDockPanel.add(main);
        // Add Resize handler
        Window.addResizeHandler(this);
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.SLOT_SetMainContent) {
        	main.clear();
            main.add(content);
        } else if( slot == ApplicationPresenter.SLOT_SetMenuContent ) {
        	menuSlot.add(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

	@Override
	public void onResize(ResizeEvent event) {
        mainDockPanel.setHeight(Window.getClientHeight()+"px");
	};
}
