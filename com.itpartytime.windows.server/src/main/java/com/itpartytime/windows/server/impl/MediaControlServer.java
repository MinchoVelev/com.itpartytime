package com.itpartytime.windows.server.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaControlServer {
	private static final Logger logger = LoggerFactory
			.getLogger(MediaControlServer.class);

	private static final int SERVER_PORT = 44455;
	public static boolean run = true;

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		logger.info("Starting server on port {}..", SERVER_PORT);
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		logger.info("Server started.");
		while (run) {
			Socket clientSocket = serverSocket.accept();
			MediaControlRequestExecutor requestExecutor = new MediaControlRequestExecutor(
					clientSocket);
			requestExecutor.start();
		}
		serverSocket.close();
	}
}
