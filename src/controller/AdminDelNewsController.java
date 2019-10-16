package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.News;
import model.bean.User;
import model.dao.CommentDAO;
import model.dao.NewsDAO;
import util.AuthUtil;
import util.DefineUtil;

//@MultipartConfig
public class AdminDelNewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;
	private CommentDAO commentDAO;

	public AdminDelNewsController() {
		super();
		newsDAO = new NewsDAO();
		commentDAO = new CommentDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		}
		News news = newsDAO.getItem(id);
		if (news == null) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		} else {
			/* không cho user xóa tin trái phép: chỉ có admin và tác giả mới được xóa */
			User user = AuthUtil.getUserLogin(request);
			System.out.println(news.getCreateBy());
			System.out.println(user.getId());
			if (user.getUserPermissions().getId() != 1 && news.getCreateBy() != user.getId()) {
				response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
				return;
			}
		}

		// delete song in songs table
		if (newsDAO.deleteItem(id) > 0) {
			String picture = news.getPicture();
			if (!"".equals(picture)) {
				String path = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD + File.separator
						+ picture;
				File file = new File(path);
				file.delete();
			}
			// xóa những comment của news này
			commentDAO.deleteItemByNewsId(id);
			
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=3&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/songs?msg=0&page=" + currentPage);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
