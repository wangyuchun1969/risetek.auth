package com.risetek.auth.client.application.selfAccount;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;

public class TableResources {
	public static CSSResources resources = GWT.create(CSSResources.class);

	public interface CSSResources extends CellTable.Resources {
		@Source({ CellTable.Style.DEFAULT_CSS, "Table.css" })
		Style cellTableStyle();
		interface Style extends CellTable.Style {
			public String onlineStatus();
			public String warningStatus();
			public String offlineStatus();
		}
	}
}
