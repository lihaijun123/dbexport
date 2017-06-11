package com.focus3d.export.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.focus3d.export.common.ExportMobile;
import com.focus3d.export.model.MobileModel;
import com.focus3d.export.service.MobileExportService;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class ExportMobileMain {
	private static final Logger log = LoggerFactory.getLogger(ExportMobileMain.class);
	private static final String[] clomus = new String[]{"���", "����", "url", "�ֻ�", "ץȡʱ��"};
	private static final String sheetName = "�ֻ�";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("/com/focus3d/spring/datasource.xml");
		MobileExportService mobileExportService = beanFactory.getBean(MobileExportService.class.getName(), MobileExportService.class);
		long st = System.currentTimeMillis();
		List<MobileModel> list = mobileExportService.list(1, 30);
		ExportMobile exportMobile = new ExportMobile(sheetName, clomus, "F:\\", list);
		exportMobile.export();
		log.info("������ɡ�");
		long et = System.currentTimeMillis();
		log.info("��ʱ��" + ((et - st) / 1000) + "��");
	}

}
