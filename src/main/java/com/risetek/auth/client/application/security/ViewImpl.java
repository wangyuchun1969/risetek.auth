package com.risetek.auth.client.application.security;

import java.util.List;

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
import com.risetek.auth.shared.UserSecurity;

public class ViewImpl extends ViewWithUiHandlers<MyUiHandlers> implements SecurityPresenter.MyView {

    private SimplePanel panel = new SimplePanel();
    private Button button = new Button("clickme");
    
    @Inject
    ViewImpl() {
		FlowPanel flows = new FlowPanel();
        initWidget(flows);
        
        flows.add(panel);
		// boot mark, copyright, etc.
		flows.add(createBootMark());
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
		
		button.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().ListUsers();
			}});
		
		bottomPanel.add(button);
		return wrapPanel;
	}

	@Override
	public void showUsers(List<UserSecurity> users) {
		panel.clear();
		FlowPanel lists = new FlowPanel();
		panel.add(lists);
		for(UserSecurity u:users)
			lists.add(new HTML(u.getUsername()));
	}
}
