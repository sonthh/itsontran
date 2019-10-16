package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comment;
import model.dao.CommentDAO;

public class AdminActiveCommentNewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private NewsDAO newsDAO;
	private CommentDAO commentDAO;
       
    public AdminActiveCommentNewsController() {
        super();
        commentDAO = new CommentDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("AdminActiveCommentNewsController.doPost()");
		int commentId = Integer.parseInt(request.getParameter("acommentId"));
		int newsId = Integer.parseInt(request.getParameter("anewsId"));
		int status = Integer.parseInt(request.getParameter("astatus"));
		
		status = status == 1 ? 0 : 1;
		int result = commentDAO.activeItemAndSubItem(commentId, newsId, status);
		System.out.println("Result:" + result);
		
		//lấy ra danh sách các commnent bị ảnh hưởng bởi nut active rồi gởi về lại javascript để toggle class active
		ArrayList<Comment> list = new ArrayList<>();
		list.add(commentDAO.getItem(commentId));
		list.addAll(commentDAO.getItemsByCommentParentIdOfNewsAll(commentId, newsId));
		PrintWriter out = response.getWriter();
		StringBuilder responseString = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i != list.size() - 1)
				responseString.append(list.get(i).getId() + ";");
			else 
				responseString.append(list.get(i).getId());
		}
		out.print(responseString);
	}

}
