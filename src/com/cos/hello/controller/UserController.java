package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.ServletException;
// javax로 시작하는 패키지는 톰캣이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.model.Users;

public class UserController extends HttpServlet {

	// req와 res는 톰캣이 만들어 준다.(클라이언트의 요청이 있을때 마다)
	// req는 BufferedReader 할 수 있는 ByteStream
	// resp는 BufferedWriter 할 수 있는 ByteStream

	// http://localhost:8000/hello/front
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("comment process 요청");
		System.out.println("UserController 실행됨");

		String gubun = req.getParameter("gubun"); // /hello/front
		System.out.println(gubun);
		// http://localhost:8000/hello/user?gubun=login
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번 더 request
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번 더 request
		} else if (gubun.equals("selectOne")) { // 유저정보 보기
			// 인증이 필요한 페이지
			HttpSession session = req.getSession();
			if (session.getAttribute("sessionUser") != null) { // 인증끝
				Users user = (Users)session.getAttribute("sessionUser"); 
				System.out.println("인증되었습니다.");
				System.out.println(user);
			} else {
				System.out.println("인증되지 않았습니다.");
			}

			
			resp.sendRedirect("user/selectOne.jsp"); // 한번 더 request
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp"); // 한번 더 request
		} else if (gubun.equals("joinProc")) { // 회원가입 수행해줘
			
			// 데이터 원형 username=ssar&password=1234&email=ssar@natem.com
			// 1번 form의 input태그에 있는 3가지 값 username, password, email!! 받기!
			// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음.
			// 단 post방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
			String username = req.getParameter("username");
			String email = req.getParameter("email");
			String password = req.getParameter("password");

 
			System.out.println("=================================joinProc Start=============================");
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			System.out.println("=================================joinProc End=============================");

			// 2번 DB에 연결해서 3가지 값을 INSERT 하기
			// 생략
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			resp.sendRedirect("index.jsp");
 
		} else if(gubun.equals("loginProc")) {
			// 1번 
			String username = req.getParameter("username");
			String password = req.getParameter("password");

			System.out.println("=================================loginProc Start=============================");
			System.out.println(username);
			System.out.println(password);
			System.out.println("=================================loginProc End=============================");

			// 2번 데이터베이스 값이 있는 select 해서 확인
			// 생략
			Users user = Users.builder()
					.id(1)
					.username(username)
					.build();
					
			// 3번
			HttpSession session = req.getSession();
			// session에는 사용자 패스워드 절대 넣지 않기.
			session.setAttribute("sessionUser", "9998");
			// 모든 응답에는 jSessionId가 쿠키로 추가된다.

			// 4번 index.jsp 페이지로 이동
			resp.sendRedirect("index.jsp");

		}
	}
}
