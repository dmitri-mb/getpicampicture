package getpicampicture;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSenderTcp extends FileSender {
	
	private DataOutputStream out;

	public FileSenderTcp(String filename, DataOutputStream outStream) {
		
		super(filename);
		out = outStream;
		
	}

	@Override
	public void execute() {
		
		//System.out.println(super.getFileName());
		String strout = "empty";
		
		File file = new File(super.getFileName());
		 
		
		try {
			
			FileInputStream fis = new FileInputStream(file);
			
			strout = String.valueOf(fis.available());
			
			byte[] filebytes = new byte[fis.available()];
			
			fis.read(filebytes);
			
			out.writeUTF("write from CameraServer: " + strout);
			out.write(filebytes);
			
			fis.close();
			
		} catch (IOException e) {
			System.out.println("FileSenderTcp. Send data error: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
