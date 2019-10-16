package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comment;
import model.bean.User;
import model.dao.CommentDAO;
import model.dao.UserDAO;
import util.DateUtil;

public class PublicAddCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentDAO commentDAO;
	private UserDAO userDAO;

	public PublicAddCommentController() {
		super();
		commentDAO = new CommentDAO();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userCommentId = Integer.parseInt(request.getParameter("userCommentId"));
		String content = request.getParameter("content");
		/* chưa đăng nhập thì không comment nhé*/
		if (userCommentId == 0 || "".equals(content.trim())) 
			return;
		
		int newsId = Integer.parseInt(request.getParameter("newsId"));
		int parentCommentId = Integer.parseInt(request.getParameter("parentCommentId"));
		
		User user = userDAO.getItem(userCommentId);
		Timestamp date = new Timestamp(new Date().getTime());
		Comment comment = new Comment(0, content, userCommentId, date, parentCommentId, newsId);
		
		//add comment to db
		System.out.println(commentDAO.addItem(comment));
		
		PrintWriter out = response.getWriter();
		String className = parentCommentId == 0 ? "" : "left-padding";
		out.println(
			"<div class='comment-list " + className + "'>" +
				"<div class='single-comment justify-content-between d-flex'>" +
					"<div class='user justify-content-between d-flex'>" +
						"<div class='thumb'>" +
							"<i class='fa fa-user'></i>" +
						"</div>" +
						"<div class='desc'>" +
							"<h5><a href='#'>" + user.getUsername() + "</a></h5>" +
							"<p class='date'>" + DateUtil.getDateFormatDetail(date) + " </p>" +
							"<p class='comment'>" + content + "</p>" +
						"</div>" +
					"</div>" +
					"<div class='reply-btn'>" +
						"<a onclick=\"return reply(" + parentCommentId + ", '" + user.getUsername() + "')\""
						+ " href='javascript:void(0)' class='btn-reply text-uppercase'>reply</a>" +
					"</div>" +
				"</div>" +
			"</div>"
		);
		/* Chưa có cha nào nó là cha luôn */
		if (parentCommentId == 0) {
			
		}
	}

}
