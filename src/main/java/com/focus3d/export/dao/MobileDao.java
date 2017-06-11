package com.focus3d.export.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.focus3d.export.model.MobileModel;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class MobileDao extends AbstractDao{
	
	private static final String TABLE_NAME = "spider_mobile";
	
	public List<MobileModel> list(int start, int end) {
		if(end > start){
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				connection = getConnection();
				preparedStatement = connection.prepareStatement("select * from " + TABLE_NAME + " where 1=1 limit ?,?");
				preparedStatement.setInt(1, start > 0 ? (-- start) : 0);
				preparedStatement.setInt(2, end);
				resultSet = preparedStatement.executeQuery();
				List<MobileModel> list = new ArrayList<MobileModel>();
				while(resultSet.next()){
					int sn = resultSet.getInt(1);
					String title = resultSet.getString(2);
					String url = resultSet.getString(3);
					String email = resultSet.getString(4);
					Date addTime = resultSet.getTimestamp(5);
					MobileModel mobileModel = new MobileModel();
					mobileModel.setSn(sn);
					mobileModel.setTitle(title);
					mobileModel.setUrl(url);
					mobileModel.setMobile(email);
					mobileModel.setAddTime(addTime);
					list.add(mobileModel);
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		throw new RuntimeException("end must great to start");
	}
	
	
}
