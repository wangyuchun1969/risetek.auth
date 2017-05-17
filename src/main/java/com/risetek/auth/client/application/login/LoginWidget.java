package com.risetek.auth.client.application.login;

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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class LoginWidget extends SimplePanel {

	public interface LoginSubmitHandle {
		public void onSubmit(String username, String password);
	}

	private LoginSubmitHandle submitHandler;

	private final LoginBundle.Style style = LoginBundle.resources.style();
	private final FlowPanel backgroundPanel = new FlowPanel();
	private final SimplePanel title = new SimplePanel();

	private final FlowPanel username_flowpanel = new FlowPanel();
	private final TextBox username_input_box = new TextBox();
	private final SimplePanel username_tips = new SimplePanel();

	private final FlowPanel password_flowpanel = new FlowPanel();
	private final PasswordTextBox password_input_box = new PasswordTextBox();
	private final SimplePanel password_tips = new SimplePanel();

	private Image user_img = new Image(LoginBundle.resources.user_png());
	private Image password_img = new Image(LoginBundle.resources.password_png());
	
	private final Button loginSubmit = new Button();

	public LoginWidget(LoginSubmitHandle submitHandler) {
		this.submitHandler = submitHandler;
		
		loginSubmit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (username_input_box.getValue() == null
						|| "".equals(username_input_box.getValue())) {
					username_tips.getElement().getStyle()
							.setDisplay(Display.BLOCK);
					username_input_box.setFocus(true);
					return;
				}
				if (password_input_box.getValue() == null
						|| "".equals(password_input_box.getValue())) {
					password_tips.getElement().getStyle()
							.setDisplay(Display.BLOCK);
					password_input_box.setFocus(true);
					return;
				}

				loginSubmit.setText("登录中...");
				LoginWidget.this.submitHandler.onSubmit(
						username_input_box.getValue(), password_input_box.getValue());
			}
		});

		style.ensureInjected();
		setStyleName("loginWidget");
		backgroundPanel.setStyleName(style.background());

		Element div = DOM.createDiv();
		Element span = DOM.createSpan();
		span.setInnerHTML("登&nbsp;录");
		div.setInnerHTML(span.getString());
		title.getElement().setInnerHTML(div.getString());
		title.setStyleName(style.topTitle());
		backgroundPanel.add(title);

		// Interval 0
		Element internval0 = DOM.createDiv();
		internval0.setClassName(style.interval());
		backgroundPanel.getElement().appendChild(internval0);

		// --- USERNAME BOX -----
		username_flowpanel.setStyleName(style.box_outer());
		username_flowpanel.addStyleName(style.box_outer_border());
		SimplePanel username_icon = new SimplePanel();
		username_icon.setStyleName(style.box_icon());
		//username_icon.getElement().setInnerText("用户:");
		user_img.setStyleName(style.box_icon_img());
		username_icon.add(user_img);
		username_flowpanel.add(username_icon);
		SimplePanel username_input_panel = new SimplePanel();
		username_flowpanel.add(username_input_panel);
		username_input_panel.setStyleName(style.box_input());
		username_input_box.getElement().setPropertyString("ime-mode","disabled");
		username_input_panel.add(username_input_box);
		username_input_panel.getElement().setPropertyString("ime-mode","disabled");

		username_flowpanel.add(username_tips);
		username_tips.setStyleName(style.box_tips());
		username_tips.getElement().setInnerText("用户名不能为空");
		username_tips.getElement().getStyle().setDisplay(Display.NONE);

		username_input_box.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == '\r') {
					loginSubmit.click();
				} else
					reset();
			}
		});

		username_input_box.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				username_flowpanel.removeStyleName(style.box_outer_border());
				username_flowpanel.addStyleName(style.box_outer_border_highlight());
				reset();
			}
		});
		username_input_box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				username_flowpanel.removeStyleName(style.box_outer_border_highlight());
				username_flowpanel.addStyleName(style.box_outer_border());
			}
		});

		username_input_box.setStyleName(style.box_input_area());
		backgroundPanel.add(username_flowpanel);

		username_input_box.getElement().setPropertyString("placeholder",
				"请输入用户名");

		// Interval 1
		Element internval1 = DOM.createDiv();
		internval1.setClassName(style.interval());
		backgroundPanel.getElement().appendChild(internval1);

		// --- PASSWORD BOX ----
		password_flowpanel.setStyleName(style.box_outer());
		password_flowpanel.addStyleName(style.box_outer_border());

		SimplePanel password_icon = new SimplePanel();
		password_icon.setStyleName(style.box_icon());
		//password_icon.getElement().setInnerText("口令:");
		password_img.setStyleName(style.box_icon_img());
		password_icon.add(password_img);
		password_flowpanel.add(password_icon);
		SimplePanel password_input_panel = new SimplePanel();
		password_flowpanel.add(password_input_panel);
		password_input_panel.setStyleName(style.box_input());
		password_input_panel.add(password_input_box);

		password_flowpanel.add(password_tips);
		password_tips.setStyleName(style.box_tips());
		password_tips.getElement().setInnerText("口令不能为空");
		password_tips.getElement().getStyle().setDisplay(Display.NONE);

		password_input_box.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == '\r') {
					loginSubmit.click();
				} else
					reset();
			}
		});
		password_input_box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				password_flowpanel.removeStyleName(style.box_outer_border_highlight());
				password_flowpanel.addStyleName(style.box_outer_border());
			}
		});

		password_input_box.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				password_flowpanel.removeStyleName(style.box_outer_border());
				password_flowpanel.addStyleName(style.box_outer_border_highlight());
				reset();
			}
		});
		
		
		password_input_box.setStyleName(style.box_input_area());
		backgroundPanel.add(password_flowpanel);
		password_input_box.getElement().setPropertyString("placeholder", "请输入密码");

		// Interval 2
		Element internval2 = DOM.createDiv();
		internval2.setClassName(style.interval());
		backgroundPanel.getElement().appendChild(internval2);

		loginSubmit.setStyleName(style.loginButton());
		backgroundPanel.add(loginSubmit);

		add(backgroundPanel);
		// set myself on center
		getElement().setPropertyString("align", "center");
	}

	protected void onLoad() {
		reset();
		password_input_box.setText(null);
		username_input_box.setFocus(true);
	}

	public void setStatus(String status) {
		loginSubmit.setText(status);
	}
	
	private void reset() {
		loginSubmit.setText("登录");
		password_tips.getElement().getStyle().setDisplay(Display.NONE);
		username_tips.getElement().getStyle().setDisplay(Display.NONE);
	}
}
