package org.owl.examples;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿은 데이터 처리만 하고 페이지 출력은 JSP와 같은 뷰 기술로 넘긴다.
 * 
 * @author Jacob
 */
@WebServlet("/helloMvc")
public class HelloMvcServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 데이터를 만든다.
		String name = "김솔이";

		// 데이터를 request에 태운다.
		request.setAttribute("name", name);

		// /WEB-INF/jsp/hello.jsp로 forward 한다.
		request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(request,
				response);
	}
}