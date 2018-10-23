package com.risetek.auth.client.application.home;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.generator.IBuilderStamp;

public class HomeView extends ViewWithUiHandlers<MyUiHandlers> implements HomePresenter.MyView {
	private final SimplePanel panel = new SimplePanel();
    private Button dbInitButton = new Button("初始化数据");
    
	private final ResBundle.Style style = ResBundle.resources.style();
    
    @Inject
    HomeView() {
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
        
        Label label = new Label("我想实现一个OpenAuth的微服务");
        label.setStyleName(style.notes_mydream());
        flows.add(label);

        space = new SimplePanel();
        space.setStyleName(style.space_panel());
        flows.add(space);
        
        flows.add(panel);
        //flows.add(dbInitButton);
		// boot mark, copyright, etc.
		flows.add(createBootMark());
		
		dbInitButton.addClickHandler(event->getUiHandlers().dbInit());
    }

	private Widget createBootMark() {
		SimplePanel wrapPanel = new SimplePanel();
		FlowPanel bottomPanel = new FlowPanel();
		
		IBuilderStamp stamp = GWT.create(IBuilderStamp.class);
		HTML buildTimestamp = new HTML(stamp.getBuilderStamp());

		wrapPanel.getElement().setPropertyString("align", "center");

		wrapPanel.add(bottomPanel);

		bottomPanel.add(new HTML("成都瑞科技术有限公司版权所有"));
		bottomPanel.add(buildTimestamp);
		return wrapPanel;
	}

	@Override
	public void showUserInfo(String info) {
		panel.getElement().setInnerText("---" + info);
	}
}
