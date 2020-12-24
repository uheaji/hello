package com.cos.hello.service;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.model.Users;

import dao.UsersDao;

public class UsersService {

	public void 회원가입(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Users user = Users.builder().username(username).password(password).email(email).build();

		UsersDao usersDao = new UsersDao(); // getInstance방식으로 바꾸기(싱글톤패턴)
		int result = usersDao.insert(user);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			response.sendRedirect("auth/login.jsp");
		} else {
			response.sendRedirect("auth/join.jsp");
		}
	}

	public void 로그인(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 값을 받기
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Users user = Users.builder().username(username).password(password).build();

		UsersDao usersDao = new UsersDao();
		Users userEntity = usersDao.login(user);

		if (userEntity != null) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionUser", userEntity);
			response.sendRedirect("index.jsp");
		} else {
			response.sendRedirect("auth/login.jsp");
		}
	}

	public void 유저정보보기(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			request.setAttribute("user", userEntity);
			RequestDispatcher dis = request.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(request, response);
		} else {
			response.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 유저정보수정페이지(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			request.setAttribute("user", userEntity);
			RequestDispatcher dis = request.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(request, response);
		} else {
			response.sendRedirect("auth/login.jsp");
		}
	}
}
