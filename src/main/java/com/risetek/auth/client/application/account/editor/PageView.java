package com.risetek.auth.client.application.account.editor;

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


class PageView extends PopupViewWithUiHandlers<PageUiHandlers> implements EditorPresenter.MyView {
	private final StyleBundle.Style style = StyleBundle.resources.style();
	private final FlowPanel docker = new FlowPanel();
    
	private final FlowPanel backgroundPanel = new FlowPanel();
    
	private final PopupPanel pop = new PopupPanel();
	
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button close_button = new Button("放弃");
	private final Button update_button = new Button("确认");

	
	private final TextBox username = new TextBox();
	private final TextBox rolesBox = new TextBox();
	private final TextBox teamsBox = new TextBox();
	private final TextBox notesBox = new TextBox();
	private final PasswordTextBox passedBox = new PasswordTextBox();

	private final Widget user_item = addItem(new Label("账号"), username);
	private final Widget passwd_item = addItem(new Label("密码"), passedBox);
	private final Widget roles_item = addItem(new Label("roles"), rolesBox);
	private final Widget teams_item = addItem(new Label("teams"), teamsBox);
	private final Widget notes_item = addItem(new Label("备注"), notesBox);
	@Inject
	public PageView(EventBus eventBus) {
		super(eventBus);
		style.ensureInjected();
		pop.setStyleName(style.Editor());
		initWidget(pop);
		pop.setGlassEnabled(true);
		pop.add(docker);

		docker.setWidth("400px");
		backgroundPanel.add(user_item);
		backgroundPanel.add(passwd_item);
		backgroundPanel.add(roles_item);
		backgroundPanel.add(teams_item);
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
			localEntity.setPassword(passedBox.getText());
			localEntity.setName(username.getText());
			localEntity.setRoles(rolesBox.getText());
			localEntity.setTeams(teamsBox.getText());
			localEntity.setNotes(notesBox.getText());
			getUiHandlers().onSave(localEntity);
		});
		update_button.setStyleName(style.editorButton());
		buttonPanel.add(update_button);
		
		
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
		username.setText(entity.getName());
		rolesBox.setText(entity.getRoles());
		teamsBox.setText(entity.getTeams());
		notesBox.setText(entity.getNotes());
		passedBox.setText(entity.getPassword());
	}
	@Override
	public void showPassword(AccountEntity entity) {
		showItems(entity);
		user_item.setVisible(false);
		passwd_item.setVisible(true);
		roles_item.setVisible(false);
		teams_item.setVisible(false);
		notes_item.setVisible(false);
	}
	
	@Override
	public void showRoles(AccountEntity entity) {
		showItems(entity);
		user_item.setVisible(false);
		passwd_item.setVisible(false);
		roles_item.setVisible(true);
		teams_item.setVisible(false);
		notes_item.setVisible(false);
	}

	@Override
	public void showTeams(AccountEntity entity) {
		showItems(entity);
		user_item.setVisible(false);
		passwd_item.setVisible(false);
		roles_item.setVisible(false);
		teams_item.setVisible(true);
		notes_item.setVisible(false);
	}
	
	@Override
	public void showNote(AccountEntity entity) {
		showItems(entity);
		user_item.setVisible(false);
		passwd_item.setVisible(false);
		roles_item.setVisible(false);
		teams_item.setVisible(false);
		notes_item.setVisible(true);
	}

	@Override
	public void showNew(AccountEntity entity) {
		showItems(entity);
		user_item.setVisible(true);
		passwd_item.setVisible(true);
		roles_item.setVisible(true);
		teams_item.setVisible(true);
		notes_item.setVisible(true);
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
		if( null == username.getText() || username.getText().equals(null) || username.getText().equals("")) {
			Window.alert("账号不能为空");
			return false;
		}
		if( null == passedBox.getText() || passedBox.getText().equals(null) || passedBox.getText().equals("")) {
			Window.alert("密码不能为空");
			return false;
		}
		if( null == rolesBox.getText() || rolesBox.getText().equals(null) || rolesBox.getText().equals("")) {
			Window.alert("roles不能为空");
			return false;
		}
		if( rolesBox.getText().indexOf("admin") != -1 && !getUiHandlers().accountIsLoginAccount(username.getText())) {
			Window.alert("您不能添加/管理\"admin\"类型账户(登录账户除外)");
			return false;
		}
		if( null == teamsBox.getText() || teamsBox.getText().equals(null) || teamsBox.getText().equals("")) {
			Window.alert("teams不能为空");
			return false;
		}
		return true;
	}
}
