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
		try (Connection conn = ConnectionUtil.getNewConnection()){
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
		try (Connection conn = ConnectionUtil.getNewConnection()){
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
		try (Connection conn = ConnectionUtil.getNewConnection()){
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
	
	public void addAuthor(Author author) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				authorDao.addAuthor(author);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	public void editAuthor(Author author) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				authorDao.updateAuthor(author);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	public void deleteAuthor(Author author) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				authorDao.deleteAuthor(author);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.readAllBooks();
			} catch (SQLException e){
				throw e;
			}
		} 
	}

	public Author readAuthorById(Author author) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				return authorDao.readAuthorById(author);
			} catch (SQLException e){
				throw e;
			}
		} 
	}
	
	public List<Author> readAllAuthors() throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				return authorDao.readAllAuthors();
			} catch (SQLException e){
				throw e;
			}
		} 
	}	
	
	public List<Author> readAllAuthorsWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				return authorDao.readAllAuthorsWithPageNo(pageNo, pageSize, q);
			} catch (SQLException e){
				throw e;
			}
		} 
	}

	public Integer getAuthorsCount(String q) throws ClassNotFoundException, SQLException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				return authorDao.getAuthorsCount(q);
			} catch (SQLException e){
				throw e;
			}
		}
	}
}
