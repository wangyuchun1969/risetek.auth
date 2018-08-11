package com.risetek.auth.client.application.tools;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ToolsView extends ViewWithUiHandlers<MyUiHandlers> implements ToolsPresenter.MyView {
	private final SimplePanel panel = new SimplePanel();
    private Button dbInitButton = new Button("初始化数据");
    
	private final ResBundle.Style style = ResBundle.resources.style();
    
    @Inject
    ToolsView() {
    	style.ensureInjected();

    	SimplePanel viewPanel = new SimplePanel();
    	viewPanel.setStyleName(style.view_panel());
		// set myself on center
		viewPanel.getElement().setPropertyString("align", "center");
        initWidget(viewPanel);

        FlowPanel flows = new FlowPanel();
        flows.setStyleName(style.flow_panel());
        viewPanel.add(flows);

        SimplePanel space = new SimplePanel();
        space.setStyleName(style.space_panel());
        flows.add(space);
        
        flows.add(panel);
        flows.add(dbInitButton);
		dbInitButton.addClickHandler(event->getUiHandlers().dbInit());
    }
}
