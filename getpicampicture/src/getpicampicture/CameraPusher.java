package getpicampicture;

import java.util.Map;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

/**
 * 
 * 
 * 
 */
public class CameraPusher extends RaspberryPiCamera implements ConnectionEventListener, ChannelEventListener{

	private final Pusher pusher;
	private final String channelName;
	private final String eventName;
	
	private String apiKey;
		
	public CameraPusher(String[] args) {

		apiKey = (args.length > 0) ? args[0] : "906eae22e03eaa4f0c11";
		channelName = (args.length > 1) ? args[1] : "no-channel";
		eventName = (args.length > 2) ? args[2] : "no-event";
		PusherOptions options = new PusherOptions().setEncrypted(true);
		pusher = new Pusher(apiKey, options);

	}
	
	@Override
	public void connect(){
		
		if (pusher == null) return;

		pusher.connect(this);

		pusher.subscribe(channelName, this, eventName);
		
	}
	
	/* ConnectionEventListener implementation */

	@Override
	public void onConnectionStateChange(ConnectionStateChange change) {

		System.out.println(String.format(
				"Connection state changed from [%s] to [%s]",
				change.getPreviousState(), change.getCurrentState()));
	}

	@Override
	public void onError(String message, String code, Exception e) {

		System.out.println(String.format(
				"An error was received with message [%s], code [%s], exception [%s]",
				message, code, e));
		// Reinitiate connection in case of failure
		try {
		    Thread.sleep(5000);
		    pusher.connect(this);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}	
	}
	
	/* ChannelEventListener implementation */

	@Override
	public void onEvent(String channelName, String eventName, String data) {
		
		System.out.println(String.format(
				"Received event [%s] on channel [%s] with data [%s]", eventName,
				channelName, data));
		
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObject = gson.fromJson(data, Map.class);
		
		String cmd = jsonObject.get(GlobalVars.PUSHER_CMDNAME);
		
		System.out.println(cmd);
		
		if ((cmd != null ) && cmd.equals(GlobalVars.PUSHER_CMDVAL)) {
			
			if (super.execCommand(cmd))
				sendPicture();
			
		}
		
	}

	@Override
	public void onSubscriptionSucceeded(String channelName) {

		System.out.println(String.format("Subscription to channel [%s] succeeded",
				channelName));
	}

	@Override
	public void sendPicture() {
		
		FileSenderHttp UploadFile = new FileSenderHttp(GlobalVars.RASPI_CMD_IMG, GlobalVars.UPLOAD_URL);
		UploadFile.execute();
					
	}


}
