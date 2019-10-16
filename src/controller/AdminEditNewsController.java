package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Category;
import model.bean.News;
import model.bean.User;
import model.dao.CategoryDAO;
import model.dao.NewsDAO;
import util.AuthUtil;
import util.DefineUtil;
import util.FileUtil;

@MultipartConfig
public class AdminEditNewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;

	public AdminEditNewsController() {
		super();
		newsDAO = new NewsDAO();
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
		//id không tồn tại
		News news = newsDAO.getItem(id);
		if (news == null) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		} else {
			/* không cho user sửa tin trái phép: chỉ có admin và tác giả mới được sửa */
			User user = AuthUtil.getUserLogin(request);
			System.out.println(news.getCreateBy());
			System.out.println(user.getId());
			if (user.getUserPermissions().getId() != 1 && news.getCreateBy() != user.getId()) {
				response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
				return;
			}
		}
		request.setAttribute("news", news);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		// id không tồn tại
		News news = newsDAO.getItem(id);
		if (news == null) {
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=0&page=" + currentPage);
			return;
		}
		String oldFileName = news.getPicture();

		// get param in form
		String name = request.getParameter("name");
		int catId = Integer.parseInt(request.getParameter("category"));
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		// get filename from part
		Part part = request.getPart("image");
		String fileName = FileUtil.rename(part.getSubmittedFileName());
		User userLogin = AuthUtil.getUserLogin(request);
		Category category = new CategoryDAO().getItem(catId);
		news.setName(name);
		news.setPreview(preview);
		news.setDetail(detail);
		news.setCategory(category);
		news.setCreateBy(userLogin.getUserPermissions().getId());
		if (!"".equals(fileName)) {
			news.setPicture(fileName);
		}

		if (newsDAO.editItem(news) > 0) {
			if (!"".equals(fileName)) {
				// xóa file cũ
				String path = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD + File.separator
						+ oldFileName;
				File oldFile = new File(path);
				oldFile.delete();

				// up file mới
				String dirPath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD;
				File newFile = new File(dirPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				part.write(dirPath + File.separator + fileName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=2&page=" + currentPage);
			return;
		} else {
			request.setAttribute("news", news);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/news/edit.jsp?msg=0");
			rd.forward(request, response);
		}
	}

}
