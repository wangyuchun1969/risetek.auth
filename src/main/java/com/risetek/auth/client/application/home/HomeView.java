package com.risetek.auth.client.application.home;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
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
    private Button resourcesButton = new Button("权限编辑");
    private Button dbInitButton = new Button("初始化数据");
    private Button logoutButton = new Button("Logout");
    @Inject
    HomeView() {
		FlowPanel flows = new FlowPanel();
        initWidget(flows);
        
        flows.add(panel);
        flows.add(securityButton);
        flows.add(resourcesButton);
        flows.add(dbInitButton);
        flows.add(logoutButton);
		// boot mark, copyright, etc.
		flows.add(createBootMark());
		
		securityButton.addClickHandler(event->getUiHandlers().gotoSecurity());
		resourcesButton.addClickHandler(event->getUiHandlers().gotoResources());
		dbInitButton.addClickHandler(event->getUiHandlers().dbInit());
		logoutButton.addClickHandler(event->getUiHandlers().logout());
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
