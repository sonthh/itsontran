package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comment;
import model.bean.News;
import model.bean.User;
import model.dao.CommentDAO;
import model.dao.NewsDAO;
import util.AuthUtil;

public class AdminIndexCommentNewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;
	private CommentDAO commentDAO;
       
    public AdminIndexCommentNewsController() {
        super();
        newsDAO = new NewsDAO();
        commentDAO = new CommentDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?page=" + currentPage);
			return;
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		}
		//id không tồn tại
		News news = newsDAO.getItem(id);
		if (news == null) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		} else {
			/* chỉ cho admin và editor của chình bài viết này được vô phần comment */
			User user = AuthUtil.getUserLogin(request);
			if (user.getUserPermissions().getId() != 1 && news.getCreateBy() != user.getId()) {
				response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
				return;
			}
			
			ArrayList<Comment> listComments = commentDAO.getItemsByNewsIdAndSort(id);
			request.setAttribute("listComments", listComments);
			request.setAttribute("news", news);
			request.setAttribute("currentPage", currentPage);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/comment.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
