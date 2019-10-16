package util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

public class MessageUtil {
	public static void getMessage(HttpServletRequest request, JspWriter out) throws IOException {
		if (request.getParameter("msg") != null) {
			int msg = 0;
			try {
				msg = Integer.parseInt(request.getParameter("msg"));
			} catch (NumberFormatException e) {
				out.print("<div class='alert alert-danger message'>Have error.</div>");
				return;
			}
			switch (msg) {
			case 0:
				out.print("<div class='alert alert-danger message'>Have error.</div>");
				break;
			case 1:
				out.print("<div class='alert alert-success message'>Add succesful.</div>");
				break;
			case 2:
				out.print("<div class='alert alert-success message'>Edit succesful.</div>");
				break;
			case 3:
				out.print("<div class='alert alert-warning message'>Delete succesful.</div>");
				break;
			case 5:
				out.print("<div class='alert alert-danger message'>Admin can't delete.</div>");
				break;
			default:
				out.print("<div class='alert alert-danger message'>Have error.</div>");
				break;
			}
		}
	}

	public static void getErrorMessage(HttpServletRequest request, JspWriter out) throws IOException {
		if (request.getParameter("msg") != null) {
			int msg = 0;
			try {
				msg = Integer.parseInt(request.getParameter("msg"));
			} catch (NumberFormatException e) {
				return;
			}
			if (msg == 0)
				out.print("<span style='color: red;  padding-left: 10px'>Có lỗi xảy ra</span>");
		}
	}
}
