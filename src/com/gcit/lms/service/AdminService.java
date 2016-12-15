package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;

public class AdminService {
	public void addAuthor(Author author) throws SQLException{
		Connection conn =  ConnectionUtil.getConnection();
		try {
			AuthorDAO adao = new AuthorDAO(conn);
			adao.addAuthor(author);
			//author.setAuthorID(authorID);
			//adao.addBookAuthor(author);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
}
