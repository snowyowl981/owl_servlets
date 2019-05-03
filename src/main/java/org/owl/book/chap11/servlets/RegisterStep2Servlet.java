package org.owl.book.chap11.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/register/step2")
public class RegisterStep2Servlet extends HttpServlet {

	Logger logger = LogManager.getLogger();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String agree = request.getParameter("agree");

		if (!"true".equals(agree)) {// 동의하지 않으면 step1으로 돌아감
			logger.debug("약관에 동의하지 않았습니다.");
			request.getRequestDispatcher("/WEB-INF/jsp/register/step1.jsp")
					.forward(request, response);
		} else { // 동의하면 step2로 forward
			request.getRequestDispatcher("/WEB-INF/jsp/register/step2.jsp")
					.forward(request, response);
		}
	}
}