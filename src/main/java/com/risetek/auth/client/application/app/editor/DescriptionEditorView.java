package com.risetek.auth.client.application.app.editor;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.risetek.auth.shared.AppEntity;

public class DescriptionEditorView extends PopupViewWithUiHandlers<EditorUiHandlers> 
		implements DescriptionEditorPresenter.MyView {
	private final StyleBundle.Style style = StyleBundle.resources.style();
	private final PopupPanel pop = new PopupPanel();
	private final FlowPanel docker = new FlowPanel();
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button delete_button = new Button("删除");
	private final Button close_button = new Button("放弃");
	private final Button update_button = new Button("确认");
	private final TextBox nameBox = new TextBox();
	private final TextBox notesBox = new TextBox();
	private final FlowPanel backgroundPanel = new FlowPanel();
	
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
	
	@Inject
	protected DescriptionEditorView(EventBus eventBus) {
		super(eventBus);
		style.ensureInjected();
		pop.setStyleName(style.Editor());
		initWidget(pop);
		pop.setGlassEnabled(true);
		pop.add(docker);

		docker.setWidth("400px");
		backgroundPanel.add(addItem(new Label("名称"), nameBox));
		backgroundPanel.add(addItem(new Label("备注"), notesBox));
		docker.add(backgroundPanel);
		
		delete_button.addClickHandler(event->getUiHandlers().onDelete(localEntity));
		delete_button.setStyleName(style.editorButton());
		buttonPanel.add(delete_button);

		close_button.addClickHandler(event->getUiHandlers().onCancle());
		close_button.setStyleName(style.editorButton());
		buttonPanel.add(close_button);
		
		SimplePanel space = new SimplePanel();
		space.setStyleName(style.editorSpace());
		buttonPanel.add(space);
		
		update_button.addClickHandler(event->{
			localEntity.setName(nameBox.getValue());
			localEntity.setNotes(notesBox.getValue());
			getUiHandlers().onSave(localEntity);
		});
		update_button.setStyleName(style.editorButton());
		buttonPanel.add(update_button);
		
		
		buttonPanel.setStyleName(style.editorButtonPanel());
		docker.add(buttonPanel);
		// buttonPanel.setSize("100%", "100%");
		// 安装ESC键用于快捷关闭本界面。
		Event.addNativePreviewHandler(event->{
			if (Event.ONKEYDOWN == event.getTypeInt() && 27 == event.getNativeEvent().getKeyCode()) {
				// ESC KEY
				hide();
			}
    	});
	}

	private AppEntity localEntity;
	@Override
	public void edit(AppEntity entity) {
		localEntity = entity;
		nameBox.setText(localEntity.getName());
		notesBox.setText(localEntity.getNotes());
		nameBox.setFocus(true);
		if(localEntity.getName() != null)
			delete_button.setEnabled(true);
		else
			delete_button.setEnabled(false);
	}

}
