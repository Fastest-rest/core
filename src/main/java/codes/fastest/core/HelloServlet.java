package codes.fastest.core;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "simpleServlet", urlPatterns = { "/register" })
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = -9170879535311153308L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String body = req.getReader().lines().collect(Collectors.joining());
		
		System.out.println(body);
		
		resp.setContentType("application/json;charset=UTF-8");

        ServletOutputStream out = resp.getOutputStream();

        out.print("TRUE");
		
	}
	
}