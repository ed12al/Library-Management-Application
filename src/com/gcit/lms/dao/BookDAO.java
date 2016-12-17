package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class BookDAO extends BaseDAO{
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws SQLException{
		save("insert into tbl_book (title, pubId) values (?, ?)", 
				new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	public Integer addBookWithID(Book book) throws SQLException{
		return saveWithID("insert into tbl_book (title, pubId) values (?, ?)", 
				new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	public void addBookWithDetails(Book book) throws SQLException {
		Integer bookId = addBookWithID(book);
		if(bookId != null && bookId > 0){
			List<Author> authors = book.getAuthors();
			if(authors != null){
				for(Author author: authors){
					save("insert into tbl_book_authors (authorId, bookId) values (?, ?)", new Object[] { author.getAuthorId(), book.getBookId() });
				}
			}
		}
	}
	
	public void updateBook(Book book) throws SQLException{
		save("update tbl_book set title = ? where bookId = ?", 
				new Object[] {book.getTitle(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws SQLException{
		save("delete from tbl_book where bookId = ?", 
				new Object[] {book.getBookId()});
	}
	
	public List<Book> readAllBooks() throws SQLException{
		return readAll("select * from tbl_book", null);
	}
	
	public List<Book> readAllBooksWithPageNo(Integer pageNo, Integer pageSize) throws SQLException {
		return readAllWithPageNo("select * from tbl_book", null, pageNo, pageSize);
	}
	
	public Integer getBooksCount() throws SQLException{
		return getCount("select count(*) AS COUNT from tbl_book", null);
	}
	
	public Book readBookById(Book book) throws SQLException{
		List<Book> books =  readAll("select * from tbl_book where bookId = ?", 
				new Object[]{ book.getBookId() });
		if(books!=null){
			return books.get(0);
		}
		return null;
	}

	public Book readBookFirstLevelById(Book book) throws SQLException{
		List<Book> books =  readAllFirstLevel("select * from tbl_book where bookId = ?", 
				new Object[]{ book.getBookId() });
		if(books!=null){
			return books.get(0);
		}
		return null;
	}
	
	public List<Book> readAllBooksFirstLevelByAuthor(Author author) throws SQLException{
		return readAllFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_authors where authorId = ?)", 
				new Object[] {author.getAuthorId()});
	}
	
	public List<Book> readAllBooksFirstLevelByGenre(Genre genre) throws SQLException{
		return readAllFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_genres where genreId = ?)", 
				new Object[] {genre.getGenreId()});
	}	
	
	public List<Book> readAllBooksFirstLevelByPublisher(Publisher publisher) throws SQLException{
		return readAllFirstLevel("select * from tbl_book where bookId = ?", 
				new Object[] { publisher.getPublisherId() });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> extractData(ResultSet rs) {
		AuthorDAO adao = new AuthorDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		GenreDAO gdao = new GenreDAO(conn);
		List<Book> books = new ArrayList<Book>();
		try {
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				b.setAuthors(adao.readAllAuthorsFirstLevelByBook(b));
				Publisher publisher = new Publisher();
				publisher.setPublisherId(rs.getInt("pubId"));
				b.setPublisher(pdao.readPublisherFirstLevelById(publisher));
				b.setGenres(gdao.readAllGenresFirstLevelByBook(b));
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) {
		List<Book> books = new ArrayList<Book>();
		try {
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				Publisher publisher = new Publisher();
				publisher.setPublisherId(rs.getInt("pubId"));
				b.setPublisher(publisher);
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	
}
