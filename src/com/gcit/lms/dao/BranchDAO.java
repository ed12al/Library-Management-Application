package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.Branch;

public class BranchDAO extends BaseDAO{

	public BranchDAO(Connection conn) {
		super(conn);
	}
	
	public void addBranch(Branch branch) throws SQLException {
		save("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)", 
				new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}
	
	public Integer addBranchWithID(Branch branch) throws SQLException {
		return saveWithID("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)", 
				new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}
	
	public void addAllBookCopiesByBranch(Branch branch) throws SQLException {
		if(branch.getBookCopy() != null){
			for(BookCopy bookCopy : branch.getBookCopy()){
				save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)", new Object[] { bookCopy.getBook().getBookId(), branch.getBranchId(), bookCopy.getNoOfCopies() });
			}
		}
	}
	
	public void updateBranch(Branch branch) throws SQLException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}
	
	public void updateBookCopiesByBranch(Branch branch) throws SQLException {
		deleteAllBookCopiesByBranch(branch);
		addAllBookCopiesByBranch(branch);
	}
	
	public void deleteBranch(Branch branch) throws SQLException {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { branch.getBranchId() });
	}
	
	public void deleteAllBookCopiesByBranch(Branch branch) throws SQLException {
		save("delete from tbl_book_copies where branchId = ?", new Object[] { branch.getBranchId() });
	}
	
	public List<Branch> readAllBranches() throws SQLException {
		return readAll("select * from tbl_library_branch", null);
	}
	
	public List<Branch> readAllBranchesWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException {
		if(q==null||q.trim().length()==0){
			return readAllWithPageNo("select * from tbl_library_branch", null, pageNo, pageSize);
		}else{
			q = "%"+q+"%";
			return readAllWithPageNo("select * from tbl_library_branch where branchName like ?", new Object[]{q}, pageNo, pageSize);
		}
	}
	
	public Integer getBranchesCount(String q) throws SQLException{
		if(q==null||q.trim().length()==0){
			return getCount("select count(*) AS COUNT from tbl_library_branch", null);
		}else{
			q = "%"+q+"%";
			return getCount("select count(*) AS COUNT from tbl_library_branch where branchName like ?", new Object[]{q});
		}
	}
	
	public Branch readBranchById(Branch branch) throws SQLException{
		List<Branch> branches =  readAll(
				"select * from tbl_library_branch where branchId = ?", 
				new Object[]{ branch.getBranchId() });
		if(branches!=null && branches.size()!=0){
			return branches.get(0);
		}
		return null;
	}
	
	public Branch readBranchFirstLevelById(Branch branch) throws SQLException{
		List<Branch> branches =  readAllFirstLevel(
				"select * from tbl_library_branch where branchId = ?", 
				new Object[]{ branch.getBranchId() });
		if(branches!=null && branches.size()!=0){
			return branches.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> extractData(ResultSet rs) {
		List<Branch> branches = new ArrayList<>();
		BookCopyDAO bdao = new BookCopyDAO(conn);
		try {
			while (rs.next()) {
				Branch b = new Branch();
				b.setBranchId(rs.getInt("branchId"));
				b.setBranchAddress(rs.getString("branchAddress"));
				b.setBranchName(rs.getString("branchName"));
				b.setBookCopy(bdao.readAllBookCopiesFirstLevelByBranch(b));
				branches.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branches;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> extractDataFirstLevel(ResultSet rs) {
		List<Branch> branches = new ArrayList<>();
		try {
			while (rs.next()) {
				Branch b = new Branch();
				b.setBranchId(rs.getInt("branchId"));
				b.setBranchAddress(rs.getString("branchAddress"));
				b.setBranchName(rs.getString("branchName"));
				branches.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branches;
	}

}
