package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.News;
import model.dao.CategoryDAO;
import model.dao.CommentDAO;
import model.dao.NewsDAO;

public class AdminDelCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	private CommentDAO commentDAO;
	private NewsDAO newsDAO;

	public AdminDelCatController() {
		super();
		categoryDAO = new CategoryDAO();
		commentDAO = new CommentDAO();
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
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}
		
		if (categoryDAO.deleteParentItemAndSubItems(id) > 0) {
			//duyệt qua tất cả bài hát trong danh mục này và xóa những comment của nó
			
			ArrayList<News> list = newsDAO.getItemsByCatId(id);
			for (News item : list) {
				commentDAO.deleteItemByNewsId(item.getId());
			}
			//xóa những bài hát thuộc danh mục này
			newsDAO.deleteItemByCatId(id);
			
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=3&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
