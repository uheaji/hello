package com.cos.hello.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	
	public static void back(HttpServletResponse response,  String msg) throws IOException {
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("history.back();");
		out.println("</script>");
		out.flush();
	}
	
	public static void href(HttpServletResponse response, String url, String msg) throws IOException {
//		response.setHeader("content-type", "text/html;charset=utf-8");
//		response.setContentType("text/html;charset=utf-8"); 필터
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("location.href = '"+url+"';");
		out.println("</script>");
		out.flush();
	}

}
