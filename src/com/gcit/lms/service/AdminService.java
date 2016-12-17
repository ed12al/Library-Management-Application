package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class AdminService {
	/*  template
	// write
	public void write(Object o) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getConnection()){
			try {
				//
				//
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	// read
	public List<Book> read() throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.readAllBooks();
			} catch (SQLException e){
				throw e;
			}
		} 
	}
	*/
	
	public void addBook(Book book) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				bookDao.addBookWithDetails(book);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.readAllBooks();
			} catch (SQLException e){
				throw e;
			}
		} 
	}
	
	public List<Author> readAllAuthors() throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				return authorDao.readAllAuthors();
			} catch (SQLException e){
				throw e;
			}
		} 
	}	
	
	
}
