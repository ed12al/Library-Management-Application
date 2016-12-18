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
import com.gcit.lms.service.AdminService;

@WebServlet({"/admin/viewAuthors", "/admin/editAuthor", "/admin/deleteAuthor", "/admin/addAuthor"})
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
		case "/admin/editAuthor":
			editAuthor(request, response);
			break;
		case "/admin/deleteAuthor":
			deleteAuthor(request, response);
			break;
		default:
			break;
		}
	}
	
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

	private void editAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorIdStr = request.getParameter("authorId");
		try {
			Author author = new Author();
			author.setAuthorId(Integer.parseInt(authorIdStr));
			author = adminService.readAuthorById(author);
			StringBuilder sb = new StringBuilder();
			sb.append("<span> Enter Author Name to Edit: <input type='text' id='editAuthorName' value='");
			sb.append(author.getAuthorName());
			sb.append("'></span><br /><span><input type='hidden' id='editAuthorId' value='");
			sb.append(author.getAuthorId());
			sb.append("'> </span>");
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
			sb.append("><a href='#' aria-label='Previous' onclick='viewAuthors(");
			sb.append(pageNo-1);
			sb.append(")'> <span aria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= pages; i++) {
				sb.append("<li");
				if(pageNo == i) sb.append(" class='disabled'");
				sb.append("><a href='#' onclick='viewAuthors(");
				sb.append(i);
				sb.append(")'>");
				sb.append(i);
				sb.append("</a></li>");	
			}
			sb.append("<li");
			if(pageNo >= pages) sb.append(" class='disabled'");
			sb.append("><a href='#' aria-label='Next' onclick='viewAuthors(");
			sb.append(pageNo+1);
			sb.append(")'> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav><table class='table'>");
			sb.append("<tr><th>#</th><th>Author Name</th><th>Edit Author</th><th>Delete Author</th></tr>");
			int index = 1+(pageNo-1)*10;
			for (Author a : authors) {
				sb.append("<tr><td>");
				sb.append(index++);
				sb.append("</td><td>");
				sb.append(a.getAuthorName());
				sb.append("</td><td><button class='btn btn-success' data-toggle='modal' data-target='#editAuthorModal' onclick='editAuthor(");
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
		default:
			break;
		}
	}

	private void addAuthor(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String authorName = request.getParameter("authorName").trim();
		if(authorName.length() == 0) throw new NullPointerException("Author name cannot be empty");
		Author author = new Author();
		author.setAuthorName(authorName);
		adminService.addAuthor(author);
	}

	private void removeAuthor(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String authorIdStr = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorIdStr));
		adminService.deleteAuthor(author);
	}

	private void updateAuthor(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, NumberFormatException, NullPointerException{
		String authorIdStr = request.getParameter("authorId");
		String authorName = request.getParameter("authorName").trim();
		if(authorName.length() == 0) throw new NullPointerException("Author name cannot be empty");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorIdStr));
		author.setAuthorName(authorName);
		adminService.editAuthor(author);
	}
}
