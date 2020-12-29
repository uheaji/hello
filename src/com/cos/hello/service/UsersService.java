package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;

public class UsersService {

	public void 회원가입(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JoinDto joinDto = (JoinDto) request.getAttribute("joinDto");

		UsersDao usersDao = new UsersDao(); // getInstance방식으로 바꾸기(싱글톤패턴)
		int result = usersDao.insert(joinDto);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			response.sendRedirect("auth/login.jsp");
		} else {
			response.sendRedirect("auth/join.jsp");
		}
	}

	public void 로그인(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		LoginDto loginDto = (LoginDto) request.getAttribute("dto");
		
		UsersDao usersDao = new UsersDao();
		Users userEntity = usersDao.login(loginDto);

		if (userEntity != null) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionUser", userEntity);
			// 한글처리를 위해 resp객체를 건드린다.
			// MIME 타입
			// HTTP Header에 Content-type
			Script.href(response, "index.jsp", "로그인 성공");
		} else {		
			Script.back(response,"로그인 실패");
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
	
	public void 회원수정(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		Users user = Users.builder()
				.id(id)
				.password(password)
				.email(email)
				.build();

		UsersDao usersDao = new UsersDao(); // getInstance방식으로 바꾸기(싱글톤패턴)
		int result = usersDao.update(user);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			response.sendRedirect("index.jsp");
		} else {
			// 이전 페이지로 이동
			response.sendRedirect("user?gubun=updateOne.jsp");
		}
		}
	
	public void 회원삭제(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		UsersDao usersDao = new UsersDao(); // getInstance방식으로 바꾸기(싱글톤패턴)
		int result = usersDao.delete(id);
		System.out.println("회원삭제");

		if (result == 1) {
			HttpSession session = request.getSession();
			session.invalidate(); // 세션무효화
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			response.sendRedirect("index.jsp");
		} else {
			// 이전 페이지로 이동
			response.sendRedirect("user?gubun=selectOne.jsp");
		}
		}
	}

