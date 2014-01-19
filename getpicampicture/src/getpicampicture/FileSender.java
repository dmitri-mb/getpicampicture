package getpicampicture;

public abstract class FileSender {
	
	private String fname = "image.jpeg";
	
	public void setFileName(String filename){
		fname = filename;
	}

	public String getFileName(){
		return fname;
	}
	
	public FileSender(String filename){
		fname = filename;
	}

	public abstract void execute();
}
