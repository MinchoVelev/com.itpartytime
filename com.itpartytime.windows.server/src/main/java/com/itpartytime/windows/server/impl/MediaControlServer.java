package com.itpartytime.windows.server.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MediaControlServer {
	private static final int SERVER_PORT = 44455;
	public static boolean run = true;

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Starting server on port " + SERVER_PORT);
		while (run) {
			Socket clientSocket = serverSocket.accept();
			MediaControlRequestExecutor requestExecutor = new MediaControlRequestExecutor(
					clientSocket);
			requestExecutor.start();
		}
		serverSocket.close();
	}
}
