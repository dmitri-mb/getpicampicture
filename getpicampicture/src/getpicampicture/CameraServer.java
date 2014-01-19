package getpicampicture;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 
 * 
 *
 */
public class CameraServer extends RaspberryPiCamera implements DataOutputStreamWriter {
	
	DataOutputStream clientOut;

	@Override
	public void connect() {
		
		new TCPServer(null, this, this);
				
	}

	@Override
	public void sendPicture() {

		if (clientOut != null){
			System.out.println("send picture");
			new FileSenderTcp(GlobalVars.RASPI_CMD_IMG, clientOut).execute();
		}
		
	}
	
	@Override
	public boolean execCommand(String command){
		
		if (super.execCommand(command)) {
			
			System.out.println("exec ok");
			
			sendPicture();
			return true;
			
		}
		
		return false;
	}
	
	@Override
	public void setOut(DataOutputStream out){
		clientOut = out;
	}

}
