package com.itpartytime.windows.server.impl;

import com.itpartytime.windows.server.IMediaController;
import com.itpartytime.windows.server.User32WithKeybdEvent;

public class MediaController implements IMediaController {
	private static final byte VK_VOLUME_UP = (byte) 0xAF;
	private static final byte VK_VOLUME_DOWN = (byte) 0xAE;
	private static final byte VK_MEDIA_NEXT_TRACK = (byte) 0xB0;
	private static final byte VK_MEDIA_PREV_TRACK = (byte) 0xB1;
	private static final byte VK_MEDIA_PLAY_PAUSE = (byte) 0xB3;

	private static final int KEYEVENTF_EXTENDEDKEY = 1;

	User32WithKeybdEvent u32Api;

	public MediaController() {
		u32Api = User32WithKeybdEvent.INSTANCE;
	}

	public void playNext() {
		pressKey(VK_MEDIA_NEXT_TRACK);
	}

	public void playPrevious() {
		pressKey(VK_MEDIA_PREV_TRACK);
	}

	public void increaseVolume() {
		pressKey(VK_VOLUME_UP);

	}

	public void decreaseVolume() {
		pressKey(VK_VOLUME_DOWN);

	}

	public void changePlayState() {
		pressKey(VK_MEDIA_PLAY_PAUSE);
	}

	private void pressKey(byte key) {
		u32Api.keybd_event(key, (byte) 0, KEYEVENTF_EXTENDEDKEY, 0);
	}
}
