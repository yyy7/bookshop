package com.ddbs.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.TreeMap;
import com.ddbs.db.DbConn;
import com.ddbs.model.BookImages;

public class RecommendBooks {
	DbConn db = new DbConn();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection connection = db.getConn();
	String[] names = null; // names�������������userne�ֶεĸ���ֵ
	int columnCount;// ��ȥu_account�ֶκ���ֶ�����
	int userAmount = 0;

	// ���ر��Ƽ����ĸ��û���������ɵļ���
	public Vector<BookImages> recommendBooks(String name, int recommendCount) {
		// ����saveUserDataToMap�������ص�map�������ݿ��е�����ȡ�����ŵ�map��
		HashMap<String, int[]> resultMap = dbAllDataToMap();
		String u_account = name;
//		System.out.println("uaccount:" + u_account);
//		System.out.println("columnCount" + columnCount);
		// ��ȡ���˲���name֮���u_account;
		String[] otherKeys = new String[userAmount - 1];
		// ָ�����ֵ��û�����
		int[] nameData = null;
		if (resultMap.get(u_account) != null) {
			nameData = resultMap.get(u_account);
//			System.out.println("���û�����");
		}
		if (resultMap.get(u_account) == null) {
//			System.out.println("���û�������");
			return null;
		}
		// �����û�������
		int[] otherData = new int[columnCount];
		// ��ָ���û��������û��ռ�н�
		Map<String, Double> angles = new TreeMap<String, Double>();
		// �Ѳ���ָ���û��������û���ȡ�����浽otherKeys
		int n = 0;
		for (Entry<String, int[]> entry : resultMap.entrySet()) {
			if (!(entry.getKey().equals(u_account))) {
//				System.out.print(entry.getKey());
				otherKeys[n] = entry.getKey();
//				System.out.println("n=" + n);
				n++;
			}
		}

		// ����ָ���û��������û�֮��ļнǣ����浽map��
//		System.out.println("namedata lenght:" + nameData.length);
//		System.out.println("otherkeys:" + otherKeys.length);
		for (int j = 0; j < otherKeys.length; j++) {
			String tempKey = otherKeys[j];
//			System.out.println(tempKey);
			otherData = resultMap.get(tempKey);
			Double tempAngle = recommendMethod(nameData, otherData);
			angles.put(tempKey, tempAngle);
//			System.out.println("j=" + j);
		}
		// ��angles����value��������
		List<Entry<String, Double>> sortList = new ArrayList<Entry<String, Double>>(
				angles.entrySet());
		Collections.sort(sortList, new Comparator<Map.Entry<String, Double>>() {
			// ��������ȡ��ǰ�ĸ���ȡ���н�С���ĸ������԰�����������
			public int compare(Entry<String, Double> en1,
					Entry<String, Double> en2) {
				return en1.getValue().compareTo(en2.getValue());
			}
		});

		// ȡ���н���С���Ǹ��û�
		String recommendedUser = "";
		for (Entry<String, Double> entry : sortList) {
			if (entry.getValue() > 0.0000000001 && entry.getValue() > 0) {
				recommendedUser = entry.getKey();
				break;
			}
		}
//		System.out.println("recommendUser:"+recommendedUser);
		Map<String, Integer> recommendMap = columnAndData(recommendedUser);
		Map<String, Integer> uAccountMap = columnAndData(name);
		Vector<BookImages> resultData = finalResult(recommendMap, uAccountMap,
				recommendCount);
		db.close(pstmt, connection);
		return resultData;
	}

	// ��������֮��ļн�
	public double recommendMethod(int[] a, int[] b) {
		int aLength;// ����a,b�ĳ���,����������ĳ�����һ�µ�
		int sumPointMul = 0;// ������˵Ľ��
		int aSum = 0, bSum = 0;// ����a,b������ƽ��֮��
		double resultCos;// ������������ֵ
		double angle;// �������ļн�
		aLength = a.length;
		for (int i = 0; i < aLength; i++) {
			sumPointMul += a[i] * b[i];
			aSum += Math.pow(a[i], 2);
			bSum += Math.pow(b[i], 2);
		}
		// �������ֵ
		if (aSum == 0 || bSum == 0) {
			return -1;
		}
		resultCos = sumPointMul / (Math.sqrt(aSum) * Math.sqrt(bSum));
		// ���÷����Ǻ�������н�
		angle = Math.acos(resultCos);
		return angle;
	}

	// �����ݿ��ȡ���ݷŵ�map��,���û�����Ϊkeyֵ�洢
	public HashMap<String, int[]> dbAllDataToMap() {
		String sql = "select * from recommend_books";
		HashMap<String, int[]> userData = new HashMap<String, int[]>();
		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount() - 1;
			// ����nά���飬��Ÿ��û�����
			while (rs.next()) {
				int value[] = new int[columnCount];
				// ���û����ݴ���value ������
				for (int i = 0; i < columnCount; i++) {
					value[i] = rs.getInt(i + 2);
				}
				// ��ȡu_account
				String name = rs.getString(1);
//				System.out.print(":" + name);
				// ��u_accountΪkey,value����Ϊֵ
				userData.put(name, value);
			}
			if (rs.last()) {
//				System.out.println("rs���");
				userAmount = rs.getRow();
			}
//			System.out.println("useramount:" + userAmount);
			names = new String[userAmount];
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userData;
	}

	public Map<String, Integer> columnAndData(String userAccount) {
		String sql = "select  * from recommend_books where u_account='"
				+ userAccount + "'";
		Map<String, Integer> datasMap = new HashMap<String, Integer>();
		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			String[] b_o_nums = new String[columnCount];
			//TODOΪ��ô���ε�ѭ���ĳ�ʼֵҪ��ͬ
			for (int i = 2; i < columnCount; i++) {
				b_o_nums[i-2] = rsmd.getColumnName(i);
//				System.out.println("colunmName" + b_o_nums[i-2] + "");
			}
			while (rs.next()) {
				int value = 0;
				//TODO Ϊʲô
				//���û����ݴ���value ������
				for (int i = 2; i < columnCount; i++) {
//					System.out.println("b_oNums=="+b_o_nums[i]);
					value = rs.getInt(i);
					datasMap.put(b_o_nums[i-2], value);
				}
			}
//			for (Entry<String, Integer> entry : datasMap.entrySet()) {
//				System.out.print(entry.getKey() + ":" + entry.getValue() + "");
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datasMap;
	}

	public Vector<BookImages> finalResult(Map<String, Integer> recommendMap,
			Map<String, Integer> uAccountMap, int recommendCount) {
		Vector<BookImages> result = new Vector<BookImages>();
		String[] resultB_ids = new String[recommendCount];
		List<Entry<String, Integer>> sortList = new ArrayList<Entry<String, Integer>>(
				recommendMap.entrySet());
		Collections.sort(sortList,
				new Comparator<Map.Entry<String, Integer>>() {
					// ��������ȡ��ǰ�ĸ���ȡ���н�С���ĸ������԰�����������
					public int compare(Entry<String, Integer> en1,
							Entry<String, Integer> en2) {
						return en2.getValue().compareTo(en1.getValue());
					}
				});
//		System.out.println();
		int flag = 0;
		for (Entry<String, Integer> entry : sortList) {
			String key = entry.getKey();
//			System.out.println(key + "sort:" + entry.getValue() + "");
			if (flag >= recommendCount) {
				break;
			}
			if (uAccountMap.containsKey(key) && uAccountMap.get(key) == 0) {
				int index = key.lastIndexOf("_");// �ж���û��Ҫ�ָ��separetor�ַ�
				String[] separetedArr = null;// ��ŷָ�������
				if (index != -1) {
					separetedArr = key.split("_");
//					for (int i = 0; i < separetedArr.length; i++) {
//						System.out.println("i="+i+","+separetedArr[i]+"");
//					}
					resultB_ids[flag] = separetedArr[0];
					flag++;
				}
				if (index == -1) {
					continue;
				}
			}
		}
//		System.out.println("flag:" + flag);
//		for (int i = 0; i < resultB_ids.length; i++) {
//			System.out.println("result"+resultB_ids[i]+"");
//			
//		}
		for (int i = 0; i < resultB_ids.length; i++) {
			String sql = "select b_id,b_img from book_images where b_id = '"
					+ resultB_ids[i] + "'";
//			System.out.println(sql);
			try {
				pstmt = connection.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					BookImages bookImages = new BookImages();
					bookImages.setB_id(rs.getString(1));
					bookImages.setB_img(rs.getString(2));
//					System.out.println("bid = " + bookImages.getB_id());
					result.add(bookImages);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
