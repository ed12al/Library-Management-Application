package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO {
	public Connection conn = null;

	public BaseDAO(Connection conn){
		this.conn = conn;
	}
	
	public void save(String query, Object[] vals) throws SQLException{
		try(PreparedStatement pstmt = conn.prepareStatement(query)){
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			pstmt.executeUpdate();
		}
	}
	
	public Integer saveWithID(String query, Object[] vals) throws SQLException{
		Integer ID = null;
		try(PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				ID = rs.getInt(1);
			}
			return ID;
		}
	}
	
	public Integer getCount(String query, Object[] vals) throws SQLException{
		try( PreparedStatement pstmt = conn.prepareStatement(query)){
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}else{
				return null;
			}
		} 
	}
	
	public <T> List<T> readAllWithPageNo(String query, Object[] vals, Integer pageNo, Integer pageSize) throws SQLException{		
		int limit = (pageNo -1) * pageSize;
		query = query+" LIMIT "+limit+" , "+pageSize;
		try (PreparedStatement pstmt = conn.prepareStatement(query)){
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractData(rs);
		}
	}
	
	public <T>List<T> readAll(String query, Object[] vals) throws SQLException{
		try (PreparedStatement pstmt = conn.prepareStatement(query)){
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractData(rs);
		}
	}

	public abstract <T>List<T> extractData(ResultSet rs);
	
	public <T>List<T> readAllFirstLevel(String query, Object[] vals) throws SQLException{
		try (PreparedStatement pstmt = conn.prepareStatement(query)){
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractDataFirstLevel(rs);
		}
	}
	
	public <T>List<T> readAllFirstLevelWithPageNo(String query, Object[] vals, Integer pageNo, Integer pageSize) throws SQLException{
		int limit = (pageNo -1) * pageSize;
		query = query+" LIMIT "+limit+" , "+pageSize;
		try (PreparedStatement pstmt = conn.prepareStatement(query)){
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractDataFirstLevel(rs);
		} 
	}

	public abstract <T>List<T> extractDataFirstLevel(ResultSet rs);

}
