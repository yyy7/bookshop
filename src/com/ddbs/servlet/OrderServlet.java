package com.ddbs.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ddbs.dao.imple.OrderAction;
import com.ddbs.model.Cart;
import com.ddbs.model.UserAddress;

public class OrderServlet extends HttpServlet{
	//����ҳ��չʾ
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		Vector<Cart> carts = (Vector<Cart>)session.getAttribute("carts");
		Enumeration enu = carts.elements();
		Vector<Cart> order = new Vector<Cart>();
		
		float order_sum = 0;
		while(enu.hasMoreElements()){
			Cart value = (Cart)enu.nextElement();
			String b_id = req.getParameter(value.getB_id());
			//System.out.println(b_id);
			//System.out.println(b_id);//ѡ�е�Ϊon  û��ѡ�е�Ϊnull
			if(b_id != null){
				order.addElement(value);
				order_sum += value.getB_sumdiscountprice();
			}
		}
		order_sum = (float)(Math.round(order_sum*100))/100;

		
		//�����ݿ���ȡ��Ĭ�ϵ�ַ
		String u_account = (String)session.getAttribute("u_account");
		OrderAction oAction  = new OrderAction();
		Vector<UserAddress> addresses = oAction.getAddress(u_account);
		req.setAttribute("address", addresses);
		session.setAttribute("order", order);
		req.setAttribute("order_sum", order_sum);
		
		//�����ݿ���ȡ�����еĵ�ַ
		Vector<UserAddress> allAddress = oAction.getAllAddress(u_account);
		req.setAttribute("allAddress", allAddress);
		
		req.getRequestDispatcher("order.jsp").forward(req, resp);
	}
}
