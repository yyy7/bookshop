package com.ddbs.dao.imple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ddbs.dao.LoginDao;
import com.ddbs.db.DbConn;
import com.sun.xml.internal.bind.v2.model.core.ID;

import sun.security.action.GetBooleanAction;

//��¼��֤
public class Login implements LoginDao{
	
	//��֤�û�����ʲô���͵��˺ŵ�¼��   Ȼ��ȶ����ݿ��е�����  �����Ƿ���ȷ
	DbConn dbConn = new DbConn();
	Connection conn = dbConn.getConn();
	public boolean checkUser(String u_account,String u_password,String tag){
		try {
			Statement stm = conn.createStatement();
			String sql = "select u_password from users where "+tag+"=\""+u_account+"\"";
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString(1));
				if(u_password.equals(rs.getString(1))){
					//System.out.println("��¼�ɹ�");
					return true;
				}else{
					//System.out.println("��¼ʧ��");
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
	//��֤���б���
		public boolean compareCode(String inputCode, String randomCode) {
			boolean flag = false;
			char[] inputChar = inputCode.toCharArray();
			char[] codeChar = randomCode.toCharArray();
			// �ж������Ƿ���ͬ
			if (inputChar.length == codeChar.length) {
				for (int i = 0; i < codeChar.length; i++) {
					// ���Զ����ɵ���֤���еĴ�д��ĸת����Сд���ٱȽ�
					if (codeChar[i] <= 90 && codeChar[i] >= 65) {
//						System.out.println("1111111111");
						int codeInt = (int) codeChar[i];
						int codeResu = codeInt + 32;// ת����Сд
//						 System.out.println("codere"+codeResu);
						int inputInt = (int) inputChar[i];
//						 System.out.println("input"+inputInt);
						if (inputInt <= 90 && inputInt >= 65) {
//							System.out.println("333333333");
							if (inputInt != codeInt) {
//								System.out.println("da�ȶ�shibai");
								flag = false;
								break;
							} else {
//								System.out.println("da�ȶԳɹ�");
								flag = true;
							}
						} else {
							if (inputInt != codeResu) {
//								System.out.println("da�ȶ�shibai");
								flag = false;
								break;
							} else {
//								System.out.println("da�ȶԳɹ�");
								flag = true;
							}
						}

					} else {
//						System.out.println("22222222");
						int codeInt = (int) codeChar[i];
						int inputInt = (int) inputChar[i];
						if (codeInt != inputInt) {
							System.out.println("xi�ȶ�shibai");
							flag = false;
							break;
						} else {
//							System.out.println("xi�ȶԳɹ�");
							flag = true;
						}
					}
				}
			}
//			System.out.println("flag:"+flag);
			return flag;
		}
		
		//�û�ʹ��������ʽ��¼ʱ  ��ȡ�û�����վ���˺�
		public String getAccount(String u_account,String tag){
			String sql = "select u_account from users where "+tag+"=\""+u_account+"\"";
			try {
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()){
					return rs.getString(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println(sql);
			return null;
		}
		
		//����Ҫtag��־����¼ ��Ϊ����ķ����Ѿ�ת������u_account���ֶ���
		public boolean check(String u_account,String u_password){
			try {
				Statement stm = conn.createStatement();
				String sql = "select u_password from users where u_account="+"\""+u_account+"\"";
				System.out.println(sql);
				ResultSet rs = stm.executeQuery(sql);
				while(rs.next()){
					System.out.println(rs.getString(1));
					if(u_password.equals(rs.getString(1))){
						//System.out.println("��¼�ɹ�");
						return true;
					}else{
						//System.out.println("��¼ʧ��");
						return false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			
			return false;
		}

}
