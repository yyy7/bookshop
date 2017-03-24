package com.ddbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.el.ValueExpressionLiteral;

import com.ddbs.dao.imple.CartAction;
import com.ddbs.model.Cart;

public class CartServlet extends HttpServlet{
	//���¹��ﳵ����
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/html;charset=utf-8");
		HttpSession session = req.getSession();
		CartAction cartAction = new CartAction();
		PrintWriter out = resp.getWriter();
		
		//System.out.println("asasd");
		//ͼ������
		String number = req.getParameter("number");
		int num = Integer.parseInt(number);
		System.out.println("�༭���ͼ������"+num);
		
		//ͼ����
		String b_id = req.getParameter("b_id");
		System.out.println(b_id);
		
		//ͼ��ԭ��
		String price = req.getParameter("price");
		Float p = Float.parseFloat(price);
		System.out.println(p);
		
		//ͼ���ۺ��
		String dis_price = req.getParameter("dis_price");
		Float dis = Float.parseFloat(dis_price);
		System.out.println(dis);
		
		//���ﳵ��ԭ���� ����
		Vector<Cart> carts = (Vector<Cart>)session.getAttribute("carts");
		Enumeration enu = carts.elements();
		
		Vector<Cart> newCarts = new Vector<Cart>();

		float sum = 0;
		int s_num = 0;
		//�޸�session��carts��ֵ  �������ݿ��е�ֵ
		while(enu.hasMoreElements()){
			Cart value = (Cart)enu.nextElement();
			if(b_id.equals(value.getB_id())){
				value.setB_nums(num);
				value.setB_sumprice(num*p);
				value.setB_sumdiscountprice(num*dis);
			}
			sum = sum+value.getB_sumdiscountprice();
			newCarts.add(value);
			s_num += value.getB_nums();
		}
		
		session.setAttribute("carts", newCarts);
		session.setAttribute("num", sum);
		session.setAttribute("s_nums", s_num);
		out.println("okay");
		//session.invalidate();
	}
}
