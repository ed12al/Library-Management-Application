package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;

public class BookDAO extends BaseDAO{
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws SQLException{
		save("insert into tbl_book (title, pubId) values (?, ?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	public void updateBook(Book book) throws SQLException{
		save("update tbl_book set title = ? where bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws SQLException{
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readAllBooks() throws SQLException{
		return read("select * from tbl_book", null);
	}

	@Override
	public List<Book> extractData(ResultSet rs) {
		AuthorDAO adao = new AuthorDAO(conn);
		List<Book> books = new ArrayList<Book>();
		try {
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				b.setAuthors(adao.readAllFirstLevel(
						"select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?))",
						new Object[] { b.getBookId()}));
				//add genres & publisher
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) {
		List<Book> books = new ArrayList<Book>();
		try {
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	
}
