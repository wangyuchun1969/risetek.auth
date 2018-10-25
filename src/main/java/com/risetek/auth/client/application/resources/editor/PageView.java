package com.risetek.auth.client.application.resources.editor;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.risetek.auth.client.application.ApplicationInfoRecord;
import com.risetek.auth.shared.AppEntity;
import com.risetek.auth.shared.UserResourceEntity;
import com.risetek.auth.shared.UserSecurityEntity;

class PageView extends PopupViewWithUiHandlers<PageUiHandlers> implements EditorPresenter.MyView {
	private final StyleBundle.Style style = StyleBundle.resources.style();
	private final FlowPanel docker = new FlowPanel();
    
	private final FlowPanel backgroundPanel = new FlowPanel();
    
	private final PopupPanel pop = new PopupPanel();
	
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button close_button = new Button("放弃");
	private final Button update_button = new Button("确认");
	//--------by chenzhen------------
	private final ListBox combobox = new ListBox();
	private final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle(); 
	private final SuggestBox suggestUsrBox = new SuggestBox(oracle);
	//--------------------
	private final TextBox keyBox = new TextBox();
	private final TextBox valueBox = new TextBox();
	
	@Inject
	public PageView(EventBus eventBus) {
		super(eventBus);
		style.ensureInjected();
		pop.setStyleName(style.Editor());
		initWidget(pop);
		pop.setGlassEnabled(true);
		pop.add(docker);

		docker.setWidth("400px");
		//-------by chenz-------------
		suggestUsrBox.setSize("280px", "25px");
		suggestUsrBox.getElement().getStyle().setFontSize(20, Unit.PX);
		suggestUsrBox.getElement().getStyle().setColor("#42b4ff");
		
		backgroundPanel.add(addItem(new Label("使用者"), suggestUsrBox));
		backgroundPanel.add(addItem(new Label("应用名"), combobox));
		//-------------------------------
		backgroundPanel.add(addItem(new Label("Key"), keyBox));
		backgroundPanel.add(addItem(new Label("Value"), valueBox));
		docker.add(backgroundPanel);

		close_button.addClickHandler(event->getUiHandlers().onCancle());
		close_button.setStyleName(style.editorButton());
		buttonPanel.add(close_button);
		
		SimplePanel space = new SimplePanel();
		space.setStyleName(style.editorSpace());
		buttonPanel.add(space);
		
		update_button.addClickHandler(event->{
			//------------by chenz---------------------
			localEntity.setUsername(suggestUsrBox.getText());
			localEntity.setApplication(combobox.getSelectedItemText());
			//---------------------------------
			localEntity.setKey(keyBox.getValue());
			localEntity.setValue(valueBox.getValue());
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
	
	private UserResourceEntity localEntity;
	public void showEditor(UserResourceEntity entity) {
		localEntity = entity;
		//------------------------
		comboboxInit();
		oracleInit();
		suggestUsrBox.setText(entity.getUsername());
		for(int i = 0;  i < combobox.getItemCount(); i++) {
			if(combobox.getItemText(i).equals(entity.getApplication())) {
				combobox.setSelectedIndex(i);
				break;
			}
		}
		//--------------------------
		keyBox.setText(entity.getKey());
		valueBox.setText(entity.getValue());
		
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
	
	private void comboboxInit() {
		combobox.clear();
		if(ApplicationInfoRecord.apps == null) {
			 combobox.addItem("null");
			 return;
		}
			 
		for(AppEntity app : ApplicationInfoRecord.apps) {
			combobox.addItem(app.getName());
		 }
	}
	
	private void oracleInit() {
		oracle.clear();
		for(UserSecurityEntity usr : ApplicationInfoRecord.users) {
			oracle.add(usr.getUsername());
		}
	}
}
