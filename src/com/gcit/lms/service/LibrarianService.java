package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;

public class LibrarianService {
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
	public List<Book> readAllBooksFirstLevel() throws SQLException, ClassNotFoundException {
		try (Connection conn = ConnectionUtil.getNewConnection()){
			try {
				BookDAO bookDao = new BookDAO(conn);
				return bookDao.readAllBooksFirstLevel();
			} catch (SQLException e){
				throw e;
			}
		} 
	}
}
