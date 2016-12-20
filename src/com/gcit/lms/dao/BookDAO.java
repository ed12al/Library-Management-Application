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
		Integer pubId = book.getPublisher() == null ? null : book.getPublisher().getPublisherId();
		save("insert into tbl_book (title, pubId) values (?, ?)", 
				new Object[] {book.getTitle(), pubId});
	}
	
	public Integer addBookWithID(Book book) throws SQLException{
		Integer pubId = book.getPublisher() == null ? null : book.getPublisher().getPublisherId();
		return saveWithID("insert into tbl_book (title, pubId) values (?, ?)", 
				new Object[] {book.getTitle(), pubId});
	}
	
	public void addBookWithDetails(Book book) throws SQLException {
		Integer bookId = addBookWithID(book);
		book.setBookId(bookId);
		if(bookId != null && bookId > 0){
			addAllAuthorsByBook(book);
			addAllGenresByBook(book);
		}
	}
	
	public void updateBook(Book book) throws SQLException{
		Integer pubId = book.getPublisher() == null ? null : book.getPublisher().getPublisherId();
		save("update tbl_book set title = ?, pubId = ? where bookId = ?", 
				new Object[] {book.getTitle(), pubId, book.getBookId()});
	}
	
	public void updateBookWithDetails(Book book) throws SQLException {
		updateBook(book);
		deleteAllAuthorsByBook(book);
		addAllAuthorsByBook(book);
		deleteAllGenresByBook(book);
		addAllGenresByBook(book);
	}
	
	private void addAllAuthorsByBook(Book book) throws SQLException {
		if(book.getAuthors() != null){
			for(Author author: book.getAuthors()){
				save("insert into tbl_book_authors (authorId, bookId) values (?, ?)", new Object[] { author.getAuthorId(), book.getBookId() });
			}
		}
	}
	
	private void addAllGenresByBook(Book book) throws SQLException {
		if(book.getGenres() != null){
			for(Genre genre: book.getGenres()){
				save("insert into tbl_book_genres (genreId, bookId) values (?, ?)", new Object[] { genre.getGenreId(), book.getBookId() });
			}
		}
	}
	
	private void deleteAllAuthorsByBook(Book book) throws SQLException {
		save("delete from tbl_book_authors where bookId = ? ", new Object[]{ book.getBookId() });
	}
	
	private void deleteAllGenresByBook(Book book) throws SQLException {
		save("delete from tbl_book_genres where bookId = ? ", new Object[]{ book.getBookId() });
	}

	public void deleteBook(Book book) throws SQLException{
		save("delete from tbl_book where bookId = ?", 
				new Object[] {book.getBookId()});
	}
	
	public List<Book> readAllBooks() throws SQLException{
		return readAll("select * from tbl_book", null);
	}
	
	public List<Book> readAllBooksFirstLevel() throws SQLException{
		return readAllFirstLevel("select * from tbl_book", null);
	}
	
	public List<Book> readAllBooksWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException {
		if(q==null||q.trim().length()==0){
			return readAllWithPageNo("select * from tbl_book", null, pageNo, pageSize);
		}else{
			q = "%"+q+"%";
			return readAllWithPageNo("select * from tbl_book where title like ?", new Object[]{q}, pageNo, pageSize);
		}
	}
	
	public Integer getBooksCount(String q) throws SQLException{
		if(q==null||q.trim().length()==0){
			return getCount("select count(*) AS COUNT from tbl_book", null);
		}else{
			q = "%"+q+"%";
			return getCount("select count(*) AS COUNT from tbl_book where title like ?", new Object[]{q});
		}
	}
	
	public Book readBookById(Book book) throws SQLException{
		List<Book> books =  readAll("select * from tbl_book where bookId = ?", 
				new Object[]{ book.getBookId() });
		if(books!=null && books.size()!=0){
			return books.get(0);
		}
		return null;
	}

	public Book readBookFirstLevelById(Book book) throws SQLException{
		List<Book> books =  readAllFirstLevel("select * from tbl_book where bookId = ?", 
				new Object[]{ book.getBookId() });
		if(books!=null && books.size()!=0){
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
				publisher = pdao.readPublisherFirstLevelById(publisher);
				b.setPublisher(publisher);
				b.setGenres(gdao.readAllGenresFirstLevelByBook(b));
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e){
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
				Integer pubId = rs.getInt("pubId");
				if(pubId != 0) {
					Publisher publisher = new Publisher();
					publisher.setPublisherId(rs.getInt("pubId"));
					b.setPublisher(publisher);
				}
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	
}
