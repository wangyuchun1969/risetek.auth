package com.risetek.auth.client.application.home;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.generator.IBuilderStamp;

public class HomeView extends ViewWithUiHandlers<MyUiHandlers> implements HomePresenter.MyView {

    private SimplePanel panel = new SimplePanel();
    private Button securityButton = new Button("Security");
    private Button dbInitButton = new Button("初始化数据");
    @Inject
    HomeView() {
		FlowPanel flows = new FlowPanel();
        initWidget(flows);
        
        flows.add(panel);
        flows.add(securityButton);
        flows.add(dbInitButton);
		// boot mark, copyright, etc.
		flows.add(createBootMark());
		
		securityButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().gotoSecurity();
			}});
		
		dbInitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().dbInit();
			}});
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
