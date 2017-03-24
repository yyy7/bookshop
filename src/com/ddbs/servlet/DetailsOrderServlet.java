package com.ddbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.omg.CORBA.INTERNAL;

import com.ddbs.dao.imple.BookDaoImpl;
import com.ddbs.dao.imple.DetailsInCart;
import com.ddbs.dao.imple.OrderAction;
import com.ddbs.model.Cart;
import com.ddbs.model.UserAddress;

public class DetailsOrderServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/html;charset=utf-8");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		//System.out.println("������ʼ");
		
		String u_account = (String)session.getAttribute("u_account");
		if(u_account==null){
			req.setAttribute("fail", "fail");
//			out.print("<script type='text/javascript'>alert('����û�е�¼,��Ϊ����ת����¼ҳ��')</script>");
//			retut
		}else{
			String n = req.getParameter("bookNum");
			String b_id = req.getParameter("b_id");
			System.out.println(n+","+b_id);
			DetailsInCart detailsInCart = new DetailsInCart();
			Cart cart = detailsInCart.inCart(u_account, b_id, Integer.parseInt(n));
			
			Vector<Cart> order = new Vector<Cart>();
			order.add(cart);
			session.setAttribute("order", order);
			
			System.out.println("���붩���ɹ�");
			
			//�����ݿ���ȡ�����еĵ�ַ
			OrderAction oAction = new OrderAction();
			Vector<UserAddress> allAddress = oAction.getAllAddress(u_account);
			req.setAttribute("allAddress", allAddress);
			
			//�����ݿ���ȡ��Ĭ�ϵ�ַ
			Vector<UserAddress> addresses = oAction.getAddress(u_account);
			req.setAttribute("address", addresses);
			req.setAttribute("order_sum", cart.getB_sumdiscountprice());
			
		}
		req.getRequestDispatcher("order.jsp").forward(req, resp);
	}
}
