import com.partytime.windows.server.IMediaController;
import com.partytime.windows.server.impl.MediaController;

public class Test {
	public static void main(String[] args) {
		IMediaController mediaController = new MediaController();
		mediaController.increaseVolume();
	}
}
