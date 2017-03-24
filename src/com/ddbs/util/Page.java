package com.ddbs.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Page {
	private int pageSize;//ÿҳ��ʾ�ļ�¼��
	private int pageCount;//��ҳ��
	private int curPage;//��ǰҳ
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	//������ҳ��
	public void setPageCount(ResultSet rs) {
		try {
			rs.last();//���α��ƶ������һ��
			int lastrow=rs.getRow();//��ʾ��ǰ�кţ���һ��ʼ
			if(lastrow%pageSize==0){
				pageCount=lastrow/pageSize;
			}else{
				pageCount=lastrow/pageSize+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getPageCount() {
		return pageCount;
	}
	
	//����Ҫ��ʾ��ҳ
	public void setCurPage(int row) {
		if (row<=1)
			curPage=1;
		else if(row>=pageCount){
			curPage=pageCount;
		}else{
			curPage=row;
		}
	}
	
	public int getCurPage() {
		return curPage;
	}
	
	//����ҳ�����ý����
	public ResultSet setResultset(ResultSet rs){
		try {
			//System.out.println("����ҳ�����ý����:"+(curPage-1)*pageSize+1+"curPage,,pageSize::"+curPage+"--"+pageSize);
			rs.absolute((curPage-1)*pageSize+1);//���α��ƶ���ָ����
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
