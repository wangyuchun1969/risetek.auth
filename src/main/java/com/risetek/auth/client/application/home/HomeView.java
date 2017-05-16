package com.risetek.auth.client.application.home;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.risetek.auth.client.generator.IBuilderStamp;

public class HomeView extends ViewImpl implements HomePresenter.MyView {

    private SimplePanel panel = new SimplePanel();
    @Inject
    HomeView() {
		FlowPanel flows = new FlowPanel();
        initWidget(flows);
        
        flows.add(panel);
        panel.add(new Label("hello world!"));

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
		return wrapPanel;
	}
}
