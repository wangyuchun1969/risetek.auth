package com.risetek.auth.client.application.app;

import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.auth.shared.AppEntity;


class PageView extends ViewWithUiHandlers<PageUiHandlers> implements PagePresenter.MyView, ResizeHandler {
	private final ResizeLayoutPanel resizeLayoutPanel = new ResizeLayoutPanel();
	private final DockLayoutPanel docker = new DockLayoutPanel(Unit.PX);
	private final DescriptionTable descriptionTable;
	private final int cellHeight = 26;

	@Inject
	public PageView(ListDataProvider<AppEntity> dataProvider) {
		descriptionTable = new DescriptionTable(dataProvider);
		initWidget(docker);
		docker.add(resizeLayoutPanel);
		resizeLayoutPanel.setSize("100%", "100%");
		resizeLayoutPanel.add(descriptionTable);
		resizeLayoutPanel.addResizeHandler(this);
	}

	@Override
	public void onResize(ResizeEvent event) {
		int offsetHeight = descriptionTable.getOffsetHeight();
		descriptionTable.setPageSize(offsetHeight / cellHeight);
		getUiHandlers().update(0, descriptionTable.getPageSize(), null);
	}

	@Override
	public void setData(List<AppEntity> records) {
		descriptionTable.setData(records);
	}

	@Override
	public int getPageSize() {
		return descriptionTable.getPageSize();
	}

	@Override
	public void upDateDatabase(AppEntity entity) {
		// TODO Auto-generated method stub
		
	}

	private class DescriptionTable extends CellTable<AppEntity> {
		private final ListDataProvider<AppEntity> dataProvider;
		private final NoSelectionModel<AppEntity> selectionModel = new NoSelectionModel<AppEntity>();
		
		public DescriptionTable(ListDataProvider<AppEntity> dataProvider) {
			super(10, TableResources.resources);
			DescriptionTextColumn ident_Column = new DescriptionTextColumn() {
				@Override
				public String getValue(AppEntity object) {
					return Integer.toString(object.getId());
				}
			};
			addColumn(ident_Column, "编号");
			setColumnWidth(ident_Column, 60, Unit.PX);

			DescriptionTextColumn phone_Column = new DescriptionTextColumn() {
				@Override
				public String getValue(AppEntity object) {
					return object.getName();
				}
			};
			addColumn(phone_Column, "名称");
			setColumnWidth(phone_Column, 150, Unit.PX);

			DescriptionTextColumn notes_Column = new DescriptionTextColumn() {
				@Override
				public String getValue(AppEntity object) {
					return object.getNotes();
				}
			};
			addColumn(notes_Column, "备注");
			setColumnWidth(notes_Column, 300, Unit.PX);

			this.dataProvider = dataProvider;
			this.dataProvider.addDataDisplay(this);
			setSize("100%", "100%");
			setTableLayoutFixed(true);

			selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					Editor(selectionModel.getLastSelectedObject());
				}
			});
			setSelectionModel(selectionModel);
			
			setRowStyles(new RowStyle());
		}
		
		
		public void setData(List<AppEntity> records) {
			int filled = getPageSize() - records.size();
			for (int looper = 0; looper < filled; looper++)
				records.add(null);

			dataProvider.getList().clear();
			dataProvider.getList().addAll(records);
			dataProvider.refresh();
		}

		public void Editor(AppEntity entity) {
			getUiHandlers().editor(entity);
		}
		
		private abstract class DescriptionTextColumn extends TextColumn<AppEntity> {
			public void render(Context context, AppEntity object, SafeHtmlBuilder sb) {
				if(object == null)
					sb.appendHtmlConstant("&nbsp;");
				else
					super.render(context, object, sb);
			}
		}
		
		private class RowStyle implements RowStyles<AppEntity> {
			@Override
			public String getStyleNames(AppEntity object, int rowIndex) {
				return null;
			}
		}
	}
}
