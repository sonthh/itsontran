package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.AuthUtil;
import util.DateUtil;

public class AuthHowLongAgoLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthHowLongAgoLoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if (AuthUtil.checkDoneLogin(request)) {
    		Date loginTime = (Date) session.getAttribute("loginTime");
    		out.println("Acess " + DateUtil.getHowLongAgo(loginTime, new Date()));
		} else {
			System.out.println("Chưa đăng nhập mà sao lại click được vào đây");
		}
	}

}
