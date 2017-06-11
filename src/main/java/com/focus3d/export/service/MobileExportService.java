package com.focus3d.export.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focus3d.export.dao.MobileDao;
import com.focus3d.export.model.EmailModel;
import com.focus3d.export.model.MobileModel;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class MobileExportService {
	private static final Logger log = LoggerFactory.getLogger(MobileExportService.class);
	private MobileDao mobileDao;
	
	public List<MobileModel> list(int start, int end){
		List<MobileModel> list = mobileDao.list(start, end);
		log.debug("list size:" + list.size());
		return list;
	}
	

	public MobileDao getMobileDao() {
		return mobileDao;
	}

	public void setMobileDao(MobileDao mobileDao) {
		this.mobileDao = mobileDao;
	}
}
