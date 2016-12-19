package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gcit.lms.entity.Loan;
import com.gcit.lms.common.Contants;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

public class LoanDAO extends BaseDAO{
	
	public LoanDAO(Connection conn) {
		super(conn);
	}
	
	public void addLoan(Loan loan) throws SQLException {
		Date today = new Date();
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?, ?, ?, ?, ?)", 
				new Object[] { loan.getBook().getBookId(), loan.getBranch().getBranchId(), loan.getBorrower().getCardNo(), new java.sql.Date(today.getTime()), new java.sql.Date(today.getTime()+Contants.ONE_WEEK) });
	}
	
	public Integer addLoanWithID(Loan loan) throws SQLException {
		Date today = new Date();
		return saveWithID("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?, ?, ?, ?, ?)", 
				new Object[] { loan.getBook().getBookId(), loan.getBranch().getBranchId(), loan.getBorrower().getCardNo(), new java.sql.Date(today.getTime()), new java.sql.Date(today.getTime()+Contants.ONE_WEEK) });
	}

	public void updateDueDate(Loan loan) throws SQLException {
		save("update tbl_book_loans set dueDate = ? where loanId = ?",
				new Object[] { new java.sql.Date(loan.getDueDate().getTime()), loan.getLoanId() });
	}

	public void deleteLoan(Loan loan) throws SQLException {
		save("delete from tbl_book_loans where loanId = ?", 
				new Object[] { loan.getLoanId() });
	}

	public List<Loan> readAllLoans() throws SQLException {
		return readAll("select * from tbl_book_loans", null);
	}
	
	public List<Loan> readAllLoansWithPageNo(Integer pageNo, Integer pageSize) throws SQLException {
		return readAllWithPageNo("select * from tbl_book_loans", null, pageNo, pageSize);
	}
	
	public Integer getLoansCount() throws SQLException{
		return getCount("select count(*) AS COUNT from tbl_book_loans", null);
	}
	
	public Loan readLoanById(Loan loan) throws SQLException{
		List<Loan> loans =  readAll(
				"select * from tbl_book_loans where loanId = ?", 
				new Object[]{loan.getLoanId()});
		if(loans!=null && loans.size()!=0){
			return loans.get(0);
		}
		return null;
	}
	
	public Loan readLoanFirstLevelById(Loan loan) throws SQLException{
		List<Loan> loans =  readAllFirstLevel(
				"select * from tbl_book_loans where loanId = ?", 
				new Object[]{loan.getLoanId()});
		if(loans!=null && loans.size()!=0){
			return loans.get(0);
		}
		return null;
	}
	
	public List<Loan> readAllLoansFirstLevelByBorrower(Borrower borrower) throws SQLException{
		return readAllFirstLevel("select * from tbl_book_loans where cardNo = ?",
				new Object[] { borrower.getCardNo()});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Loan> extractData(ResultSet rs) {
		List<Loan> loans = new ArrayList<Loan>();
		BookDAO bookDao = new BookDAO(conn);
		BranchDAO branchDao = new BranchDAO(conn);
		BorrowerDAO borrowerDao = new BorrowerDAO(conn);
		try {
			while (rs.next()) {
				Loan l = new Loan();
				l.setLoanId(rs.getInt("loanId"));
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				l.setBook(bookDao.readBookFirstLevelById(book));
				Branch branch = new Branch();
				branch.setBranchId(rs.getInt("branchId"));
				l.setBranch(branchDao.readBranchFirstLevelById(branch));
				Borrower borrower = new Borrower();
				borrower.setCardNo(rs.getInt("cardNo"));
				l.setBorrower(borrowerDao.readBorrowerFirstLevelById(borrower));
				l.setDateOut(rs.getDate("dateOut"));
				l.setDateIn(rs.getDate("dateIn"));
				l.setDueDate(rs.getDate("dueDate"));
				loans.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loans;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Loan> extractDataFirstLevel(ResultSet rs) {
		List<Loan> loans = new ArrayList<Loan>();
		try {
			while (rs.next()) {
				Loan l = new Loan();
				l.setLoanId(rs.getInt("loanId"));
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				l.setBook(book);
				Branch branch = new Branch();
				branch.setBranchId(rs.getInt("branchId"));
				l.setBranch(branch);
				Borrower borrower = new Borrower();
				borrower.setCardNo(rs.getInt("cardNo"));
				l.setBorrower(borrower);
				l.setDateOut(rs.getDate("dateOut"));
				l.setDateIn(rs.getDate("dateIn"));
				l.setDueDate(rs.getDate("dueDate"));
				loans.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loans;
	}

}
