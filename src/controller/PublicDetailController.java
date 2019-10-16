package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comment;
import model.bean.News;
import model.dao.CommentDAO;
import model.dao.NewsDAO;

public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;
	private CommentDAO commentDAO;

	public PublicDetailController() {
		super();
		newsDAO = new NewsDAO();
		commentDAO = new CommentDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}

		News news = newsDAO.getItem(id);
		if (news == null) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}

		// kiểm tra nếu có cookie tin này thì không tăng view
		Cookie[] cookies = request.getCookies();
		boolean check = true;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("itemNews-" + news.getId()) && cookie.getMaxAge() != 0) {
				check = false;
				break;
			}
		}
		if (check) {
			//tăng views và add cookie lần sau sẽ không tăng nữa
			newsDAO.increaseView(id);
			Cookie cookie = new Cookie("itemNews-" + news.getId(), news.getId() + "");
			cookie.setMaxAge(60 * 60);
			response.addCookie(cookie);
		}
		
		//chỉ lấy mấy cái đầu xỏ thôi còn lại qua jsp xử lý tiếp
		ArrayList<Comment> listComments = commentDAO.getActiveItemsByCommentParentIdOfNews(0, news.getId());
		request.setAttribute("news", news);
		request.setAttribute("listComments", listComments);
		
		RequestDispatcher rd = request.getRequestDispatcher("/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
