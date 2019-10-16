package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.News;
import model.bean.User;
import model.dao.NewsDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexNewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;
       
    public AdminIndexNewsController() {
        super();
        newsDAO = new NewsDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = AuthUtil.getUserLogin(request);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		}
		int numberOfItems = 0;
		//*** admin thì thấy hết tin còn editor thì thấy tin của mình thôi
		if (user.getUserPermissions().getId() == 1)
			numberOfItems = newsDAO.countItems();
		else
			numberOfItems = newsDAO.countItemsOfUser(user.getId());
		//***
		
		if (numberOfItems > 0) {
			int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
			int currentPage = 1;
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
			}
			if (currentPage < 1)
				currentPage = 1;
			else if (currentPage > numberOfPages)
				currentPage = numberOfPages;
			int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;
			
			//*** admin thì thấy hết tin còn editor thì thấy tin của mình thôi
			ArrayList<News> listNewses = null;
			if (user.getUserPermissions().getId() == 1)
				listNewses = newsDAO.getItemsPaginaiton(offset);
			else
				listNewses = newsDAO.getItemsPaginaitonOfUser(offset, user.getId());
			//***
			
			request.setAttribute("listNewses", listNewses);
			
			request.setAttribute("numberOfPages", numberOfPages);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("numberOfItems", numberOfItems); 
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
