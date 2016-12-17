package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class AdminService {
	public void addAuthor(Author author) throws SQLException{
		Connection conn =  null;
		try {
			conn = ConnectionUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.addAuthor(author);
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			if(conn != null) conn.rollback();
		} finally{
			if(conn != null) conn.close();
		}
	}
	
	public void addBook(Book book) throws SQLException{
		
	}
	
}
