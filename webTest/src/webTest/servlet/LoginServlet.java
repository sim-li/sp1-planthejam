package webTest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webTest.dataConnection.UserDAO;
import webTest.entity.User;

import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final UserDAO db;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		db = new UserDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String err = request.getParameter("error");
		String msg = request.getParameter("logout");
		if (err != null) {
			request.setAttribute("error", "Invalid username and password!");
		}
		if (msg != null) {
			request.setAttribute("msg", "You've been logged out successfully.");
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/view/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = db.getUserByUsername(username);
		
		
		if(user == null || !password.equals(user.getPassword()) ) {
			response.sendRedirect("login?error");
		}
	}

}
