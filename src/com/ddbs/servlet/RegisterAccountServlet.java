package com.ddbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ddbs.dao.imple.Register;
import com.ddbs.model.Users;

//����û����Ƿ��ظ�
public class RegisterAccountServlet extends HttpServlet{
	//ע���ʱ��  �û����Ƿ���ȷ  ajax����
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/html;charset=utf-8");
		
		
		PrintWriter out = resp.getWriter();
		
		String u_account = req.getParameter("u_account");
		System.out.println(u_account);
		
		Register register = new Register();
		Boolean check = register.checkAccount(u_account);
		if(check){
			out.println("okay");
		}
	
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
