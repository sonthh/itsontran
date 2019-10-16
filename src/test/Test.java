package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.SendMailUtil;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static void main(String[] args) {
		System.out.println("Test.main()");
	}
    
    
    public Test() {
        //super();
    	System.out.println("Test.Test()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SendMailUtil.send("sonthh.vinaenter@gmail.com", "Test gởi mail", "hahaha" + request.getContextPath() + "/register", "tranhuuhongson@gmail.com", "tranhuuhongSon1998@");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
