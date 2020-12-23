package com.cos.hello.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.model.Users;

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
		if (gubun.equals("login")) {
			response.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			response.sendRedirect("auth/join.jsp");
		} else if (gubun.equals("selectOne")) { // 유저정보
			// 인증이 필요한 페이지
			String result;
			HttpSession session = request.getSession();
			if (session.getAttribute("sessionUser") != null) {
				Users user = (Users) session.getAttribute("sessionUser");
				result = "인증되었습니다.";
				System.out.println("인증되었습니다.");
				System.out.println(user);
			} else {
				result = "인증되지않았습니다.";
				System.out.println("인증되지 않았습니다.");
			}

			request.setAttribute("result", result);
			RequestDispatcher dis = request.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(request, response);

		} else if (gubun.equals("updateOne")) {

			response.sendRedirect("user/updateOne.jsp");

		} else if (gubun.equals("joinProc")) { // 회원가입 수행해줘

			// 데이터 원형 username=ssar&password=1234&email=ssar@natem.com

			// 1번 form의 input태그에 있는 3가지 값 username, password, email!! 받기!

			// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음.

			// 단 post방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.

			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			System.out.println("=================================joinProc Start=============================");
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			System.out.println("=================================joinProc End=============================");
			// 2번 DB에 연결해서 3가지 값을 INSERT 하기
			// 생략
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			response.sendRedirect("index.jsp");

		} else if (gubun.equals("loginProc")) {

			// 1번

			String username = request.getParameter("username");

			String password = request.getParameter("password");

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

			HttpSession session = request.getSession();

			// session에는 사용자 패스워드 절대 넣지 않기

			session.setAttribute("sessionUser", user);

			// 모든 응답에는 jSessiontId가 쿠키로 추가된다.

			// 4번 index.jsp 페이지로 이동

			response.sendRedirect("index.jsp");

		}

	}

}
