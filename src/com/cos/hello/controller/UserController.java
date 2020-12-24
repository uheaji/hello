package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;
import com.cos.hello.service.UsersService;
import com.cos.hello.service.UsersService;

import dao.UsersDao;

// 디스패쳐의 역할 = 분기 = 필요한 view를 응답해주는 것
public class UserController extends HttpServlet {
	// req와 res는 톰켓이 만들어줍니다. (클라이언트의 요청이 있을 때 마다)
	// req는 BufferedReader 할 수 있는 ByteStream
	// res는 BufferedWriter 할 수 있는 ByteStream
	// http://localhost:8000/hello/front

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	// post요청은 FORM태그 만들고 요청 = INSERT, DELETE, UPDATE
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
		System.out.println("comment post 요청");
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserController 실행됨");
		String gubun = request.getParameter("gubun"); // /hello/front
		System.out.println(gubun);
		route(gubun, request, response);
	}

	private void route(String gubun, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		UsersService usersService = new UsersService();
		
		if (gubun.equals("login")) {
			response.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			response.sendRedirect("auth/join.jsp");
		} else if (gubun.equals("selectOne")) { // 유저정보
			usersService.유저정보보기(request, response);
		} else if (gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(request, response);
		} else if (gubun.equals("joinProc")) { // 회원가입 수행해줘
			usersService.회원가입(request, response);
			// 회원정보수정하기
			
			// 데이터 원형 username=ssar&password=1234&email=ssar@nate.com
			// 1번 form의 input태그에 있는 3가지 값 username, password, email!! 받기!
			// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음.
			// 단 post방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
		
			System.out.println("=================================joinProc Start=============================");
//			System.out.println(username);
//			System.out.println(password);
//			System.out.println(email);
			System.out.println("=================================joinProc End=============================");
			
			// 2번 DB에 연결해서 3가지 값을 INSERT 하기
			

		} else if (gubun.equals("loginProc")) {
			// SELECT id, username, email FROM users WHERE username = ? AND password = ?
			// DAO의 함수명 : login() return을 Users오브젝트를 리턴
			// 정상 : 세션에 Users 오브젝트 담고 index.jsp
			// 비정상 : login.jsp
			usersService.로그인(request, response);
		
			System.out.println("=================================loginProc Start=============================");
//			System.out.println(username);
//			System.out.println(password);
			System.out.println("=================================loginProc End=============================");

		}
	}
}
