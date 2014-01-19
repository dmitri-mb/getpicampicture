package getpicampicture;
/**
 * 
 * Listens for incoming Pusher or remote tcp requests,
 * calls Raspberry Pi command for taking picture
 * and sends the picture taken to specified destination 
 * 
 */

public class getpicampicture {

	public static void main(String[] args) {
		
		String[] PusherArgs = new String[3];
		
		PusherArgs[0] = GlobalVars.PUSHER_KEY;
		PusherArgs[1] = GlobalVars.PUSHER_CHANNEL;
		PusherArgs[2] = GlobalVars.PUSHER_EVENT;
		
		new CameraPusher(PusherArgs).connect();
		new CameraServer().connect();

	}
}
