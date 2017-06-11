package com.focus3d.export.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.focus3d.export.common.ExportEmail;
import com.focus3d.export.model.EmailModel;
import com.focus3d.export.service.EmailExportService;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class ExportEmailMain {
	private static final Logger log = LoggerFactory.getLogger(ExportEmailMain.class);
	private static final String[] clomus = new String[]{"���", "����", "url", "����", "ץȡʱ��"};
	private static final String sheetName = "����";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("/com/focus3d/spring/datasource.xml");
		EmailExportService emailExportService = beanFactory.getBean(EmailExportService.class.getName(), EmailExportService.class);
		long st = System.currentTimeMillis();
		List<EmailModel> list = emailExportService.list(1, 30);
		ExportEmail exportToExcel = new ExportEmail(sheetName, clomus, "F:\\", list);
		exportToExcel.export();
		log.info("������ɡ�");
		long et = System.currentTimeMillis();
		log.info("��ʱ��" + ((et - st) / 1000) + "��");
	}

}
