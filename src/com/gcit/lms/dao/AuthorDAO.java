package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class AuthorDAO extends BaseDAO{

	public AuthorDAO(Connection conn) {
		super(conn);
	}
	
	public void addAuthor(Author author) throws SQLException {
		save("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}
	
	public Integer addAuthorWithID(Author author) throws SQLException {
		return saveWithID("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}
	
	public void addBookAuthor(Author author) throws SQLException {
		for(Book b: author.getBooks()){
			save("insert into tbl_book_authors (bookId, authorId) values (?, ?)", new Object[] { author.getAuthorId(), b.getBookId() });
		}
	}

	public void updateAuthor(Author author) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws SQLException {
		return read("select * from tbl_author", null);
	}
	
	public List<Author> extractData(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();
		try {
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				//a.setBooks(books);
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
		
	}

	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();
		try {
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

}
