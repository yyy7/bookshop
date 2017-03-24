package com.ddbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BookshopFilter implements Filter{

	public void destroy() {
		System.out.println("filter destroy");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("filter start");
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		//HttpSession session = request.getSession();
		
		//��������
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		
		
		//��ȡ��ǰ��ҳ��
		String uri = request.getRequestURI();
		String s[] = uri.split("/");
		String page = s[s.length-1];
		System.out.println("���������ص��ĵ�ǰ��ַ:"+page);
		
		//����ǵ�¼ҳ��   ע��ҳ��   ��¼��֤ҳ��  �Ͳ�Ӧ��ִ����ת�Ĺ���
		/*if(page.equals("login.jsp")||page.equals("login")||page.equals("register.jsp")||page.equals("register")||page.equals("registerAccount")||page.endsWith(".css")||page.endsWith(".js")||page.endsWith(".jpg")||page.endsWith(".gif")||page.endsWith(".png")||page.equals("code")||page.equals("detailsCart")){
			System.out.println("1111");
			chain.doFilter(request, response);//��������������洫 
			return;
		}
		
		//��֤�û��Ƿ��¼
		if(session.getAttribute("u_account") == null){
			session.setAttribute("page", uri);
			System.out.println("asdf");
		}
*/
		
		if(page.equals("index.jsp")||page.equals("about.jsp")||page.equals("cart.jsp")||page.equals("contact.jsp")||page.equals("specials.jsp")){
			/*chain.doFilter(request, response);//��������������洫 
			return;*/
			if(session.getAttribute("username") == null){
				session.setAttribute("page", uri);
				System.out.println("asdf");
			}
		}
		
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
