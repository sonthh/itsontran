package test;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Mail")
public class Mail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Mail() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());

		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		System.out.println(url);
		
		StringBuffer URL = request.getRequestURL();
		String uri = request.getRequestURI();
		String host = URL.substring(0, URL.indexOf(uri));
		System.out.println(host);
		
		Enumeration<String> ar = request.getHeaderNames();
		while (ar.hasMoreElements()) {
			String string = (String) ar.nextElement();
			System.out.println(string);
		}
		System.out.println(request.authenticate(response));
		/*
		 * boolean check = SendMailUtil.send("sonthh.vinaenter@gmail.com",
		 * "Test gởi mail", "hahaha" + request.getContextPath() + "/register",
		 * "tranhuuhongson@gmail.com", "tranhuuhongSon1998@");
		 * System.out.println(check);
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
