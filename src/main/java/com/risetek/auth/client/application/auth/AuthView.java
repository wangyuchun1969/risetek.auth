
package com.risetek.auth.client.application.auth;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class AuthView extends ViewWithUiHandlers<MyUiHandlers> implements AuthPresenter.MyView {
	private final AuthBundle.Style style = AuthBundle.resources.style();
	private final SimplePanel titlePanel = new SimplePanel();

	private final TextBox username_box = new TextBox();
	private final SimplePanel username_tips = new SimplePanel();

	private final PasswordTextBox password_box = new PasswordTextBox();
	private final SimplePanel password_tips = new SimplePanel();

	private final Button loginSubmit = new Button();
	
	private Panel createUserNamePanel() {
		FlowPanel flowpanel = new FlowPanel();
		Image img = new Image(AuthBundle.resources.user_png());
		
		// --- USERNAME BOX -----
		flowpanel.setStyleName(style.box_outer());
		flowpanel.addStyleName(style.box_outer_border());
		SimplePanel icon = new SimplePanel();
		icon.setStyleName(style.box_icon());
		img.setStyleName(style.box_icon_img());
		icon.add(img);
		flowpanel.add(icon);
		SimplePanel inputPanel = new SimplePanel();
		flowpanel.add(inputPanel);
		inputPanel.setStyleName(style.box_input());
		inputPanel.add(username_box);

		flowpanel.add(username_tips);
		username_tips.setStyleName(style.box_tips());
		username_tips.getElement().setInnerText("用户名不能为空");
		username_tips.getElement().getStyle().setDisplay(Display.NONE);

		username_box.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == '\r') {
					loginSubmit.click();
				} else
					reset();
			}
		});

		username_box.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				flowpanel.removeStyleName(style.box_outer_border());
				flowpanel.addStyleName(style.box_outer_border_highlight());
				reset();
			}
		});
		username_box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				flowpanel.removeStyleName(style.box_outer_border_highlight());
				flowpanel.addStyleName(style.box_outer_border());
			}
		});

		username_box.setStyleName(style.box_input_area());		
		username_box.getElement().setPropertyString("placeholder","请输入用户名");

		// TODO: 这个没有效
		username_box.getElement().setPropertyString("ime-mode","disabled");

		return  flowpanel;
	}
	
	
	private Panel createPasswordPanel() {
		FlowPanel flowpanel = new FlowPanel();
		Image img = new Image(AuthBundle.resources.password_png());
		
		flowpanel.setStyleName(style.box_outer());
		flowpanel.addStyleName(style.box_outer_border());

		SimplePanel icon = new SimplePanel();
		icon.setStyleName(style.box_icon());
		img.setStyleName(style.box_icon_img());
		icon.add(img);
		flowpanel.add(icon);
		SimplePanel inputPanel = new SimplePanel();
		flowpanel.add(inputPanel);
		inputPanel.setStyleName(style.box_input());
		inputPanel.add(password_box);

		flowpanel.add(password_tips);
		password_tips.setStyleName(style.box_tips());
		password_tips.getElement().setInnerText("密码不能为空");
		password_tips.getElement().getStyle().setDisplay(Display.NONE);

		password_box.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == '\r') {
					loginSubmit.click();
				} else
					reset();
			}
		});
		password_box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				flowpanel.removeStyleName(style.box_outer_border_highlight());
				flowpanel.addStyleName(style.box_outer_border());
			}
		});

		password_box.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				flowpanel.removeStyleName(style.box_outer_border());
				flowpanel.addStyleName(style.box_outer_border_highlight());
				reset();
			}
		});
		
		
		password_box.setStyleName(style.box_input_area());
		password_box.getElement().setPropertyString("placeholder", "请输入密码");
		
		return flowpanel;
	}
	
	private boolean isEmpty(TextBox box) {
		return (box.getValue() == null || "".equals(box.getValue()));
	}

	private Element createInterval() {
		Element internval = DOM.createDiv();
		internval.setClassName(style.interval());
		return internval;
	}
	
    @Inject
    public AuthView() {
    	style.ensureInjected();
		FlowPanel flowPanel = new FlowPanel();
        
		flowPanel.setStyleName(style.background());

		// --- Title ---
		titlePanel.setStyleName(style.topTitle());
		flowPanel.add(titlePanel);

		// Interval 0
		flowPanel.getElement().appendChild(createInterval());
		// --- USERNAME BOX -----
		flowPanel.add(createUserNamePanel());
		// Interval 1
		flowPanel.getElement().appendChild(createInterval());
		// --- PASSWORD BOX ----
		flowPanel.add(createPasswordPanel());
		// Interval 2
		flowPanel.getElement().appendChild(createInterval());

		// --- SUBMIT BUTTON ---
		loginSubmit.setStyleName(style.loginButton());
		loginSubmit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (isEmpty(username_box)) {
					username_tips.getElement().getStyle()
							.setDisplay(Display.BLOCK);
					username_box.setFocus(true);
					return;
				}

				if(isEmpty(password_box)) {
					password_tips.getElement().getStyle()
							.setDisplay(Display.BLOCK);
					password_box.setFocus(true);
					return;
				}

				loginSubmit.setText("登录中...");
				AuthView.this.getUiHandlers().Login(username_box.getValue(), password_box.getValue());
			}
		});
		flowPanel.add(loginSubmit);

    	SimplePanel simplePanel = new SimplePanel();
		simplePanel.setStyleName("authWidget");
		simplePanel.add(flowPanel);
		// set myself on center
		simplePanel.getElement().setPropertyString("align", "center");

        initWidget(simplePanel);
    }

	@Override
	public void reset() {
		loginSubmit.setText("登录");
		password_tips.getElement().getStyle().setDisplay(Display.NONE);
		username_tips.getElement().getStyle().setDisplay(Display.NONE);
	}

	public void setClientID(String clientId) {
		/*
		Element span = DOM.createSpan();
		Element div = DOM.createDiv();
		span.setInnerHTML("授&nbsp;权:&nbsp;" + clientId);
		div.setInnerHTML(span.getString());
		titlePanel.getElement().setInnerHTML(div.getString());
		*/
        username_box.setFocus(true);
	}
}
