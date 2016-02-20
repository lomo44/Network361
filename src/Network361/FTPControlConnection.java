package Network361;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPControlConnection extends Connection {
	
	public FTPControlConnection(Socket _soc) {
		super(_soc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doRequest() throws IOException {
		// TODO Auto-generated method stub
		this.writeLineToDataOutputStream("FTP Control Connection Established, please enter file path");
		String filename = this.readLineFromBufferedReader();
		File newfile = new File(filename);
		if(isFileValid(newfile)){
			int portnum = this.getConnectionSocket().getPort()+1;
			this.writeLineToDataOutputStream("File Exist, Sending Data Connection Port Number: " + portnum);
			this.writeLineToDataOutputStream(Integer.toString(portnum));
			ServerSocket FTPdata = new ServerSocket(portnum);
			Socket FTPdataSocket = FTPdata.accept();
			Thread FTPdataconnection = new Thread(new FTPDataConnection(FTPdataSocket,filename,true));
			FTPdataconnection.start();
			try {
				FTPdataconnection.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.writeLineToDataOutputStream("File Sent");
			FTPdata.close();
		}
		else {
			this.writeLineToDataOutputStream("File does not Exist");
		}
	}
	protected boolean isFileValid(File newfile){
            return newfile.exists()&&!newfile.isDirectory();
        }
	@Override
	public void run() {
		try {
			doRequest();
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
