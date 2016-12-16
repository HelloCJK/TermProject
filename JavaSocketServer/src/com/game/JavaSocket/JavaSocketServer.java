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
		ServerSocket serverSocket = null; // 서버의 소켓
		int port = 1500; // 포트 번호

		try {
			serverSocket = new ServerSocket(port); // 포트에 서버소켓을 붙인다(Bind)
			System.out.println(getTime() + " 서버가 준비되었습니다.");
			while (true) {

				Socket socket = serverSocket.accept(); // 클라이언트의 접속을
														// 허가한다.(Accept)
				InetAddress clientAddress = socket.getInetAddress(); // 클라이언트의
																		// 주소를
																		// 가져온다.
				System.out.println(getTime() + clientAddress + " 에서 클라이언트가 접속했습니다.");

				try {
					InputStream in = socket.getInputStream(); // 클라이언트 소켓의 바이트
					BufferedReader br = new BufferedReader(new InputStreamReader(in));

					OutputStream out = socket.getOutputStream(); 
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

					while(true){
						char[] buf = new char[20];
						
						br.read(buf,0,20);
						System.out.println(getTime() + " 클라이언트로부터 받은 문자열 : " + String.valueOf(buf));
						pw.write(buf, 0,20);
						pw.flush(); // 버퍼를 비움
					}
				} catch (Exception e) {
					System.out.println(getTime() + " Error");
					e.printStackTrace();
				} finally {
					socket.close();
					System.out.println(getTime() + " 서버가 닫힙니다.");
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
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]"); // 날짜 출력
		return f.format(new Date());
	}

}
