package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class AdminService {

/* * * * * * * * * * * * * * * * * * admin author * * * * * * * * * * * * * * * * * * */
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
	
	public Integer addAuthorWithId(Author author) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				Integer ID = authorDao.addAuthorWithID(author);
				conn.commit();
				return ID;
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

	public List<Author> readAllAuthorsFirstLevel() throws SQLException, ClassNotFoundException{
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				AuthorDAO authorDao = new AuthorDAO(conn);
				return authorDao.readAllAuthorsFirstLevel();
			} catch (SQLException e) {
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
	
/* * * * * * * * * * * * * * * * * * admin publisher * * * * * * * * * * * * * * * * * * */
	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				publisherDao.addPublisher(publisher);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	public Integer addPublisherWithId(Publisher publisher) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				Integer ID = publisherDao.addPublisherWithID(publisher);
				conn.commit();
				return ID;
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}

	public void editPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				publisherDao.updatePublisher(publisher);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public void deletePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				publisherDao.deletePublisher(publisher);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public Publisher readPublisherById(Publisher publisher) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				return publisherDao.readPublisherById(publisher);
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public List<Publisher> readAllPublishersWithPageNo(Integer pageNo, Integer pageSize, String q)
			throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				return publisherDao.readAllPublishersWithPageNo(pageNo, pageSize, q);
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
	public List<Publisher> readAllPublishersFirstLevel() throws SQLException, ClassNotFoundException{
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				return publisherDao.readAllPublishersFirstLevel();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public Integer getPublishersCount(String q) throws ClassNotFoundException, SQLException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				PublisherDAO publisherDao = new PublisherDAO(conn);
				return publisherDao.getPublishersCount(q);
			} catch (SQLException e) {
				throw e;
			}
		}
	}
/* * * * * * * * * * * * * * * * * * admin branch * * * * * * * * * * * * * * * * * * */
	public void addBranch(Branch branch) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BranchDAO branchDao = new BranchDAO(conn);
				branchDao.addBranch(branch);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}

	public void editBranch(Branch branch) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BranchDAO branchDao = new BranchDAO(conn);
				branchDao.updateBranch(branch);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public void deleteBranch(Branch branch) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BranchDAO branchDao = new BranchDAO(conn);
				branchDao.deleteBranch(branch);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public Branch readBranchById(Branch branch) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BranchDAO branchDao = new BranchDAO(conn);
				return branchDao.readBranchById(branch);
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public List<Branch> readAllBranchesWithPageNo(Integer pageNo, Integer pageSize, String q)
			throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BranchDAO branchDao = new BranchDAO(conn);
				return branchDao.readAllBranchesWithPageNo(pageNo, pageSize, q);
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public Integer getBranchesCount(String q) throws ClassNotFoundException, SQLException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BranchDAO branchDao = new BranchDAO(conn);
				return branchDao.getBranchesCount(q);
			} catch (SQLException e) {
				throw e;
			}
		}
	}
/* * * * * * * * * * * * * * * * * * admin borrower * * * * * * * * * * * * * * * * * * */
	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BorrowerDAO borrowerDao = new BorrowerDAO(conn);
				borrowerDao.addBorrower(borrower);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}

	public void editBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BorrowerDAO borrowerDao = new BorrowerDAO(conn);
				borrowerDao.updateBorrower(borrower);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public void deleteBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BorrowerDAO borrowerDao = new BorrowerDAO(conn);
				borrowerDao.deleteBorrower(borrower);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public Borrower readBorrowerById(Borrower borrower) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BorrowerDAO borrowerDao = new BorrowerDAO(conn);
				return borrowerDao.readBorrowerById(borrower);
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public List<Borrower> readAllBorrowersWithPageNo(Integer pageNo, Integer pageSize, String q)
			throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BorrowerDAO borrowerDao = new BorrowerDAO(conn);
				return borrowerDao.readAllBorrowersWithPageNo(pageNo, pageSize, q);
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public Integer getBorrowersCount(String q) throws ClassNotFoundException, SQLException {
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				BorrowerDAO borrowerDao = new BorrowerDAO(conn);
				return borrowerDao.getBorrowersCount(q);
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
/* * * * * * * * * * * * * * * * * * admin book * * * * * * * * * * * * * * * * * * */
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
	
	public void editBook(Book book) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				bookDao.updateBookWithDetails(book);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
	
	public void deleteBook(Book book) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				bookDao.deleteBook(book);
				conn.commit();
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}

	public Book readBookById(Book book) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.readBookById(book);
			} catch (SQLException e){
				throw e;
			}
		} 
	}
	
	public List<Book> readAllBooksWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.readAllBooksWithPageNo(pageNo, pageSize, q);
			} catch (SQLException e){
				throw e;
			}
		} 
	}

	public Integer getBooksCount(String q) throws ClassNotFoundException, SQLException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.getBooksCount(q);
			} catch (SQLException e){
				throw e;
			}
		}
	}
/* * * * * * * * * * * * * * * * * * admin genre * * * * * * * * * * * * * * * * * * */
	public List<Genre> readAllGenresFirstLevel() throws SQLException, ClassNotFoundException{
		try (Connection conn = ConnectionUtil.getNewConnection()) {
			try {
				GenreDAO genreDao = new GenreDAO(conn);
				return genreDao.readAllGenresFirstLevel();
			} catch (SQLException e) {
				throw e;
			}
		}
	}	
	public Integer addGenreWithId(Genre genre) throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				GenreDAO genreDao = new GenreDAO(conn);
				Integer ID = genreDao.addGenreWithID(genre);
				conn.commit();
				return ID;
			} catch (SQLException e){
				conn.rollback();
				throw e;
			}
		} 
	}
}
