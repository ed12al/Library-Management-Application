package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.service.LibrarianService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@WebServlet({"/librarian/viewBranches", "/librarian/editBranchBooks", "/librarian/editBranch"})
public class LibrarianServlet extends HttpServlet{
	private static final long serialVersionUID = -6688300013004311686L;
	private static final LibrarianService libService = new LibrarianService();
	private static final Integer pageSize = 10;
	
	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		switch(request.getServletPath()){
		case "/librarian/viewBranches":
			viewLibrarianBranches(request, response);
			break;
		case "/librarian/editBranch":
			editBranch(request, response);
			break;
		case "/librarian/editBranchBooks":
			viewBranchBooks(request, response);
			break;
		default:
			break;
		}
	}
/* * * * * * * * * * * * * * * * * * GET: librarian * * * * * * * * * * * * * * * * * * */	
	private void viewBranchBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			Branch branch = new Branch();
			branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
			branch = libService.readBranchById(branch);
			Map<Book, Integer> bookCopies = new HashMap<>();
			if(branch.getBookCopy() != null){
				for(BookCopy copy: branch.getBookCopy()){
					bookCopies.put(copy.getBook(), copy.getNoOfCopies());
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>");
			sb.append(branch.getBranchName());
			sb.append("</label></div>");
			for(Book book : libService.readAllBooksFirstLevel()){
				sb.append("<div class='form-group'><label class='control-label'>");
				sb.append(book.getTitle());
				sb.append("</label><input type='text' class='bookCopy form-control' id='");
				sb.append(book.getBookId());
				sb.append("' value='");
				sb.append(bookCopies.getOrDefault(book, 0));
				sb.append("'></div>");
			}
			sb.append("<input type='hidden' id='editBranchBookId' value='");
			sb.append(branch.getBranchId());
			sb.append("'>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
		
	}
	private void viewLibrarianBranches(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String q = request.getParameter("searchString");
		try {
			String pageNoStr = request.getParameter("pageNo");
			Integer pageNo = null;
			if(pageNoStr == null){
				pageNo = 1;
			}else{
				pageNo = Integer.parseInt(pageNoStr);
			}
			List<Branch> branches = libService.readAllBranchesWithPageNo(pageNo, pageSize, q);
			Integer count = libService.getBranchesCount(q);
			Integer pages = (count + pageSize - 1)/pageSize;
			StringBuilder sb = new StringBuilder();
			sb.append("<nav aria-label='Page navigation'><ul class='pagination'><li");
			if(pageNo < 2) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Previous'");
			if(pageNo > 1) {
				sb.append(" onclick='viewBranches(");
				sb.append(pageNo-1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= pages; i++) {
				sb.append("<li");
				if(pageNo == i) {
					sb.append(" class='disabled'><a href='#'>");
				}else{
					sb.append("><a href='#' onclick='viewBranches(");
					sb.append(i);
					sb.append(")'>");
				}
				sb.append(i);
				sb.append("</a></li>");	
			}
			sb.append("<li");
			if(pageNo >= pages) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Next'");
			if(pageNo < pages) {
				sb.append(" onclick='viewBranches(");
				sb.append(pageNo+1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav><table class='table'>");
			sb.append("<tr><th>#</th><th>Branch Name</th><th>View Detail</th><th>Edit Branch</th></tr>");
			int index = 1+(pageNo-1)*10;
			for (Branch a : branches) {
				sb.append("<tr><td>");
				sb.append(index++);
				sb.append("</td><td>");
				sb.append(a.getBranchName());
				sb.append("</td><td><button class='btn btn-info' data-toggle='modal' data-target='#viewBranchModal' onclick='viewBranch(");
				sb.append(a.getBranchId());
				sb.append(")'>View</button></td><td><button class='btn btn-success' data-toggle='modal' data-target='#editBranchModal' onclick='editBranch(");
				sb.append(a.getBranchId());
				sb.append(")'>Edit</button></td>");
			}
			sb.append("</table>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void editBranch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String branchIdStr = request.getParameter("branchId");
		try {
			Branch branch = new Branch();
			branch.setBranchId(Integer.parseInt(branchIdStr));
			branch = libService.readBranchById(branch);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Enter Branch Name to Edit:</label><input class='form-control' type='text' id='editBranchName' value='");
			sb.append(branch.getBranchName());
			sb.append("'></div><div class='form-group'><label class='control-label'>Enter Branch Address to Edit:</label><input class='form-control' type='text' id='editBranchAddress' value='");
			if(branch.getBranchAddress() != null) sb.append(branch.getBranchAddress());
			sb.append("'></div><input type='hidden' id='editBranchId' value='");
			sb.append(branch.getBranchId());
			sb.append("'>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch(request.getServletPath()){
		case "/librarian/editBranch":
			try {
				updateBranch(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				response.sendError(500);
				e.printStackTrace();
			}
			break;
		case "/librarian/editBranchBooks":	
			try {
				editBranchBooks(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				response.sendError(500);
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

/* * * * * * * * * * * * * * * * * * POST: librarian * * * * * * * * * * * * * * * * * * */	
	private void editBranchBooks(HttpServletRequest request, HttpServletResponse response) 
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException, IOException{
		Branch branch = new Branch();
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		String bookCopiesStr = request.getParameter("bookCopies");
		try{
			JSONParser parser = new JSONParser();
			JSONArray jsonArray = (JSONArray) parser.parse(bookCopiesStr);
			List<BookCopy> bookCopies = new ArrayList<>();
			for(Object o : jsonArray){
				JSONObject jsonObject = (JSONObject) o;
				try{
					Book book = new Book();
					book.setBookId(Integer.parseInt(jsonObject.get("id").toString()));
					BookCopy bookCopy = new BookCopy();
					bookCopy.setBook(book);
					bookCopy.setNoOfCopies(Integer.parseInt(jsonObject.get("copy").toString()));
					bookCopies.add(bookCopy);
				}catch(NumberFormatException e){
					System.out.println("cannot parse one record!");
				}
			}
			branch.setBookCopy(bookCopies);
		} catch (Exception e){
			e.printStackTrace();
		}
		libService.updateBranchBookCopy(branch);
	}
	private void updateBranch(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String branchIdStr = request.getParameter("branchId");
		String branchName = request.getParameter("branchName").trim();
		String branchAddress = request.getParameter("branchAddress").trim();
		if (branchName.length() == 0)
			throw new NullPointerException("Branch name cannot be empty");
		Branch branch = new Branch();
		branch.setBranchId(Integer.parseInt(branchIdStr));
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress.length() == 0 ? null : branchAddress);
		libService.editBranch(branch);
	}
}
