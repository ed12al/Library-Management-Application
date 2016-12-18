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
		save("insert into tbl_author (authorName) values (?)", 
				new Object[] { author.getAuthorName() });
	}
	
	public Integer addAuthorWithID(Author author) throws SQLException {
		return saveWithID("insert into tbl_author (authorName) values (?)", 
				new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Author author) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws SQLException {
		save("delete from tbl_author where authorId = ?", 
				new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws SQLException {
		return readAll("select * from tbl_author", null);
	}
	
	public List<Author> readAllAuthorsWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException {
		if(q==null||q.trim().length()==0){
			return readAllWithPageNo("select * from tbl_author", null, pageNo, pageSize);
		}else{
			q = "%"+q+"%";
			return readAllWithPageNo("select * from tbl_author where authorName like ?", new Object[]{q}, pageNo, pageSize);
		}
	}
	
	public Integer getAuthorsCount(String q) throws SQLException{
		if(q==null||q.trim().length()==0){
			return getCount("select count(*) AS COUNT from tbl_author", null);
		}else{
			q = "%"+q+"%";
			return getCount("select count(*) AS COUNT from tbl_author where authorName like ?", new Object[]{q});
		}
	}
	
	public Author readAuthorById(Author author) throws SQLException{
		List<Author> authors =  readAll(
				"select * from tbl_author where authorId = ?", 
				new Object[]{author.getAuthorId()});
		if(authors!=null){
			return authors.get(0);
		}
		return null;
	}
	
	public Author readAuthorFirstLevelById(Author author) throws SQLException{
		List<Author> authors =  readAllFirstLevel(
				"select * from tbl_author where authorId = ?", 
				new Object[]{author.getAuthorId()});
		if(authors!=null){
			return authors.get(0);
		}
		return null;
	}
	
	public List<Author> readAllAuthorsFirstLevelByBook(Book book) throws SQLException{
		return readAllFirstLevel("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?))",
				new Object[] { book.getBookId()});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Author> extractData(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();
		BookDAO bdao = new BookDAO(conn);
		try {
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				a.setBooks(bdao.readAllBooksFirstLevelByAuthor(a));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
		
	}

	@SuppressWarnings("unchecked")
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
