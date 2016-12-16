package com.game.JavaSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

					while (true) {
						char[] buf = new char[1024];

						br.read(buf, 0, 1024);
						if(buf==null) break;
						String recive = String.valueOf(buf);
						System.out.println(getTime() + " Ŭ���̾�Ʈ�κ��� ���� ���ڿ� : " + recive);
						if (recive == "s") {
							System.out.println(getTime()+"�������Դϴ�.");
							br.read(buf, 0, 1024);
							String data = String.valueOf(buf);
							writeFile(data);
							System.out.println(getTime()+"����Ϸ�.");
						}
						if (recive == "l"){
							System.out.println(getTime()+"�����͸� �����մϴ�.");
							buf = stringFile().toCharArray();
							pw.write(buf, 0, 1024);
							pw.flush(); // ���۸� ���
						}						
						
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

	public String stringFile() {
		String out = null;
		File m_file = new File("E:\\TermProJect00\\TermProject\\JavaSocketServer\\test.txt");
		try {
			FileReader m_reader = new FileReader(m_file);
			int c = 0;
			while (true) {
				c = m_reader.read();
				if (c == -1)
					break;
				if (out == null)
					out = String.valueOf((char) c);
				else
					out += String.valueOf((char) c);
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

	public void writeFile(String str) {
		File m_file = new File("E:\\TermProJect00\\TermProject\\JavaSocketServer\\test.txt");
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(m_file));
			// BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(str);
			bufferedWriter.newLine();

			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
