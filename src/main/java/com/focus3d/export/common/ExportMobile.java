package com.focus3d.export.common;

import java.util.List;

import com.focus3d.export.model.EmailModel;
import com.focus3d.export.model.MobileModel;

public class ExportMobile extends AbstractExport<MobileModel> {

	public ExportMobile(String sheetName, String[] clomunName, String exportToDir, List<MobileModel> data) {
		super(sheetName, clomunName, exportToDir, data);
	}

}
