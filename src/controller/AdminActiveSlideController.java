package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.SlideDAO;

public class AdminActiveSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SlideDAO slideDAO;
       
    public AdminActiveSlideController() {
        super();
        slideDAO = new SlideDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("aid"));
		int status = Integer.parseInt(request.getParameter("astatus"));
		int statusChange = status == 1 ? 0 : 1;
		if (slideDAO.changeStatusItem(id, statusChange) > 0) {
//			System.out.println("active/deactive slide successful");
		} else {
			System.out.println("active/deactive slide fail");
		}
	}

}
