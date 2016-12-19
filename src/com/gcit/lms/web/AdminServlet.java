package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@WebServlet({"/admin/viewAuthors", "/admin/viewAuthor", "/admin/editAuthor", "/admin/deleteAuthor", "/admin/addAuthor",
	"/admin/viewPublishers", "/admin/viewPublisher", "/admin/editPublisher", "/admin/deletePublisher", "/admin/addPublisher",
	"/admin/viewBranches", "/admin/viewBranch", "/admin/editBranch", "/admin/deleteBranch", "/admin/addBranch",
	"/admin/viewBorrowers", "/admin/viewBorrower", "/admin/editBorrower", "/admin/deleteBorrower", "/admin/addBorrower",
	"/admin/viewBooks", "/admin/viewBook", "/admin/editBook", "/admin/deleteBook", "/admin/addBook"})
public class AdminServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final AdminService adminService = new AdminService();
	private static final Integer pageSize = 10;

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		switch(request.getServletPath()){
		case "/admin/viewAuthors":
			viewAuthors(request, response);
			break;
		case "/admin/viewAuthor":
			viewAuthor(request, response);
			break;
		case "/admin/editAuthor":
			editAuthor(request, response);
			break;
		case "/admin/deleteAuthor":
			deleteAuthor(request, response);
			break;
		case "/admin/viewPublishers":
			viewPublishers(request, response);
			break;
		case "/admin/viewPublisher":
			viewPublisher(request, response);
			break;
		case "/admin/editPublisher":
			editPublisher(request, response);
			break;
		case "/admin/deletePublisher":
			deletePublisher(request, response);
			break;
		case "/admin/viewBranches":
			viewBranches(request, response);
			break;
		case "/admin/viewBranch":
			viewBranch(request, response);
			break;
		case "/admin/editBranch":
			editBranch(request, response);
			break;
		case "/admin/deleteBranch":
			deleteBranch(request, response);
			break;
		case "/admin/viewBorrowers":
			viewBorrowers(request, response);
			break;
		case "/admin/viewBorrower":
			viewBorrower(request, response);
			break;
		case "/admin/editBorrower":
			editBorrower(request, response);
			break;
		case "/admin/deleteBorrower":
			deleteBorrower(request, response);
			break;
		case "/admin/viewBooks":
			viewBooks(request, response);
			break;
		case "/admin/viewBook":
			viewBook(request, response);
			break;
		case "/admin/editBook":
			editBook(request, response);
			break;
		case "/admin/deleteBook":
			deleteBook(request, response);
			break;
		default:
			break;
		}
	}
/* * * * * * * * * * * * * * * * * * GET: admin borrower * * * * * * * * * * * * * * * * * * */	
	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookIdStr = request.getParameter("bookId");
		try {
			Book book = new Book();
			book.setBookId(Integer.parseInt(bookIdStr));
			book = adminService.readBookById(book);
			StringBuilder sb = new StringBuilder();
			sb.append("<span> Are you sure that you want to delete ");
			sb.append(book.getTitle());
			sb.append(" from the database?</span><br /><span><input type='hidden' id='deleteBookId' value='");
			sb.append(book.getBookId());
			sb.append("'> </span>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException |NullPointerException e) {
			e.printStackTrace();
		}	
	}
	
	private void viewBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookIdStr = request.getParameter("bookId");
		try {
			Book book = new Book();
			book.setBookId(Integer.parseInt(bookIdStr));
			book = adminService.readBookById(book);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Book ID:</label><p class='form-control-static'>");
			sb.append(book.getBookId());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Title:</label><p class='form-control-static'>");
			sb.append(book.getTitle());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Publisher:</label><p class='form-control-static'>");
			if(book.getPublisher() != null && book.getPublisher().getPublisherName() != null) sb.append(book.getPublisher().getPublisherName());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Genres:</label>");
			if(book.getGenres().size() != 0){
				for(Genre genre : book.getGenres()){
					sb.append("<p class='form-control-static'>");
					sb.append(genre.getGenreName());
					sb.append("</p>");
				}
			}else{
				sb.append("<p class='form-control-static'></p>");
			}
			sb.append("</div><div class='form-group'><label class='control-label'>Authors:</label>");
			if(book.getAuthors().size() != 0){
				for(Author author : book.getAuthors()){
					sb.append("<p class='form-control-static'>");
					sb.append(author.getAuthorName());
					sb.append("</p>");
				}
			}else{
				sb.append("<p class='form-control-static'></p>");
			}
			sb.append("</div>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void editBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookIdStr = request.getParameter("bookId");
		try {
			Book book = new Book();
			book.setBookId(Integer.parseInt(bookIdStr));
			book = adminService.readBookById(book);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Enter Title to Edit:</label><input class='form-control' type='text' id='editBookName' value='");
			sb.append(book.getTitle());
			/*
			 * add publisher, genres, authors
			 */
			sb.append("'></div><input type='hidden' id='editBookId' value='");
			sb.append(book.getBookId());
			sb.append("'>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void viewBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String q = request.getParameter("searchString");
		try {
			String pageNoStr = request.getParameter("pageNo");
			Integer pageNo = null;
			if(pageNoStr == null){
				pageNo = 1;
			}else{
				pageNo = Integer.parseInt(pageNoStr);
			}
			List<Book> books = adminService.readAllBooksWithPageNo(pageNo, pageSize, q);
			Integer count = adminService.getBooksCount(q);
			Integer pages = (count + pageSize - 1)/pageSize;
			StringBuilder sb = new StringBuilder();
			sb.append("<nav aria-label='Page navigation'><ul class='pagination'><li");
			if(pageNo < 2) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Previous'");
			if(pageNo > 1) {
				sb.append(" onclick='viewBooks(");
				sb.append(pageNo-1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= pages; i++) {
				sb.append("<li");
				if(pageNo == i) {
					sb.append(" class='disabled'><a href='#'>");
				}else{
					sb.append("><a href='#' onclick='viewBooks(");
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
				sb.append(" onclick='viewBooks(");
				sb.append(pageNo+1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav><table class='table'>");
			sb.append("<tr><th>#</th><th>Book Name</th><th>View Detail</th><th>Edit Book</th><th>Delete Book</th></tr>");
			int index = 1+(pageNo-1)*10;
			for (Book a : books) {
				sb.append("<tr><td>");
				sb.append(index++);
				sb.append("</td><td>");
				sb.append(a.getTitle());
				sb.append("</td><td><button class='btn btn-info' data-toggle='modal' data-target='#viewBookModal' onclick='viewBook(");
				sb.append(a.getBookId());
				sb.append(")'>View</button></td><td><button class='btn btn-success' data-toggle='modal' data-target='#editBookModal' onclick='editBook(");
				sb.append(a.getBookId());
				sb.append(")'>Edit</button></td><td><button class='btn btn-danger' data-toggle='modal' data-target='#deleteBookModal' onclick='deleteBook(");
				sb.append(a.getBookId());
				sb.append(")'>Delete</button></td>");
			}
			sb.append("</table>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
/* * * * * * * * * * * * * * * * * * GET: admin borrower * * * * * * * * * * * * * * * * * * */
	private void deleteBorrower(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String borrowerIdStr = request.getParameter("borrowerId");
		try {
			Borrower borrower = new Borrower();
			borrower.setCardNo(Integer.parseInt(borrowerIdStr));
			borrower = adminService.readBorrowerById(borrower);
			StringBuilder sb = new StringBuilder();
			sb.append("<span> Are you sure that you want to delete ");
			sb.append(borrower.getName());
			sb.append(" from the database?</span><br /><span><input type='hidden' id='deleteCardNo' value='");
			sb.append(borrower.getCardNo());
			sb.append("'> </span>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException |NullPointerException e) {
			e.printStackTrace();
		}	
	}
	
	private void viewBorrower(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String borrowerIdStr = request.getParameter("borrowerId");
		try {
			Borrower borrower = new Borrower();
			borrower.setCardNo(Integer.parseInt(borrowerIdStr));
			borrower = adminService.readBorrowerById(borrower);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Borrower ID:</label><p class='form-control-static'>");
			sb.append(borrower.getCardNo());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Borrower Name:</label><p class='form-control-static'>");
			sb.append(borrower.getName());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Borrower Address:</label><p class='form-control-static'>");
			if(borrower.getAddress() != null) sb.append(borrower.getAddress());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Borrower Phone:</label><p class='form-control-static'>");
			if(borrower.getPhone() != null) sb.append(borrower.getPhone());
			sb.append("</p></div>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void editBorrower(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String borrowerIdStr = request.getParameter("borrowerId");
		try {
			Borrower borrower = new Borrower();
			borrower.setCardNo(Integer.parseInt(borrowerIdStr));
			borrower = adminService.readBorrowerById(borrower);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Enter Borrower Name to Edit:</label><input class='form-control' type='text' id='editBorrowerName' value='");
			sb.append(borrower.getName());
			sb.append("'></div><div class='form-group'><label class='control-label'>Enter Borrower Address to Edit:</label><input class='form-control' type='text' id='editBorrowerAddress' value='");
			if(borrower.getAddress() != null) sb.append(borrower.getAddress());
			sb.append("'></div><div class='form-group'><label class='control-label'>Enter Borrower Phone to Edit:</label><input class='form-control' type='text' id='editBorrowerPhone' value='");
			if(borrower.getPhone() != null) sb.append(borrower.getPhone());
			sb.append("'></div><input type='hidden' id='editCardNo' value='");
			sb.append(borrower.getCardNo());
			sb.append("'>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void viewBorrowers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String q = request.getParameter("searchString");
		try {
			String pageNoStr = request.getParameter("pageNo");
			Integer pageNo = null;
			if(pageNoStr == null){
				pageNo = 1;
			}else{
				pageNo = Integer.parseInt(pageNoStr);
			}
			List<Borrower> borrowers = adminService.readAllBorrowersWithPageNo(pageNo, pageSize, q);
			Integer count = adminService.getBorrowersCount(q);
			Integer pages = (count + pageSize - 1)/pageSize;
			StringBuilder sb = new StringBuilder();
			sb.append("<nav aria-label='Page navigation'><ul class='pagination'><li");
			if(pageNo < 2) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Previous'");
			if(pageNo > 1) {
				sb.append(" onclick='viewBorrowers(");
				sb.append(pageNo-1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= pages; i++) {
				sb.append("<li");
				if(pageNo == i) {
					sb.append(" class='disabled'><a href='#'>");
				}else{
					sb.append("><a href='#' onclick='viewBorrowers(");
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
				sb.append(" onclick='viewBorrowers(");
				sb.append(pageNo+1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav><table class='table'>");
			sb.append("<tr><th>#</th><th>Borrower Name</th><th>View Detail</th><th>Edit Borrower</th><th>Delete Borrower</th></tr>");
			int index = 1+(pageNo-1)*10;
			for (Borrower a : borrowers) {
				sb.append("<tr><td>");
				sb.append(index++);
				sb.append("</td><td>");
				sb.append(a.getName());
				sb.append("</td><td><button class='btn btn-info' data-toggle='modal' data-target='#viewBorrowerModal' onclick='viewBorrower(");
				sb.append(a.getCardNo());
				sb.append(")'>View</button></td><td><button class='btn btn-success' data-toggle='modal' data-target='#editBorrowerModal' onclick='editBorrower(");
				sb.append(a.getCardNo());
				sb.append(")'>Edit</button></td><td><button class='btn btn-danger' data-toggle='modal' data-target='#deleteBorrowerModal' onclick='deleteBorrower(");
				sb.append(a.getCardNo());
				sb.append(")'>Delete</button></td>");
			}
			sb.append("</table>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
/* * * * * * * * * * * * * * * * * * GET: admin branch * * * * * * * * * * * * * * * * * * */
	private void deleteBranch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String branchIdStr = request.getParameter("branchId");
		try {
			Branch branch = new Branch();
			branch.setBranchId(Integer.parseInt(branchIdStr));
			branch = adminService.readBranchById(branch);
			StringBuilder sb = new StringBuilder();
			sb.append("<span> Are you sure that you want to delete ");
			sb.append(branch.getBranchName());
			sb.append(" from the database?</span><br /><span><input type='hidden' id='deleteBranchId' value='");
			sb.append(branch.getBranchId());
			sb.append("'> </span>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException |NullPointerException e) {
			e.printStackTrace();
		}	
	}
	
	private void viewBranch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String branchIdStr = request.getParameter("branchId");
		try {
			Branch branch = new Branch();
			branch.setBranchId(Integer.parseInt(branchIdStr));
			branch = adminService.readBranchById(branch);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Branch ID:</label><p class='form-control-static'>");
			sb.append(branch.getBranchId());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Branch Name:</label><p class='form-control-static'>");
			sb.append(branch.getBranchName());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Branch Address:</label><p class='form-control-static'>");
			if(branch.getBranchAddress() != null) sb.append(branch.getBranchAddress());
			sb.append("</p></div>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void editBranch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String branchIdStr = request.getParameter("branchId");
		try {
			Branch branch = new Branch();
			branch.setBranchId(Integer.parseInt(branchIdStr));
			branch = adminService.readBranchById(branch);
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
	
	private void viewBranches(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String q = request.getParameter("searchString");
		try {
			String pageNoStr = request.getParameter("pageNo");
			Integer pageNo = null;
			if(pageNoStr == null){
				pageNo = 1;
			}else{
				pageNo = Integer.parseInt(pageNoStr);
			}
			List<Branch> branches = adminService.readAllBranchesWithPageNo(pageNo, pageSize, q);
			Integer count = adminService.getBranchesCount(q);
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
			sb.append("<tr><th>#</th><th>Branch Name</th><th>View Detail</th><th>Edit Branch</th><th>Delete Branch</th></tr>");
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
				sb.append(")'>Edit</button></td><td><button class='btn btn-danger' data-toggle='modal' data-target='#deleteBranchModal' onclick='deleteBranch(");
				sb.append(a.getBranchId());
				sb.append(")'>Delete</button></td>");
			}
			sb.append("</table>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
/* * * * * * * * * * * * * * * * * * GET: admin publisher * * * * * * * * * * * * * * * * * * */
	private void deletePublisher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String publisherIdStr = request.getParameter("publisherId");
		try {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(Integer.parseInt(publisherIdStr));
			publisher = adminService.readPublisherById(publisher);
			StringBuilder sb = new StringBuilder();
			sb.append("<span> Are you sure that you want to delete ");
			sb.append(publisher.getPublisherName());
			sb.append(" from the database?</span><br /><span><input type='hidden' id='deletePublisherId' value='");
			sb.append(publisher.getPublisherId());
			sb.append("'> </span>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException |NullPointerException e) {
			e.printStackTrace();
		}	
	}
	
	private void viewPublisher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String publisherIdStr = request.getParameter("publisherId");
		try {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(Integer.parseInt(publisherIdStr));
			publisher = adminService.readPublisherById(publisher);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Publisher ID:</label><p class='form-control-static'>");
			sb.append(publisher.getPublisherId());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Publisher Name:</label><p class='form-control-static'>");
			sb.append(publisher.getPublisherName());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Publisher Address:</label><p class='form-control-static'>");
			if(publisher.getPublisherAddress() != null) sb.append(publisher.getPublisherAddress());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Publisher Phone:</label><p class='form-control-static'>");
			if(publisher.getPublisherPhone() != null) sb.append(publisher.getPublisherPhone());
			sb.append("</p></div>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void editPublisher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String publisherIdStr = request.getParameter("publisherId");
		try {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(Integer.parseInt(publisherIdStr));
			publisher = adminService.readPublisherById(publisher);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Enter Publisher Name to Edit:</label><input class='form-control' type='text' id='editPublisherName' value='");
			sb.append(publisher.getPublisherName());
			sb.append("'></div><div class='form-group'><label class='control-label'>Enter Publisher Address to Edit:</label><input class='form-control' type='text' id='editPublisherAddress' value='");
			if(publisher.getPublisherAddress() != null) sb.append(publisher.getPublisherAddress());
			sb.append("'></div><div class='form-group'><label class='control-label'>Enter Publisher Phone to Edit:</label><input class='form-control' type='text' id='editPublisherPhone' value='");
			if(publisher.getPublisherPhone() != null) sb.append(publisher.getPublisherPhone());
			sb.append("'></div><input type='hidden' id='editPublisherId' value='");
			sb.append(publisher.getPublisherId());
			sb.append("'>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void viewPublishers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String q = request.getParameter("searchString");
		try {
			String pageNoStr = request.getParameter("pageNo");
			Integer pageNo = null;
			if(pageNoStr == null){
				pageNo = 1;
			}else{
				pageNo = Integer.parseInt(pageNoStr);
			}
			List<Publisher> publishers = adminService.readAllPublishersWithPageNo(pageNo, pageSize, q);
			Integer count = adminService.getPublishersCount(q);
			Integer pages = (count + pageSize - 1)/pageSize;
			StringBuilder sb = new StringBuilder();
			sb.append("<nav aria-label='Page navigation'><ul class='pagination'><li");
			if(pageNo < 2) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Previous'");
			if(pageNo > 1) {
				sb.append(" onclick='viewPublishers(");
				sb.append(pageNo-1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= pages; i++) {
				sb.append("<li");
				if(pageNo == i) {
					sb.append(" class='disabled'><a href='#'>");
				}else{
					sb.append("><a href='#' onclick='viewPublishers(");
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
				sb.append(" onclick='viewPublishers(");
				sb.append(pageNo+1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav><table class='table'>");
			sb.append("<tr><th>#</th><th>Publisher Name</th><th>View Detail</th><th>Edit Publisher</th><th>Delete Publisher</th></tr>");
			int index = 1+(pageNo-1)*10;
			for (Publisher a : publishers) {
				sb.append("<tr><td>");
				sb.append(index++);
				sb.append("</td><td>");
				sb.append(a.getPublisherName());
				sb.append("</td><td><button class='btn btn-info' data-toggle='modal' data-target='#viewPublisherModal' onclick='viewPublisher(");
				sb.append(a.getPublisherId());
				sb.append(")'>View</button></td><td><button class='btn btn-success' data-toggle='modal' data-target='#editPublisherModal' onclick='editPublisher(");
				sb.append(a.getPublisherId());
				sb.append(")'>Edit</button></td><td><button class='btn btn-danger' data-toggle='modal' data-target='#deletePublisherModal' onclick='deletePublisher(");
				sb.append(a.getPublisherId());
				sb.append(")'>Delete</button></td>");
			}
			sb.append("</table>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}	
/* * * * * * * * * * * * * * * * * * GET: admin author * * * * * * * * * * * * * * * * * * */
	private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorIdStr = request.getParameter("authorId");
		try {
			Author author = new Author();
			author.setAuthorId(Integer.parseInt(authorIdStr));
			author = adminService.readAuthorById(author);
			StringBuilder sb = new StringBuilder();
			sb.append("<span> Are you sure that you want to delete ");
			sb.append(author.getAuthorName());
			sb.append(" from the database?</span><br /><span><input type='hidden' id='deleteAuthorId' value='");
			sb.append(author.getAuthorId());
			sb.append("'> </span>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException |NullPointerException e) {
			e.printStackTrace();
		}	
	}

	private void viewAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorIdStr = request.getParameter("authorId");
		try {
			Author author = new Author();
			author.setAuthorId(Integer.parseInt(authorIdStr));
			author = adminService.readAuthorById(author);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Author ID:</label><p class='form-control-static'>");
			sb.append(author.getAuthorId());
			sb.append("</p></div><div class='form-group'><label class='control-label'>Author Name:</label><p class='form-control-static'>");
			sb.append(author.getAuthorName());
			sb.append("</p></div>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void editAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorIdStr = request.getParameter("authorId");
		try {
			Author author = new Author();
			author.setAuthorId(Integer.parseInt(authorIdStr));
			author = adminService.readAuthorById(author);
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='form-group'><label class='control-label'>Enter Author Name to Edit:</label><input class='form-control' type='text' id='editAuthorName' value='");
			sb.append(author.getAuthorName());
			sb.append("'></div><input type='hidden' id='editAuthorId' value='");
			sb.append(author.getAuthorId());
			sb.append("'>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void viewAuthors(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String q = request.getParameter("searchString");
		try {
			String pageNoStr = request.getParameter("pageNo");
			Integer pageNo = null;
			if(pageNoStr == null){
				pageNo = 1;
			}else{
				pageNo = Integer.parseInt(pageNoStr);
			}
			List<Author> authors = adminService.readAllAuthorsWithPageNo(pageNo, pageSize, q);
			Integer count = adminService.getAuthorsCount(q);
			Integer pages = (count + pageSize - 1)/pageSize;
			StringBuilder sb = new StringBuilder();
			sb.append("<nav aria-label='Page navigation'><ul class='pagination'><li");
			if(pageNo < 2) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Previous'");
			if(pageNo > 1) {
				sb.append(" onclick='viewAuthors(");
				sb.append(pageNo-1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= pages; i++) {
				sb.append("<li");
				if(pageNo == i) {
					sb.append(" class='disabled'><a href='#'>");
				}else{
					sb.append("><a href='#' onclick='viewAuthors(");
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
				sb.append(" onclick='viewAuthors(");
				sb.append(pageNo+1);
				sb.append(")'");
			}
			sb.append("> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav><table class='table'>");
			sb.append("<tr><th>#</th><th>Author Name</th><th>View Detail</th><th>Edit Author</th><th>Delete Author</th></tr>");
			int index = 1+(pageNo-1)*10;
			for (Author a : authors) {
				sb.append("<tr><td>");
				sb.append(index++);
				sb.append("</td><td>");
				sb.append(a.getAuthorName());
				sb.append("</td><td><button class='btn btn-info' data-toggle='modal' data-target='#viewAuthorModal' onclick='viewAuthor(");
				sb.append(a.getAuthorId());
				sb.append(")'>View</button></td><td><button class='btn btn-success' data-toggle='modal' data-target='#editAuthorModal' onclick='editAuthor(");
				sb.append(a.getAuthorId());
				sb.append(")'>Edit</button></td><td><button class='btn btn-danger' data-toggle='modal' data-target='#deleteAuthorModal' onclick='deleteAuthor(");
				sb.append(a.getAuthorId());
				sb.append(")'>Delete</button></td>");
			}
			sb.append("</table>");
			response.getWriter().append(sb.toString());
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
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
		case "/admin/editAuthor":
			try {
				updateAuthor(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/deleteAuthor":
			try {
				removeAuthor(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/addAuthor":
			try {
				addAuthor(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/editPublisher":
			try {
				updatePublisher(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/deletePublisher":
			try {
				removePublisher(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/addPublisher":
			try {
				addPublisher(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/editBranch":
			try {
				updateBranch(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/deleteBranch":
			try {
				removeBranch(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/addBranch":
			try {
				addBranch(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/editBorrower":
			try {
				updateBorrower(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/deleteBorrower":
			try {
				removeBorrower(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/addBorrower":
			try {
				addBorrower(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/editBook":
			try {
				updateBook(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/deleteBook":
			try {
				removeBook(request, response);
				//response.setStatus(200);
			} catch (NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/admin/addBook":
			try {
				addBook(request, response);
				//response.setStatus(200);
			} catch (MySQLIntegrityConstraintViolationException | NumberFormatException | NullPointerException e) {
				response.sendError(400);
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
/* * * * * * * * * * * * * * * * * * POST: admin book * * * * * * * * * * * * * * * * * * */
	private void addBook(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String bookName = request.getParameter("bookName").trim();
		/*
		 * add publisher, genres, authors
		 */
		if(bookName.length() == 0) throw new NullPointerException("Book name cannot be empty");
		Book book = new Book();
		book.setTitle(bookName);
		adminService.addBook(book);
	}

	private void removeBook(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String bookIdStr = request.getParameter("bookId");
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookIdStr));
		adminService.deleteBook(book);
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) 
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String bookIdStr = request.getParameter("bookId");
		String bookName = request.getParameter("bookName").trim();
		/*
		 * add publisher, genres, authors
		 */
		if(bookName.length() == 0) throw new NullPointerException("Book name cannot be empty");
		Book book = new Book();
		book.setBookId(Integer.parseInt(bookIdStr));
		book.setTitle(bookName);
		adminService.editBook(book);
	}
/* * * * * * * * * * * * * * * * * * * POST: admin borrower * * * * * * * * * * * * * * * * * * */
	private void addBorrower(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String borrowerName = request.getParameter("borrowerName").trim();
		String borrowerAddress = request.getParameter("borrowerAddress").trim();
		String borrowerPhone = request.getParameter("borrowerPhone").trim();
		if (borrowerName.length() == 0)
			throw new NullPointerException("Borrower name cannot be empty");
		Borrower borrower = new Borrower();
		borrower.setName(borrowerName);
		borrower.setAddress(borrowerAddress.length() == 0 ? null : borrowerAddress);
		borrower.setPhone(borrowerPhone.length() == 0 ? null : borrowerPhone);
		adminService.addBorrower(borrower);
	}

	private void removeBorrower(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String borrowerIdStr = request.getParameter("borrowerId");
		Borrower borrower = new Borrower();
		borrower.setCardNo(Integer.parseInt(borrowerIdStr));
		adminService.deleteBorrower(borrower);
	}

	private void updateBorrower(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String borrowerIdStr = request.getParameter("borrowerId");
		String borrowerName = request.getParameter("borrowerName").trim();
		String borrowerAddress = request.getParameter("borrowerAddress").trim();
		String borrowerPhone = request.getParameter("borrowerPhone").trim();
		if (borrowerName.length() == 0)
			throw new NullPointerException("Borrower name cannot be empty");
		Borrower borrower = new Borrower();
		borrower.setCardNo(Integer.parseInt(borrowerIdStr));
		borrower.setName(borrowerName);
		borrower.setAddress(borrowerAddress.length() == 0 ? null : borrowerAddress);
		borrower.setPhone(borrowerPhone.length() == 0 ? null : borrowerPhone);
		adminService.editBorrower(borrower);
	}
/* * * * * * * * * * * * * * * * * * * POST: admin branch * * * * * * * * * * * * * * * * * * */
	private void addBranch(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String branchName = request.getParameter("branchName").trim();
		String branchAddress = request.getParameter("branchAddress").trim();
		if (branchName.length() == 0)
			throw new NullPointerException("Branch name cannot be empty");
		Branch branch = new Branch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress.length() == 0 ? null : branchAddress);
		adminService.addBranch(branch);
	}

	private void removeBranch(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String branchIdStr = request.getParameter("branchId");
		Branch branch = new Branch();
		branch.setBranchId(Integer.parseInt(branchIdStr));
		adminService.deleteBranch(branch);
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
		adminService.editBranch(branch);
	}
/* * * * * * * * * * * * * * * * * * * POST: admin publisher * * * * * * * * * * * * * * * * * * */
	private void addPublisher(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String publisherName = request.getParameter("publisherName").trim();
		String publisherAddress = request.getParameter("publisherAddress").trim();
		String publisherPhone = request.getParameter("publisherPhone").trim();
		if (publisherName.length() == 0)
			throw new NullPointerException("Publisher name cannot be empty");
		Publisher publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress.length() == 0 ? null : publisherAddress);
		publisher.setPublisherPhone(publisherPhone.length() == 0 ? null : publisherPhone);
		adminService.addPublisher(publisher);
	}

	private void removePublisher(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String publisherIdStr = request.getParameter("publisherId");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(publisherIdStr));
		adminService.deletePublisher(publisher);
	}

	private void updatePublisher(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException {
		String publisherIdStr = request.getParameter("publisherId");
		String publisherName = request.getParameter("publisherName").trim();
		String publisherAddress = request.getParameter("publisherAddress").trim();
		String publisherPhone = request.getParameter("publisherPhone").trim();
		if (publisherName.length() == 0)
			throw new NullPointerException("Publisher name cannot be empty");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(publisherIdStr));
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress.length() == 0 ? null : publisherAddress);
		publisher.setPublisherPhone(publisherPhone.length() == 0 ? null : publisherPhone);
		adminService.editPublisher(publisher);
	}
	
/* * * * * * * * * * * * * * * * * * POST: admin author * * * * * * * * * * * * * * * * * * */
	private void addAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String authorName = request.getParameter("authorName").trim();
		if(authorName.length() == 0) throw new NullPointerException("Author name cannot be empty");
		Author author = new Author();
		author.setAuthorName(authorName);
		adminService.addAuthor(author);
	}

	private void removeAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String authorIdStr = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorIdStr));
		adminService.deleteAuthor(author);
	}

	private void updateAuthor(HttpServletRequest request, HttpServletResponse response) 
			throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String authorIdStr = request.getParameter("authorId");
		String authorName = request.getParameter("authorName").trim();
		if(authorName.length() == 0) throw new NullPointerException("Author name cannot be empty");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorIdStr));
		author.setAuthorName(authorName);
		adminService.editAuthor(author);
	}
}
