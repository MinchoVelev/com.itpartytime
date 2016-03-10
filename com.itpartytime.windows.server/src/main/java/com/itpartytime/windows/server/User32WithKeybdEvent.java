package com.itpartytime.windows.server;


import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;


public interface User32WithKeybdEvent extends User32 {
	public User32WithKeybdEvent INSTANCE = (User32WithKeybdEvent) Native.loadLibrary("user32.dll",User32WithKeybdEvent.class);
	public void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
}
