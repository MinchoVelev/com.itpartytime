package com.itpartytime.windows.server.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import com.itpartytime.common.Commands;
import com.itpartytime.common.Constants;
import com.itpartytime.windows.server.IMediaController;

public class MediaControlRequestExecutor extends Thread {

	private Socket socket;
	private boolean active;

	public MediaControlRequestExecutor(Socket socket) {
		this.socket = socket;
		active = true;
	}

	@Override
	public void run() {
		try {
			IMediaController mediaController = new MediaController();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),
							Charset.forName(Constants.UTF_8)));

			while (active) {
				String command = bufferedReader.readLine();
				System.out.println("Recieved command: " + command);
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
					break;

				default:
					System.out.println("unknown command: " + command);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
