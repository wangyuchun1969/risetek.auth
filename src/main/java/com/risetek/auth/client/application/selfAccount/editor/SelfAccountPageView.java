package com.risetek.auth.client.application.selfAccount.editor;

import org.apache.xerces.impl.dv.util.Base64;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.CurrentAccountChangePasswdAction;


class SelfAccountPageView extends PopupViewWithUiHandlers<PageUiHandlers> implements SelfAccountEditorPresenter.MyView {
	private final StyleBundle.Style style = StyleBundle.resources.style();
	private final FlowPanel docker = new FlowPanel();
    
	private final FlowPanel backgroundPanel = new FlowPanel();
	private final SimplePanel titlePanel = new SimplePanel();
	private final PopupPanel pop = new PopupPanel();
	
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button close_button = new Button("放弃");
	private final Button update_button = new Button("确认");
	private final Button changePasswd_button = new Button("确认");
	private final TextBox accountNameBox = new TextBox();
	private final TextBox notesBox = new TextBox();
	private final PasswordTextBox passwdBox = new PasswordTextBox();
	private final PasswordTextBox newPasswdBox = new PasswordTextBox();
	private final PasswordTextBox confirmNewPasswdBox = new PasswordTextBox();
	
	private final Widget passwd_item = addItem(new Label("当前密码"), passwdBox);
	private final Widget newPasswd_item = addItem(new Label("新密码"), newPasswdBox);
	private final Widget confirmNewPasswd_item = addItem(new Label("确认新密码"), confirmNewPasswdBox);
	private final Widget notes_item = addItem(new Label("备注"), notesBox);
	
	private Label title_label = new Label();
	@Inject
	public SelfAccountPageView(EventBus eventBus) {
		super(eventBus);
		style.ensureInjected();
		pop.setStyleName(style.Editor());
		initWidget(pop);
		pop.setGlassEnabled(true);
		pop.add(docker);
		docker.setWidth("420px");
		titlePanel.getElement().getStyle().setBackgroundColor("#C6E2FF");
		titlePanel.getElement().getStyle().setHeight(40, Unit.PX);
		titlePanel.add(title_label);
		title_label.setStyleName(style.EditorTitleLabel());
		
		backgroundPanel.add(titlePanel);
		backgroundPanel.add(passwd_item);
		backgroundPanel.add(newPasswd_item);
		backgroundPanel.add(confirmNewPasswd_item);
		backgroundPanel.add(notes_item);
		docker.add(backgroundPanel);

		close_button.addClickHandler(event->getUiHandlers().onCancle());
		close_button.setStyleName(style.editorButton());
		buttonPanel.add(close_button);
		
		SimplePanel space = new SimplePanel();
		space.setStyleName(style.editorSpace());
		buttonPanel.add(space);
		
		update_button.addClickHandler(event->{
			if(!isvalid())
				return;
			localEntity.setName(accountNameBox.getText());
			localEntity.setPassword(passwdBox.getText());
			localEntity.setNotes(notesBox.getText());
			getUiHandlers().onSave(localEntity);
		});
		update_button.setStyleName(style.editorButton());
		buttonPanel.add(update_button);
		
		changePasswd_button.setStyleName(style.editorButton());
		buttonPanel.add(changePasswd_button);
		changePasswd_button.addClickHandler(event->{
			changePasswd_button_click_function();
		});
		
		buttonPanel.setStyleName(style.editorButtonPanel());
		docker.add(buttonPanel);
	
		// 安装ESC键用于快捷关闭本界面。
		Event.addNativePreviewHandler(event->{
			if (Event.ONKEYDOWN == event.getTypeInt() && 27 == event.getNativeEvent().getKeyCode()) {
				// ESC KEY
				hide();
			}
    	});
	}
	
	private AccountEntity localEntity;
	private void showItems(AccountEntity entity) {
		localEntity = entity;
		title_label.setText("账号信息");
		accountNameBox.setText(entity.getName());
		notesBox.setText(entity.getNotes());
		passwdBox.setText(entity.getPassword());
		update_button.setVisible(false);
		changePasswd_button.setVisible(false);
		passwd_item.setVisible(false);
		newPasswd_item.setVisible(false);
		confirmNewPasswd_item.setVisible(false);
		notes_item.setVisible(false);
	}
	
	@Override
	public void showPassword(AccountEntity entity) {
		showItems(entity);
		title_label.setText("修改密码");
		passwd_item.setVisible(true);
		passwdBox.setText(null);
		newPasswdBox.setText(null);
		confirmNewPasswdBox.setText(null);
		passwd_item.setVisible(true);
		newPasswd_item.setVisible(true);
		confirmNewPasswd_item.setVisible(true);
		
		changePasswd_button.setVisible(true);
	}
	
	@Override
	public void showNote(AccountEntity entity) {
		showItems(entity);
		title_label.setText("修改注释");
		notes_item.setVisible(true);
		update_button.setVisible(true);
		changePasswd_button.setVisible(false);
	}

	private Widget addItem(Widget label, Widget box) {
		FlowPanel fPanel = new FlowPanel();
		SimplePanel sp = new SimplePanel();
		fPanel.setStyleName(style.ItemOuter());
		label.setStyleName(style.EditorLable());
		fPanel.add(label);
		sp.addStyleName(style.editorBoxs());
		sp.add(box);
		fPanel.add(sp);
		return fPanel;
	}
	
	private boolean isvalid() {
		if( null == passwdBox.getText() || passwdBox.getText().equals(null) || passwdBox.getText().equals("")) {
			Window.alert("密码不能为空");
			return false;
		}
		return true;
	}
	
	private void changePasswd_button_click_function() {
		//确认密码
		if( null == passwdBox.getText() || passwdBox.getText().equals(null) || passwdBox.getText().equals("")) {
			Window.alert("请输入当前密码");
			return;
		}
		if( null == newPasswdBox.getText() || newPasswdBox.getText().equals(null) || newPasswdBox.getText().equals("") ||
				null == confirmNewPasswdBox.getText() || !newPasswdBox.getText().equals(confirmNewPasswdBox.getText()) ) {
			Window.alert("请输入并确认新密码");
			return;
		}
		//将新密码写入updateAccount中。
		AccountEntity updateAccount = new AccountEntity();
		updateAccount = localEntity;
		updateAccount.setPassword(newPasswdBox.getText());
		getUiHandlers().onChangePasswd(updateAccount, passwdBox.getText());
	}
}
