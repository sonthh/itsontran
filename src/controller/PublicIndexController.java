package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.News;
import model.dao.NewsDAO;
import util.DefineUtil;


/**
 * 	@author Dell
 *	@see	public index website
 *	get new items from database: "WHERE id DESC"
 */
public class PublicIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;

	public PublicIndexController() {
		super();
		newsDAO = new NewsDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int numberOfItems = newsDAO.countItems();
		if (numberOfItems > 0) {
			int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
			int currentPage = 1;
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
			}
			if (currentPage < 1 || currentPage > numberOfPages)
				currentPage = 1;
			
			int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;

			request.setAttribute("numberOfPages", numberOfPages);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("numberOfItems", currentPage);

			ArrayList<News> listNewses = newsDAO.getItemsPaginaiton(offset);
			request.setAttribute("listNewses", listNewses);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/public/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
