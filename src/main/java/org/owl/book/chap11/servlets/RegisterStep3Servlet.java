package org.owl.book.chap11.servlets;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owl.book.chap11.dao.DaoException;
import org.owl.book.chap11.dao.Member;
import org.owl.book.chap11.dao.MemberDao;
import org.owl.book.chap11.dao.MemberDaoImpl;

@WebServlet("register/step3")
public class RegisterStep3Servlet extends HttpServlet {

	MemberDao memberDao = null;

	Logger logger = LogManager.getLogger();

	/**
	 * memberDao를 초기화 한다.
	 */
	@Override
	public void init() throws ServletException {
		try {
			memberDao = new MemberDaoImpl();
		} catch (NamingException e) {
			logger.error(e.getMessage());
			throw new ServletException(e.getCause());
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");

		// 회원정보 저장
		Member member = new Member(email, password, name);
		try {
			memberDao.insert(member);
			logger.debug("회원 정보를 저장했습니다. {}", member);
			request.getRequestDispatcher("/WEB-INF/jsp/register/step3.jsp")
					.forward(request, response);
		} catch (DaoException e) {
			logger.error(e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/register/step2.jsp")
					.forward(request, response);
		}
	}
}