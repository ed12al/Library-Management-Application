package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	
	public void addBorrower(Borrower borrower) throws SQLException {
		save("insert into tbl_borrower (name, address, phone) values (?, ?, ?)", 
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}
	
	public Integer addBorrowerWithID(Borrower borrower) throws SQLException {
		return saveWithID("insert into tbl_borrower (name, address, phone) values (?, ?, ?)", 
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrower(Borrower borrower) throws SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws SQLException {
		save("delete from tbl_borrower where cardNo = ?", 
				new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readAllBorrowers() throws SQLException {
		return readAll("select * from tbl_borrower", null);
	}
	
	public List<Borrower> readAllBorrowersWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException {
		if(q==null||q.trim().length()==0){
			return readAllWithPageNo("select * from tbl_borrower", null, pageNo, pageSize);
		}else{
			q = "%"+q+"%";
			return readAllWithPageNo("select * from tbl_borrower where name like ?", new Object[]{q}, pageNo, pageSize);
		}
	}
	
	public Integer getBorrowersCount(String q) throws SQLException{
		if(q==null||q.trim().length()==0){
			return getCount("select count(*) AS COUNT from tbl_borrower", null);
		}else{
			q = "%"+q+"%";
			return getCount("select count(*) AS COUNT from tbl_borrower where name like ?", new Object[]{q});
		}
	}
	
	public Borrower readBorrowerById(Borrower borrower) throws SQLException{
		List<Borrower> borrowers =  readAll(
				"select * from tbl_borrower where cardNo = ?", 
				new Object[]{borrower.getCardNo()});
		if(borrowers!=null && borrowers.size()!=0){
			return borrowers.get(0);
		}
		return null;
	}
	
	public Borrower readBorrowerFirstLevelById(Borrower borrower) throws SQLException{
		List<Borrower> borrowers =  readAllFirstLevel(
				"select * from tbl_borrower where cardNo = ?", 
				new Object[]{borrower.getCardNo()});
		if(borrowers!=null && borrowers.size()!=0){
			return borrowers.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Borrower> extractData(ResultSet rs) {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		LoanDAO ldao = new LoanDAO(conn);
		try {
			while (rs.next()) {
				Borrower b = new Borrower();
				b.setCardNo(rs.getInt("cardNo"));
				b.setName(rs.getString("name"));
				b.setAddress(rs.getString("address"));
				b.setPhone(rs.getString("phone"));
				b.setLoans(ldao.readAllLoansFirstLevelByBorrower(b));
				borrowers.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowers;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		try {
			while (rs.next()) {
				Borrower b = new Borrower();
				b.setCardNo(rs.getInt("cardNo"));
				b.setName(rs.getString("name"));
				b.setAddress(rs.getString("address"));
				b.setPhone(rs.getString("phone"));
				borrowers.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowers;
	}

}
