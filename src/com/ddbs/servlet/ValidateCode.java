package com.ddbs.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//��֤��
public class ValidateCode extends HttpServlet {

	// ���һ�������֤��ͼƬ
	public static final int WIDTH = 160;
	public static final int HEIGHT = 50;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 1.�O�ñ���ɫ
		setBackGround(g);
		// 2.�O��߅��
		setBorder(g);
		// 3.��������
		drawRandomLine(g);
		// 4.д�����
		String random = drawRandomNum((Graphics2D) g);
//		System.out.println("random:"+random);
		// д���ͻ�����ͬʱҲ��������һ��,����֤��浽session��
		request.getSession().setAttribute("imagecheckcode", random);
//		System.out.println("random"+random);
		// 5.��ͼ��д�������
		// ֪ͨ�������ͼ�εķ�ʽ��
		response.setContentType("image/jpeg");
		// ��ͷ�����������Ҫ����
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		ImageIO.write(image, "jpg", response.getOutputStream());
		
		
	}
	

	private String drawRandomNum(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(64, 0, 0));
		g.setFont(new Font("����", Font.BOLD, 40));
		// ���еĺ��ֶ���Unicode��\u4e00--\u9fa5������,���е����Ķ�����Unicode����ȥ�ġ�����char c =
		// '\u4e00',�൱��char c='һ'

		String base = "\u0051\u0057\u0045\u0052\u0054\u0059\u0055\u0049\u004f\u0050\u0041\u0053\u0044\u0046\u0047\u0048\u004a\u004b\u004c\u005a\u0058\u0043\u0056\u0042\u004e\u004d"
				+ "\u0071\u0077\u0065\u0072\u0074\u0079\u0075\u0069\u006f\u0070\u0061\u0073\u0064\u0066\u0067\u0068\u006a\u006b\u006c\u007a\u0078\u0063\u0076\u0062\u006e\u006d"
				+ "\u0031\u0032\u0033\u0034\u0035\u0036\u0037\u0038\u0039\u0030";
		StringBuffer sb = new StringBuffer();
		int x = 14;
		for (int i = 0; i < 4; i++) {
			int degree = new Random().nextInt() % 30;// ��30���������ظ�30����30���������
			String ch = base.charAt(new Random().nextInt(base.length())) + "";// ���ꡰ
			sb.append(ch); // ��֮�����char����ת��Ϊstring�����ˡ�
			g.rotate(degree * Math.PI / 180, x, 35);// ������ת�ĽǶȣ������Ƶ�
			g.drawString(ch, x, 35);
			g.rotate(-degree * Math.PI / 180, x, 35);// ����ת���������ת��ȥ�ˣ�Ҫ��Ȼһֱѭ�����ǶȻ�һֱ���ű��ע���ֻ���ת����֤�����ڵĿ�
			x += 40;

		}
		return sb.toString();
	}

	private void drawRandomNum2(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.white);
		g.setFont(new Font("����", Font.BOLD, 20));
		// ���еĺ��ֶ���Unicode��\u4e00--\u9fa5������,���е����Ķ�����Unicode����ȥ�ġ�����char c =
		// '\u4e00',�൱��char c='һ'
		String base = "�����壬��һ��";
		g.drawString(base, 14, 30);
	}

	
	private void drawRandomLine(Graphics g) {
		// TODO Auto-generated method stub
		Color[] colors = {new Color(249, 34, 34),new Color(113, 2, 121),new Color(73, 1, 45)};
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.0f));
		g2.setFont(new Font("����", Font.BOLD, 600));
		int width = WIDTH +10;
		int height = HEIGHT+10;
		for (int i = 0; i < 13; i++) {
			g2.setColor(colors[new Random().nextInt(2)]);
			int x1 = new Random().nextInt(width);
			int y1 = new Random().nextInt(height);

			int x2 = new Random().nextInt(width);
			int y2 = new Random().nextInt(height);
			g2.drawLine(x1, y1, x2, y2);
		}
	}

	private void setBorder(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(25, 200, 100));
		g.drawRect(0, 0, WIDTH, HEIGHT);
	}

	private void setBackGround(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(25, 200, 100));
		g.fillRect(1, 1, WIDTH - 2, HEIGHT - 2);
	}
}
