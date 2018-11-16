package com.risetek.auth.client.application.account;

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.shared.AccountEntity;
import com.risetek.auth.shared.UserSecurityEntity;

public class ViewImpl extends ViewWithUiHandlers<MyUiHandlers> implements ManageAccountPresenter.MyView, ResizeHandler {
	private final DockLayoutPanel frameDocker = new DockLayoutPanel(Unit.PX);
	private final ResizeLayoutPanel resizePanel = new ResizeLayoutPanel();
	private final SimplePanel pagerSlotPanel = new SimplePanel();
	private final ListDataProvider<AccountEntity> dataprovider;
	private final NoSelectionModel<AccountEntity> selectionModel = new NoSelectionModel<AccountEntity>();
	private final Button addUserButton = new Button("添加账号");
	
	private final CellTable<AccountEntity> celltable = new CellTable<AccountEntity>(10, TableResources.resources) {
		@Override
		protected void onBrowserEvent2(Event event) {
			super.onBrowserEvent2(event);
			
			if(Event.ONMOUSEWHEEL == event.getTypeInt()) {
				if(event.getMouseWheelVelocityY() > 0) {
					getUiHandlers().onPager(1);
				} else {
					getUiHandlers().onPager(-1);
				}
			}
		}
		
		// Override getKeyboardSelectedElement,
		// so when we change page, the focus should be lost.
		@Override
		protected Element getKeyboardSelectedElement() {
			return null;
		}
	};
    
	private void initTable() {
		dataprovider.addDataDisplay(celltable);
		celltable.setSize("100%", "100%");
		GWT.log("initTable 1");
		CustomColumn<AccountEntity> id_Column = new CustomColumn<AccountEntity>("tips") {
			@Override
			public String getValue(AccountEntity object) {
				return (null==object)?null:Integer.toString(object.getId());
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					final AccountEntity object, NativeEvent event) {
				if (object == null)
					return;
				String type = event.getType();
				if ("click".equals(type)) {
					boolean bool = Window.confirm("您确认删除此项吗？"); 
					if(bool) {
						getUiHandlers().deleteAccount(object);
					}
				}
				else
					super.onBrowserEvent(context, elem, object, event);
			}
		};
		celltable.addColumn(id_Column, "ID");
		celltable.setColumnWidth(id_Column, 60, Unit.PX);
		
		CustomColumn<AccountEntity> ident_Column = new CustomColumn<AccountEntity>("tips") {
			@Override
			public String getValue(AccountEntity object) {
				return (null==object)?null:object.getName();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					final AccountEntity object, NativeEvent event) {
				if (object == null)
					return;
				String type = event.getType();
				if ("click".equals(type)) {
					//getUiHandlers().editResources(object);
				}
				else
					super.onBrowserEvent(context, elem, object, event);
			}
		};

		celltable.addColumn(ident_Column, "账号");
		celltable.setColumnWidth(ident_Column, 220, Unit.PX);

		CustomColumn<AccountEntity> passwd_Column = new CustomColumn<AccountEntity>("tips") {
			@Override
			public String getValue(AccountEntity object) {
				return (null==object)?null:"********";
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					final AccountEntity object, NativeEvent event) {
				if (object == null)
					return;
				String type = event.getType();
				if ("click".equals(type)) {
					getUiHandlers().editPassword(object);
				}
				else
					super.onBrowserEvent(context, elem, object, event);
			}
		};
		celltable.addColumn(passwd_Column, "密码");
		celltable.setColumnWidth(passwd_Column, 100, Unit.PX);
		
		CustomColumn<AccountEntity> roles_Column = new CustomColumn<AccountEntity>("tips") {
			@Override
			public String getValue(AccountEntity object) {
				return (null==object)?null:object.getRoles();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					final AccountEntity object, NativeEvent event) {
				if (object == null)
					return;
				String type = event.getType();
				if ("click".equals(type)) {
					getUiHandlers().editRoles(object);
				}
				else
					super.onBrowserEvent(context, elem, object, event);
			}
		};
		celltable.addColumn(roles_Column, "roles");
		celltable.setColumnWidth(roles_Column, 350, Unit.PX);
		
		CustomColumn<AccountEntity> teams_Column = new CustomColumn<AccountEntity>("tips") {
			@Override
			public String getValue(AccountEntity object) {
				return (null==object)?null:object.getTeams();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					final AccountEntity object, NativeEvent event) {
				if (object == null)
					return;
				String type = event.getType();
				if ("click".equals(type)) {
					getUiHandlers().editTeams(object);
				}
				else
					super.onBrowserEvent(context, elem, object, event);
			}
		};

		celltable.addColumn(teams_Column, "teams");
		celltable.setColumnWidth(teams_Column, 80, Unit.PX);
	
		
		CustomColumn<AccountEntity> notes_Column = new CustomColumn<AccountEntity>("tips") {
			@Override
			public String getValue(AccountEntity object) {
				return (null==object)?null:object.getNotes();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					final AccountEntity object, NativeEvent event) {
				if (object == null)
					return;
				String type = event.getType();
				if ("click".equals(type)) {
					getUiHandlers().editNotes(object);
				}
				else
					super.onBrowserEvent(context, elem, object, event);
			}
		};
		
		celltable.addColumn(notes_Column, "备注");

		// ------------------------------------------------------------------
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// getUiHandlers().showChart(selectionModel.getLastSelectedObject().getBaseInfo());
			}
		});
		
		celltable.setSelectionModel(selectionModel);
		celltable.sinkEvents(Event.ONCLICK);
		celltable.sinkEvents(Event.ONMOUSEWHEEL);
		celltable.setTableLayoutFixed(true);
		
	}
	
    @Inject
    ViewImpl(final EventBus eventBus, ListDataProvider<AccountEntity> dataProvider) {
		GWT.log("Inject ViewImpl");
    	dataprovider = dataProvider;
		frameDocker.setSize("100%", "100%");
		initWidget(frameDocker);
		pagerSlotPanel.setSize("100%", "100%");
		pagerSlotPanel.add(addUserButton);
		frameDocker.addSouth(pagerSlotPanel, 30);
		resizePanel.setSize("100%", "100%");
		resizePanel.addResizeHandler(this);
		initTable();
		resizePanel.add(celltable);
		frameDocker.add(resizePanel);
		
		addUserButton.addClickHandler(event->getUiHandlers().addAccount());
    }
    
	@Override
	public void showAccounts(List<AccountEntity> users) {
		GWT.log("showAccounts");
		int filled = users.size() % celltable.getPageSize();
		if(filled != 0) {
			filled = celltable.getPageSize() - filled;
			for (int looper = 0; looper < filled; looper++)
				users.add(null);
		}

		dataprovider.setList(users);
	}

	// TODO: 这个高度是与CSS相关的。
	private final int cellHeight = 26;
	@Override
	public void onResize(ResizeEvent event) {
		int offsetHeight = event.getHeight() - celltable.getHeaderHeight();
		celltable.setPageSize(offsetHeight / cellHeight);
		getUiHandlers().refreshPages(true, false);
	}

	//------------------------------------------------------------------------------------
	
	abstract class CustomColumn<T> extends Column<T, String> {
		public String tips;
		public CustomColumn(String tips) {
			super(new CustomCell());
			this.tips = tips;
		}

		@Override
		public void render(Context context, T object, SafeHtmlBuilder sb) {
			if(object == null)
				sb.appendHtmlConstant("&nbsp;");
			else
				super.render(context, object, sb);
		}
		
		@Override
		public void onBrowserEvent(Context context, Element elem, final T object, NativeEvent event) {
			super.onBrowserEvent(context, elem, object, event);
			String type = event.getType();
			Style style = elem.getParentElement().getStyle();
			if ("mouseover".equals(type)) {
				style.setCursor(Cursor.POINTER);
				style.setColor("red");
			} else if ("mouseout".equals(type)) {
				style.setCursor(Cursor.DEFAULT);
				style.clearColor();
			}
		}
	}
		
	class CustomCell extends AbstractSafeHtmlCell<String> {

		public CustomCell() {
			super(SimpleSafeHtmlRenderer.getInstance(), "mouseover",
					"mouseout", "click");
		}

		@Override
		public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
			if (value != null && value.asString().length() > 0)
				sb.append(value);
			else
				sb.appendHtmlConstant("&nbsp;");
		}
	}
	
	@Override
	public int getPageSize() {
		return celltable.getPageSize();
	}
}
