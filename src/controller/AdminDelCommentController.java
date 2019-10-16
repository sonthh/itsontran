package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comment;
import model.dao.CommentDAO;

//@MultipartConfig
public class AdminDelCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentDAO commentDAO;

	public AdminDelCommentController() {
		super();
		commentDAO = new CommentDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}

		/* id của comment */
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		}
		
		Comment comment = commentDAO.getItem(id);
		
		if (comment == null) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0=");
			return;
		} else {
			int newsId = comment.getNewsId();
			/* Chỉ có amdin và editor của tin này mới xóa comment này được */
			int result = commentDAO.deleteItem(id, newsId);
			//System.out.println("Số comment đã được xóa: " + result);
			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/news/comments?page=" + currentPage + "&id=" + newsId + "&msg=3");
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/admin/news/comments?page=" + currentPage + "&id=" + newsId + "&msg=0");
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
