package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO {
public static Connection conn = null;
	
	public BaseDAO(Connection conn){
		this.conn = conn;
	}
	
	public void save(String query, Object[] vals) throws SQLException{
		PreparedStatement pstmt = null;	
		try {
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(pstmt!=null) pstmt.close();
		}
	}
	
	public Integer saveWithID(String query, Object[] vals) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}else{
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(pstmt!=null)	pstmt.close();
		}
		return null;
	}
	
	public <T> List<T> read(String query, Object[] vals) throws SQLException{		
		PreparedStatement pstmt = null;
		List<T> ts = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			ts = extractData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(pstmt!=null) pstmt.close();
		}
		return ts;
	}
	
	public <T>List<T> readAll(String query, Object[] vals) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(pstmt!=null) pstmt.close();
		}
		return null;
	}

	public abstract <T>List<T> extractData(ResultSet rs);
	
	public <T>List<T> readAllFirstLevel(String query, Object[] vals) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractDataFirstLevel(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(pstmt!=null) pstmt.close();
		}
		return null;
	}

	public abstract <T>List<T> extractDataFirstLevel(ResultSet rs);

}
