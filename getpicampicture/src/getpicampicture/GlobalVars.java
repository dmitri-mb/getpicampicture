package getpicampicture;

public class GlobalVars {
	
	//public static String RASPI_CMD_NAME = "touch.exe";
	//public static String RASPI_CMD_OPTS = "-a -m";
	public static String RASPI_CMD_NAME = "raspistill";
	public static String RASPI_CMD_OPTS = "-w 640 -h 480 -q 100 -th 0:0:0 -t 200 -ISO 800 -rot 180 -o";
		//public static String RasPiCmdOpts = "-o";
	public static String RASPI_CMD_IMG = "image.jpg";

	public static String PUSHER_KEY = "906eae22e03eaa4f0c11";
	public static String PUSHER_CHANNEL = "ch_raspi";
	public static String PUSHER_EVENT = "ev_image";
	public static String PUSHER_CMDNAME = "cmd";
	public static String PUSHER_CMDVAL = "dump";
	
	public static String UPLOAD_URL = "http://theinternetofthings.ru" + "/getpicampicture/uploadimage.php";
	
}
