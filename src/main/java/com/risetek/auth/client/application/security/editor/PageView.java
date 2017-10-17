package com.risetek.auth.client.application.security.editor;

import com.google.gwt.user.client.Event;
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
import com.risetek.auth.shared.UserSecurityEntity;

class PageView extends PopupViewWithUiHandlers<PageUiHandlers> implements EditorPresenter.MyView {
	private final StyleBundle.Style style = StyleBundle.resources.style();
	private final FlowPanel docker = new FlowPanel();
    
	private final FlowPanel backgroundPanel = new FlowPanel();
    
	private final PopupPanel pop = new PopupPanel();
	
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button close_button = new Button("放弃");
	private final Button update_button = new Button("确认");
	
	
	private final TextBox username = new TextBox();
	private final TextBox eMailBox = new TextBox();
	private final TextBox notesBox = new TextBox();
	private final PasswordTextBox passedBox = new PasswordTextBox();

	@Inject
	public PageView(EventBus eventBus) {
		super(eventBus);
		style.ensureInjected();
		pop.setStyleName(style.Editor());
		initWidget(pop);
		pop.setGlassEnabled(true);
		pop.add(docker);

		docker.setWidth("400px");
		
		backgroundPanel.add(addItem(new Label("用户"), username));
		backgroundPanel.add(addItem(new Label("密码"), passedBox));
		backgroundPanel.add(addItem(new Label("电邮"), eMailBox));
		backgroundPanel.add(addItem(new Label("备注"), notesBox));
		docker.add(backgroundPanel);

		close_button.addClickHandler(event->getUiHandlers().onCancle());
		close_button.setStyleName(style.editorButton());
		buttonPanel.add(close_button);
		
		SimplePanel space = new SimplePanel();
		space.setStyleName(style.editorSpace());
		buttonPanel.add(space);
		
		update_button.addClickHandler(event->{
			localEntity.setPasswd(passedBox.getValue());
			localEntity.setUsername(username.getValue());
			localEntity.setEmail(eMailBox.getValue());
			localEntity.setNotes(notesBox.getValue());
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
	
	private UserSecurityEntity localEntity;
	private void showItems(UserSecurityEntity entity) {
		localEntity = entity;
		username.setText(entity.getUsername());
		eMailBox.setText(entity.getEmail());
		notesBox.setText(entity.getNotes());
		passedBox.setValue(null);
	}
	@Override
	public void showPassword(UserSecurityEntity entity) {
		showItems(entity);
		username.setEnabled(false);
		eMailBox.setEnabled(false);
		notesBox.setEnabled(false);
		passedBox.setEnabled(true);
	}
	
	@Override
	public void showMail(UserSecurityEntity entity) {
		showItems(entity);
		username.setEnabled(false);
		eMailBox.setEnabled(true);
		notesBox.setEnabled(false);
		passedBox.setEnabled(false);
	}

	@Override
	public void showNote(UserSecurityEntity entity) {
		showItems(entity);
		username.setEnabled(false);
		eMailBox.setEnabled(false);
		notesBox.setEnabled(true);
		passedBox.setEnabled(false);
	}

	@Override
	public void showNew(UserSecurityEntity entity) {
		showItems(entity);
		username.setEnabled(true);
		eMailBox.setEnabled(true);
		notesBox.setEnabled(true);
		passedBox.setEnabled(true);
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
}
