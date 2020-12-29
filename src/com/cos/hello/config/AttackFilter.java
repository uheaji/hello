package com.cos.hello.config;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AttackFilter implements Filter {

	// 2번째 순서 (마지막 순서)
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// joinProc 일때
		System.out.println("attackFilter");
		// post 요청만 받아서 처리!

		HttpServletRequest req = (HttpServletRequest) request;
		String method = req.getMethod();
		if (method.equals("POST")) {
			String username = request.getParameter("username");
			username = username.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
			System.out.println("username  : " + username);
			// username에 < > 들어오는 것을 방어
			// 만약에 꺽쇠가 들어오면 전부 &lt; &gt;
			// 다시 필터 타게 할 예정
		}

		System.out.println("공격 방어 필터 실행");
		chain.doFilter(request, response);

	}

}
