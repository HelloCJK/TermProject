package com.game.JavaSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaSocketServer implements Runnable {

	@Override
	public void run() {
		ServerSocket serverSocket = null; // ������ ����
		int port = 1500; // ��Ʈ ��ȣ

		try {
			serverSocket = new ServerSocket(port); // ��Ʈ�� ���������� ���δ�(Bind)
			System.out.println(getTime() + " ������ �غ�Ǿ����ϴ�.");
			while (true) {

				Socket socket = serverSocket.accept(); // Ŭ���̾�Ʈ�� ������
														// �㰡�Ѵ�.(Accept)
				InetAddress clientAddress = socket.getInetAddress(); // Ŭ���̾�Ʈ��
																		// �ּҸ�
																		// �����´�.
				System.out.println(getTime() + clientAddress + " ���� Ŭ���̾�Ʈ�� �����߽��ϴ�.");

				try {
					InputStream in = socket.getInputStream(); // Ŭ���̾�Ʈ ������ ����Ʈ
					BufferedReader br = new BufferedReader(new InputStreamReader(in));

					OutputStream out = socket.getOutputStream(); 
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

					while(true){
						char[] buf = new char[20];
						
						br.read(buf,0,20);
						System.out.println(getTime() + " Ŭ���̾�Ʈ�κ��� ���� ���ڿ� : " + String.valueOf(buf));
						pw.write(buf, 0,20);
						pw.flush(); // ���۸� ���
					}
				} catch (Exception e) {
					System.out.println(getTime() + " Error");
					e.printStackTrace();
				} finally {
					socket.close();
					System.out.println(getTime() + " ������ �����ϴ�.");
				}
			}
		} catch (Exception e) {
			System.out.println(getTime() + " Error");
			e.printStackTrace();
		}

	}
	public String stringFile(){
	      String out = null;
	      File m_file = new File("E:\\TermProJect00\\TermProject\\test.txt");
	      try {
	         FileReader m_reader = new FileReader(m_file);
	         int c = 0;
	         while(true){
	            c = m_reader.read();
	            if(c==-1)   break;
	            if(out == null)   out = String.valueOf((char)c);
	            else         out += String.valueOf((char)c);
	         }
	         m_reader.close();
	      } catch (FileNotFoundException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return out;
	   }


	public static void main(String[] args) {

		Thread desktopServerThread = new Thread(new JavaSocketServer());
		desktopServerThread.start();

	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]"); // ��¥ ���
		return f.format(new Date());
	}

}
