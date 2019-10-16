package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.bean.UserPermissions;
import model.dao.UserDAO;
import util.SendMailUtil;
import util.StringUtil;

public class AuthRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AuthRegisterController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/auth/register.jsp");
		rd.forward(request, response);
	}

	/* Sẽ xử lí tiếp tục */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = StringUtil.md5(request.getParameter("password"));
		String fullname = request.getParameter("fullname");
		String email = request.getParameter("email");

		if (userDAO.hasUser(username)) { /* Đăng kí thất bại */
			RequestDispatcher rd = request.getRequestDispatcher("/auth/register.jsp?msg=2");/* username ton tai */
			rd.forward(request, response);
			return;
		}

		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/checkuseremail?u=" + username + "&p=" + password;
		String message = "Please acess link to register successful.\nLink: " + url;
		boolean check = SendMailUtil.send(email, "Đăng kí tài khoản itsontran.com", message, "tranhuuhongson@gmail.com",
				"tranhuuhongSon1998@");
		if (check == false) {
			RequestDispatcher rd = request.getRequestDispatcher("/auth/register.jsp?msg=3");/* Lỗi về gởi mail */
			rd.forward(request, response);
			return;
		}

		User user = new User(0, username, password, fullname, 0, new UserPermissions(3, null));
		if (userDAO.addItem(user) > 0) {
			response.sendRedirect(
					request.getContextPath() + "/register-successful"); /* đăng kí thành công vào mail để check */
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/auth/register.jsp?msg=0"); /* Đăng kí thất bại */
			rd.forward(request, response);
		}
	}

}
