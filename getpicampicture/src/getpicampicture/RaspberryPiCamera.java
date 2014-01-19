package getpicampicture;

import java.io.IOException;

/**
 * 
 * Basic camera class
 * 
 */
public abstract class RaspberryPiCamera implements CommandDispatcher {
	
	private String ProgDump = GlobalVars.RASPI_CMD_NAME;
	private String ProgOpts = GlobalVars.RASPI_CMD_OPTS;
	
	public int dumpPicture(String name) throws IOException, InterruptedException {
		
		String cmdstr = ProgDump + " " + ProgOpts + " " + name;

		System.out.println(cmdstr);

		String[] cmd = cmdstr.split("\\s+");
								
		ProcessBuilder procBuilder = new ProcessBuilder(cmd);
		procBuilder.redirectErrorStream(true);
		
		Process process = procBuilder.start();
		
		int exitVal = process.waitFor();

		return exitVal;
	}
	
	//
	// Execute local command
	//
	@Override
	public boolean execCommand(String command){
		
		if (command.equals("dump")){
			try {
				if (this.dumpPicture(GlobalVars.RASPI_CMD_IMG) == 0) {
					return true;
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	//
	// makes camera accessible to external remote calls
	//
	public abstract void connect();
	
	//
	// sends taken picture to specified remote destination 
	//
	public abstract void sendPicture();
	
}