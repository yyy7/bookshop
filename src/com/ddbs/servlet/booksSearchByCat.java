package com.ddbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ddbs.dao.imple.BookDaoImpl;
import com.ddbs.model.Books;
import com.ddbs.util.Page;

@WebServlet(name="booksSearchByCat",urlPatterns={"/booksSearchByCat","/booksSearchByCat2"})
public class booksSearchByCat extends HttpServlet{
	BookDaoImpl bdi=new BookDaoImpl();
	Vector<Books> vec=null;
	Map map=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri=request.getRequestURI();
		//System.out.println("�����ַ��"+uri);	
		PrintWriter out=response.getWriter();
		String category=request.getParameter("category");
		//System.out.println("catgory::"+category);
		
		int curPage=1;//Ĭ�ϵ�ǰҳ��Ϊ1
		int pageSize;
		//int pageSize=10;//Ĭ��ÿҳ��ʾ10����¼
		String tempCurPage=request.getParameter("curPage");
		String tempPageSize=request.getParameter("pageSize");
		System.out.println("tempPageSize::"+tempPageSize);
		if(tempPageSize==null ||"".equals(tempPageSize)){
			pageSize=8;//Ĭ��9��
		}else{
			int tempPageSize2=Integer.parseInt(tempPageSize);
			request.getSession().setAttribute("pageSize",tempPageSize2);
			pageSize=Integer.parseInt(String.valueOf(request.getSession().getAttribute("pageSize")));
		}
		
		if(tempCurPage!=null){
			curPage=Integer.parseInt(tempCurPage);
		}
		map=bdi.CheckBookByCat(category,curPage,pageSize);
		vec=(Vector<Books>) map.get("vec");
		Page page=(Page) map.get("page");
		//System.out.println("��ǰҳ����"+curPage);
		//System.out.println("page����ҳ����"+page.getPageCount());
		if(vec!=null){
			request.getSession().setAttribute("booksList",vec);
			request.getSession().setAttribute("curPage", page.getCurPage());//����ʾҳ���ݵ�ǰҳҳ��
	  		request.getSession().setAttribute("pageCount",page.getPageCount());//����ʾҳ������ҳ��
			if(uri.equals("/bookshop/booksSearchByCat")){
				String flag=(String) request.getSession().getAttribute("flag");
				if(flag!=null){
					response.sendRedirect("booksCart.jsp");
					request.getSession().setAttribute("flag", null);
				}
				out.write("booksCart.jsp");
			}
			if(uri.equals("/bookshop/booksSearchByCat2")){
				out.write("category1.jsp");
			}
		}else{
			out.write("fail");
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
