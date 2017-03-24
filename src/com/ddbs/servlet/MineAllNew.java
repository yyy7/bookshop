package com.ddbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ddbs.dao.imple.MineInfo;
import com.ddbs.dao.imple.MineOrder;
import com.ddbs.dao.imple.MineSecurity;
import com.ddbs.dao.imple.MyAddress;
import com.ddbs.model.UserAddress;
import com.ddbs.model.Users;

/**
 * Servlet implementation class MyInfoNew
 */
@WebServlet("/MineAllNew")
public class MineAllNew extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String uAccount = session.getAttribute("u_account").toString();
		String requestUrl = request.getHeader("referer");
		System.out.println(requestUrl);
		System.out.println("account:" + uAccount);
		// �жϴ��Ǹ�ҳ������ģ��Ӷ����в�ͬ�Ĵ���
		// �������� de �޸�
		if (requestUrl.equals("http://localhost:8080/bookshop/myInfo.jsp")) {
			String u_account = request.getParameter("u_account");
			String u_name = request.getParameter("u_name");
			String u_phone = request.getParameter("u_phone");
			String u_mail = request.getParameter("u_mail");

			System.out.println("u_name" + u_name);
			System.out.println("u_mail" + u_mail);
			Users temp = new Users(uAccount, u_name, "", u_phone, u_mail);

			MineInfo mineInfo = new MineInfo();
			int result = mineInfo.saveInfo(temp);
			System.out.println("result:" + result);
			if (result > 0) {
				// out.print("<script
				// type='text/javascript'>alert('�޸ĳɹ���')</script>");
				out.print("�޸ĳɹ���");
			} else {
				out.print("�޸�ʧ�ܣ������ԣ�");
			}
			response.setHeader("refresh", "1,MyInfoOld");

		}
		// �˻��밲ȫ
		if (requestUrl.equals("http://localhost:8080/bookshop/account.jsp")) {
			String firstPw = request.getParameter("firstPw").toString();
			String secondPw = request.getParameter("secondPw").toString();
			int result = 0;
			if (firstPw.equals(secondPw)) {
				MineSecurity mineSecurity = new MineSecurity();
				Users user = new Users();
				user.setU_account(uAccount);
				user.setU_password(firstPw);
				result = mineSecurity.saveSecurity(user);
			}
			if (result > 0) {
				out.print("�޸�����ɹ���");
			} else {
				out.print("�޸�����ʧ�ܣ������ԣ�");
			}
			response.setHeader("refresh", "1,MySecurityOld");
		}
		// �ջ���ַ����
		if (requestUrl.equals("http://localhost:8080/bookshop/address.jsp")) {
			String o_receiver = request.getParameter("o_receiver");
			String o_address = request.getParameter("o_address");
			String o_phone = request.getParameter("o_phone");
			String defau = request.getParameter("default");
			int isDefault = 0;
			if (defau == null) {
				isDefault = 0;
			} else {
				isDefault = 1;
			}
			String uAddrId = request.getParameter("uAddrId").toString();
			System.out.println("oReceiver:" + o_receiver);
			System.out.println("address" + o_address);
			System.out.println("o_phone" + o_phone);
			System.out.println("uAddrId" + uAddrId);
			UserAddress userAddress = new UserAddress(uAccount, o_address, o_phone, isDefault, o_receiver, uAddrId);
			int maxCountAddr = 5;
			MyAddress myAddress = new MyAddress();
			// ����һ���ջ���ַ
			if (request.getParameter("add") != null) {

				System.out.println("����==================");
				int count = Integer.parseInt(session.getAttribute("numOfAddress").toString());
				System.out.println("countAddr:" + count);
				if (count >= maxCountAddr) {
					out.print("�ջ���ַ��������������ɾ�������Ӵ��");
				} else {
					int result = myAddress.addAddr(userAddress);
					if (result > 0) {
						out.print("�����ջ���ַ�ɹ���");
					} else {
						out.print("�����ջ���ַʧ�ܣ������ԣ�");
					}
				}
				response.setHeader("refresh", "1,MyAddressOld");
			}

			// �޸��ջ���ַ
			// System.out.println(request.getParameter("update").toString());
			if (request.getParameter("update") != null) {
				System.out.println("�޸�==================");
				int result = myAddress.updateInfo(userAddress);
				if (result > 0) {
					out.print("�޸��ջ���ַ�ɹ���");
				} else {
					out.print("�޸��ջ���ַʧ�ܣ������ԣ�");
				}
				response.setHeader("refresh", "1,MyAddressOld");
			}
			// ɾ���ջ���ַ
			if (request.getParameter("delete") != null) {
				System.out.println("ɾ��==================");
				int result = myAddress.deleteAddr(uAddrId);
				if (result > 0) {
					out.print("ɾ���ջ���ַ�ɹ���");
				} else {
					out.print("ɾ���ջ���ַʧ�ܣ������ԣ�");
				}
				response.setHeader("refresh", "1,MyAddressOld");
			}
		}
		// ����ɾ��
		if (requestUrl.equals("http://localhost:8080/bookshop/allorder.jsp")) {
			String o_id = request.getParameter("o_id").toString();

			MineOrder mineOrder = new MineOrder();
			int result = mineOrder.deleteOrder(o_id);
			if (result > 0) {
				out.print("ɾ�������ɹ���");
			} else {
				out.print("ɾ������ʧ�ܣ������ԣ�");
			}
			response.setHeader("refresh", "1,MyOrders");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
