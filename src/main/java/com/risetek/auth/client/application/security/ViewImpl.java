package com.risetek.auth.client.application.security;

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.shared.UserSecurityEntity;

public class ViewImpl extends ViewWithUiHandlers<MyUiHandlers> implements SecurityPresenter.MyView, ResizeHandler {
	private final DockLayoutPanel frameDocker = new DockLayoutPanel(Unit.PX);
	private final ResizeLayoutPanel resizePanel = new ResizeLayoutPanel();
	private final SimplePanel pagerSlotPanel = new SimplePanel();
	private final ListDataProvider<UserSecurityEntity> dataprovider;
	private final NoSelectionModel<UserSecurityEntity> selectionModel = new NoSelectionModel<UserSecurityEntity>();

	private final CellTable<UserSecurityEntity> celltable = new CellTable<UserSecurityEntity>(10, TableResources.resources) {
		@Override
		protected void onBrowserEvent2(Event event) {
			super.onBrowserEvent2(event);
			
			if(event.getTypeInt() == Event.ONMOUSEWHEEL) {
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

		NullFilledTextColumn id_Column = new NullFilledTextColumn() {
			@Override
			public String getValue(UserSecurityEntity object) {
				return Integer.toString(object.getId());
			}
		};
		celltable.addColumn(id_Column, "ID");
		celltable.setColumnWidth(id_Column, 60, Unit.PX);
		
		NullFilledTextColumn ident_Column = new NullFilledTextColumn() {
			@Override
			public String getValue(UserSecurityEntity object) {
				return object.getUsername();
			}
		};
		celltable.addColumn(ident_Column, "用户名");
		celltable.setColumnWidth(ident_Column, 160, Unit.PX);
	
		NullFilledTextColumn email_Column = new NullFilledTextColumn() {
			@Override
			public String getValue(UserSecurityEntity object) {
				return object.getEmail();
			}
		};
		celltable.addColumn(email_Column, "eMail");
		celltable.setColumnWidth(email_Column, 160, Unit.PX);

		NullFilledTextColumn notes_Column = new NullFilledTextColumn() {
			@Override
			public String getValue(UserSecurityEntity object) {
				return object.getNotes();
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
    ViewImpl(final EventBus eventBus, ListDataProvider<UserSecurityEntity> dataProvider) {
		dataprovider = dataProvider;
		frameDocker.setSize("100%", "100%");
		initWidget(frameDocker);
		pagerSlotPanel.setSize("100%", "100%");
		frameDocker.addSouth(pagerSlotPanel, 30);
		resizePanel.setSize("100%", "100%");
		resizePanel.addResizeHandler(this);
		initTable();
		resizePanel.add(celltable);
		frameDocker.add(resizePanel);
    }

	@Override
	public void showUsers(List<UserSecurityEntity> users) {
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

	private abstract class NullFilledTextColumn extends TextColumn<UserSecurityEntity> {
		@Override
		public void render(Context context, UserSecurityEntity object, SafeHtmlBuilder sb) {
			if(object == null)
				sb.appendHtmlConstant("&nbsp;");
			else
				super.render(context, object, sb);
		}
	}

	@Override
	public int getPageSize() {
		return celltable.getPageSize();
	}
}
