package com.focus3d.export.common;

import java.util.List;

import com.focus3d.export.model.EmailModel;

public class ExportEmail extends AbstractExport<EmailModel> {

	public ExportEmail(String sheetName, String[] clomunName, String exportToDir, List<EmailModel> data) {
		super(sheetName, clomunName, exportToDir, data);
	}

}
