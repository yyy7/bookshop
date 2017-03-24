package com.ddbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.ddbs.action.RecommendBooks;
import com.ddbs.dao.imple.BookDaoImpl;
import com.ddbs.dao.imple.CartAction;
import com.ddbs.dao.imple.Login;
import com.ddbs.model.BookImages;
import com.ddbs.model.Books;
import com.ddbs.model.Cart;


//��֤�û��Ƿ��¼�ɹ�
public class LoginServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/html;charset=utf-8");
		HttpSession session = req.getSession();
		
		PrintWriter out = resp.getWriter();
		String u_account = req.getParameter("u_account");
		System.out.println(u_account);
		String u_password = req.getParameter("u_password");
		//System.out.println(u_password);
		String tag = req.getParameter("tag");
		System.out.println(tag);
		
		
		Login login = new Login();
		//������ʽ��¼��ʱ��  �任�ɱ���վ�˺�
		if(tag!="u_account"){
			u_account = login.getAccount(u_account, tag);
		}
		
		if(u_account.length()==4){
			System.out.println("����Ա��¼");
			session.setAttribute("manager", "manager");
		}
		
		
		//��ȡ��֤��
		String code = req.getParameter("code");
		
		Boolean check = login.check(u_account, u_password);
		
		String randomCode = (String)session.getAttribute("imagecheckcode");
		System.out.println(randomCode);
		
		Boolean check_code = login.compareCode(code, randomCode);
		
		
		
		if(!check_code){
			System.err.println("��֤�����");
			out.println("��֤�����");
		}
		
		if(check&&check_code){
			System.out.println("��¼�ɹ�");
			session.setAttribute("u_account", u_account);//�˺�
			CartAction cartAction = new CartAction();
			Float num = (float)(Math.round(cartAction.getNum(u_account)*100))/100;
			int s_num = cartAction.gets_Num(u_account);
			Vector<Cart> carts = cartAction.getData(u_account);
			session.setAttribute("carts", carts);//���ﳵ��Ϣ
			session.setAttribute("num", num);//���ﳵ��Ǯ��
			session.setAttribute("s_nums", s_num);//���ﳵ�е��ܼ���
			
			RecommendBooks recommendBooks = new RecommendBooks();
			Vector<BookImages> bImages = recommendBooks.recommendBooks(u_account, 3);
			session.setAttribute("bookImage", bImages);
			
			
			//��ȡbookimages�е�bid��Ӧ��ͼ����Ϣ
			Vector<Books> booksByBid = new Vector<Books>();
			BookDaoImpl bookDaoImpl  = new BookDaoImpl();
			
			Enumeration enumeration = bImages.elements();
			while(enumeration.hasMoreElements()){
				BookImages value = (BookImages)enumeration.nextElement();
				String b = value.getB_id();
				Books tempBooks = bookDaoImpl.CheckBookById(b);
				booksByBid.add(tempBooks);
			}
			/*for (BookImages bookImages : bImages) {
				String temp_bid = bookImages.getB_id();
				Books tempBooks = bookDaoImpl.CheckBookById(temp_bid);
				booksByBid.add(tempBooks);
			}*/
			session.setAttribute("booksByBIBid", booksByBid);
			
			String page = (String)session.getAttribute("page");
			out.println("1111"+page);
		}else{
			System.out.println("��¼ʧ��");
		}
	
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
