package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Publisher;

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
}
