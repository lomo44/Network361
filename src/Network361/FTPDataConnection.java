package Network361;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

// TODO: Unify this connection's constructor


public class FTPDataConnection extends Connection {

	private String Filename;
	private boolean isSending;
	private DataInputStream datain;
	private DataOutputStream dataout;
	private FileInputStream FileInput;
	private FileOutputStream FileOutput;
	
	public FTPDataConnection(Socket _soc, String Filename, boolean isSending) {
		super(_soc);
		this.Filename = Filename;
		this.isSending = isSending;
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doRequest() throws IOException {
		if(this.isSending){
			SendingFile();
		}
		else{
			ReceivingFiles();
		}
		this.close();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			doRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void SendingFile() throws IOException,FileNotFoundException {
		byte[] buffer = new byte[2048];
		FileInput= new FileInputStream(new File(Filename));
		dataout = this.getOutputwriter();
		for(;;){
			int byteread = FileInput.read(buffer);
			if(byteread > 0)
				dataout.write(buffer, 0, byteread);
			else
				break;
		}
		FileInput.close();
		dataout.close();
	}
	protected void ReceivingFiles() throws IOException {
		File tempFile = new File(Filename);
		datain = new DataInputStream(this.getConnectionSocket().getInputStream());
		if(tempFile.exists()){

		}
		else {
			tempFile.createNewFile();
			ReceivedFileFromBuffer(tempFile);
			}
	}
	private void ReceivedFileFromBuffer(File newFile) throws IOException{
		byte[] buffer = new byte[2048];
		FileOutput = new FileOutputStream(newFile);
		for(;;){
			int datareceived = datain.read(buffer);
			if(datareceived > 0)
				FileOutput.write(buffer, 0, datareceived);
			else {
				break;
			}
		}
		FileOutput.close();
		datain.close();
	}
}
