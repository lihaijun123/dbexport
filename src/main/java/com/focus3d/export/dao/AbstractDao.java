package com.focus3d.export.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 * *
 * @author lihaijun
 *
 */
public class AbstractDao {
	protected DataSource dataSource;
	/**
	 * 
	 * *
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	/**
	 * 
	 * *
	 * @param connection
	 * @param preparedStatement
	 * @param resultSet
	 */
	protected void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
		closeConnection(resultSet);
		closeConnection(preparedStatement);
		closeConnection(connection);
	}
	/**
	 * 
	 * *
	 * @param connection
	 */
	private void closeConnection(Connection connection){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * *
	 * @param preparedStatement
	 */
	private void closeConnection(PreparedStatement preparedStatement){
		if(preparedStatement != null){
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * *
	 * @param resultSet
	 */
	private void closeConnection(ResultSet resultSet){
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
