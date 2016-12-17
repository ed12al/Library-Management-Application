package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Book;

public class BookCopyDAO extends BaseDAO{

	public BookCopyDAO(Connection conn) {
		super(conn);
	}
	
	public void addBookCopy(BookCopy bookCopy) throws SQLException {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)", 
				new Object[] { bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId(), bookCopy.getNoOfCopies()});
	}
	
	public void updateNoOfCopies(BookCopy bookCopy) throws SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bookCopy.getNoOfCopies(), bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId()});
	}

	public void deleteBookCopy(BookCopy bookCopy) throws SQLException {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?", 
				new Object[] { bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId() });
	}

	public List<BookCopy> readAllBookCopys() throws SQLException {
		return readAll("select * from tbl_book_copies", null);
	}
	
	public List<BookCopy> readAllBookCopysWithPageNo(Integer pageNo, Integer pageSize) throws SQLException {
		return readAllWithPageNo("select * from tbl_book_copies", null, pageNo, pageSize);
	}
	
	public Integer getBookCopysCount() throws SQLException{
		return getCount("select count(*) AS COUNT from tbl_book_copies", null);
	}
	
	public BookCopy readBookCopyByIds(BookCopy bookCopy) throws SQLException{
		List<BookCopy> bookCopys =  readAll(
				"select * from tbl_book_copies where bookId = ? and branchId = ?", 
				new Object[]{bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId()});
		if(bookCopys!=null){
			return bookCopys.get(0);
		}
		return null;
	}
	
	public BookCopy readBookCopyFirstLevelById(BookCopy bookCopy) throws SQLException{
		List<BookCopy> bookCopys =  readAllFirstLevel(
				"select * from tbl_book_copies where bookId = ? and branchId = ?", 
				new Object[]{bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId()});
		if(bookCopys!=null){
			return bookCopys.get(0);
		}
		return null;
	}
	
	public List<BookCopy> readAllBookCopiesFirstLevelByBook(Book book) throws SQLException{
		return readAllFirstLevel("select * from tbl_book_copies where bookId = ?)",
				new Object[] { book.getBookId()});
	}
	
	public List<BookCopy> readAllBookCopiesFirstLevelByBranch(Branch branch) throws SQLException{
		return readAllFirstLevel("select * from tbl_book_copies where branchId = ?",
				new Object[] { branch.getBranchId()});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BookCopy> extractData(ResultSet rs) {
		List<BookCopy> bookCopies = new ArrayList<BookCopy>();
		BookDAO bookDao = new BookDAO(conn);
		BranchDAO branchDao = new BranchDAO(conn);
		try {
			while (rs.next()) {
				BookCopy b = new BookCopy();
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				b.setBook(bookDao.readBookFirstLevelById(book));
				Branch branch = new Branch();
				branch.setBranchId(rs.getInt("branchId"));
				b.setBranch(branchDao.readBranchFirstLevelById(branch));
				b.setNoOfCopies(rs.getInt("noOfCopies"));
				bookCopies.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookCopies;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookCopy> extractDataFirstLevel(ResultSet rs) {
		List<BookCopy> bookCopys = new ArrayList<BookCopy>();
		try {
			while (rs.next()) {
				BookCopy b = new BookCopy();
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				b.setBook(book);
				Branch branch = new Branch();
				branch.setBranchId(rs.getInt("branchId"));
				b.setBranch(branch);
				b.setNoOfCopies(rs.getInt("noOfCopies"));
				bookCopys.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookCopys;
	}

}
