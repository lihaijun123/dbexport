package com.focus3d.export.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focus3d.export.dao.EmailDao;
import com.focus3d.export.model.EmailModel;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class EmailExportService {
	private static final Logger log = LoggerFactory.getLogger(EmailExportService.class);
	private EmailDao emailDao;
	/**
	 * *
	 * @param start
	 * @param end
	 * @return
	 */
	public List<EmailModel> list(int start, int end){
		List<EmailModel> list = emailDao.list(start, end);
		log.debug("list size:" + list.size());
		return list;
	}
	
	public EmailDao getEmailDao() {
		return emailDao;
	}

	public void setEmailDao(EmailDao emailDao) {
		this.emailDao = emailDao;
	}
	
}
