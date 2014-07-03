package webTest.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webTest.dataConnection.DBManagementDAO;
import webTest.entity.User;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final DBManagementDAO db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        db = new DBManagementDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String email = "";
		int age = 0;
		long telephone = 0;
		
		
		if(username != null && !username.equals("")) {
			User user = db.getUserDAO().getUserByUsername(username);
			email = user.getEmail();
			age = user.getAge();
			telephone = user.getTelephone();
		} else {
			username = "";
		}
		
		request.setAttribute("username", username);
		request.setAttribute("email", email);
		request.setAttribute("age", age);
		request.setAttribute("telephone", telephone);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/view/profile.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String age = request.getParameter("age");
		String telephone = request.getParameter("telephone");
		if(username != null && !username.equals("")) {
			User user = db.getUserDAO().getUserByUsername(username);
			user.setAge(Integer.parseInt(age));
			user.setTelephone(Long.parseLong(telephone));
		}
		
		request.setAttribute("username", username);
		request.setAttribute("email", email);
		request.setAttribute("age", age);
		request.setAttribute("telephone", telephone);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/view/profile.jsp");
		rd.forward(request, response);
	}

}
