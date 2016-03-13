package com.itpartytime.windows.server.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import com.itpartytime.windows.common.Commands;
import com.itpartytime.windows.server.IMediaController;

public class MediaControlRequestExecutor extends Thread {
	private static final String UTF_8 = "UTF-8";
	private Socket socket;

	public MediaControlRequestExecutor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			IMediaController mediaController = new MediaController();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),
							Charset.forName(UTF_8)));

			String command = bufferedReader.readLine();
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

			default:
				System.out.println("unknown command");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
