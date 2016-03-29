package com.itpartytime.windows.server.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itpartytime.common.Commands;
import com.itpartytime.common.Constants;
import com.itpartytime.windows.server.IMediaController;

public class MediaControlRequestExecutor extends Thread {

	private static final Logger logger = LoggerFactory
			.getLogger(MediaControlRequestExecutor.class);

	private Socket socket;
	private String hostName;
	private boolean active;

	public MediaControlRequestExecutor(Socket socket) {
		this.socket = socket;
		hostName = socket.getInetAddress().getHostName();
		active = true;
	}

	@Override
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("Host '{}' connected.", hostName);
		}
		try {
			IMediaController mediaController = new MediaController();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),
							Charset.forName(Constants.UTF_8)));

			while (active) {
				String command = bufferedReader.readLine();
				if (logger.isDebugEnabled()) {
					logger.debug("Recieved command: '{}' from host: '{}'",
							command, hostName);
				}
				switch (command) {

				case Commands.VOLUME_UP:
					mediaController.increaseVolume();
					break;

				case Commands.VOLUME_DOWN:
					mediaController.decreaseVolume();
					break;

				case Commands.PLAY_NEXT:
					mediaController.playNext();
					break;

				case Commands.PLAY_PREVIOUS:
					mediaController.playPrevious();
					break;

				case Commands.CHANGE_STATE:
					mediaController.changePlayState();
					break;

				case Commands.EXIT:
					active = false;
					socket.close();
					if (logger.isDebugEnabled()) {
						logger.debug("Host '{}' disconnected via command.",
								hostName);
					}
					break;

				default:
					if (logger.isDebugEnabled()) {
						logger.debug("Unknown command: '{}' from host '{}'",
								command, hostName);
					}
				}
			}
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Couldn't process command. ", e);
			}
		}
	}
}
