package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO{

	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	public void addGenre(Genre genre) throws SQLException {
		save("insert into tbl_genre (genreName) values (?)", 
				new Object[] { genre.getGenreName() });
	}
	
	public Integer addGenreWithID(Genre genre) throws SQLException {
		return saveWithID("insert into tbl_genre (genreName) values (?)", 
				new Object[] { genre.getGenreName() });
	}

	public void updateGenre(Genre genre) throws SQLException {
		save("update tbl_genre set genreName = ? where genreId = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws SQLException {
		save("delete from tbl_genre where genreId = ?", 
				new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAllGenres() throws SQLException {
		return readAll("select * from tbl_genre", null);
	}
	
	public List<Genre> readAllGenresWithPageNo(Integer pageNo, Integer pageSize) throws SQLException {
		return readAllWithPageNo("select * from tbl_genre", null, pageNo, pageSize);
	}
	
	public Integer getGenresCount() throws SQLException{
		return getCount("select count(*) AS COUNT from tbl_genre", null);
	}
	
	public Genre readGenreById(Genre genre) throws SQLException{
		List<Genre> genres =  readAll(
				"select * from tbl_genre where genreId = ?", 
				new Object[]{genre.getGenreId()});
		if(genres!=null && genres.size()!=0){
			return genres.get(0);
		}
		return null;
	}
	
	public Genre readGenreFirstLevelById(Genre genre) throws SQLException{
		List<Genre> genres =  readAllFirstLevel(
				"select * from tbl_genre where genreId = ?", 
				new Object[]{genre.getGenreId()});
		if(genres!=null && genres.size()!=0){
			return genres.get(0);
		}
		return null;
	}
	
	public List<Genre> readAllGenresFirstLevelByBook(Book book) throws SQLException {
		return readAllFirstLevel("select * from tbl_genre where genreId IN (select genreId from tbl_book_genres where bookId = ?)", 
				new Object[] { book.getBookId() });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Genre> extractData(ResultSet rs) {
		List<Genre> genres = new ArrayList<Genre>();
		BookDAO bdao = new BookDAO(conn);
		try {
			while (rs.next()) {
				Genre g = new Genre();
				g.setGenreId(rs.getInt("genreId"));
				g.setGenreName(rs.getString("genreName"));
				g.setBooks(bdao.readAllBooksFirstLevelByGenre(g));
				genres.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genres;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) {
		List<Genre> genres = new ArrayList<Genre>();
		try {
			while (rs.next()) {
				Genre g = new Genre();
				g.setGenreId(rs.getInt("genreId"));
				g.setGenreName(rs.getString("genreName"));
				genres.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genres;
	}

}
