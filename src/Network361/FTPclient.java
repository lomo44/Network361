package Network361;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



public class FTPclient extends Client {
	
	private FTPControlConnection ftpControlConnection;
	private FTPDataConnection ftpDataConnection;
	private String hostname;
	private int portnum;
	
	public FTPclient(String hostname, int portnum){
		this.hostname = hostname;
		this.portnum = portnum;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket clientConnection;
		try {
			clientConnection = this.ConnectToHost(hostname, portnum);
			ftpControlConnection = new FTPControlConnection(clientConnection);
			String welcomemessage = ftpControlConnection.readLineFromBufferedReader();
			System.out.println(welcomemessage);
			String filepath = this.getUserInput();
			ftpControlConnection.writeLineToDataOutputStream(filepath);
			String fileexist = ftpControlConnection.readLineFromBufferedReader();
			System.out.println(fileexist);
			if(fileexist.equalsIgnoreCase("File does not Exist")){
				return;
			}
			else {
				int portnum = Integer.parseInt(ftpControlConnection.readLineFromBufferedReader());
				Socket dataconnection = this.ConnectToHost("localhost", portnum);
				System.out.println("Sendding File on port:"+ portnum+" Please Enter Receiving File Address: \n");
				String receivefilepath = getUserInput();
				ftpDataConnection = new FTPDataConnection(dataconnection,receivefilepath, false);
				Thread datareceive = new Thread(ftpDataConnection);
				long start_time= System.currentTimeMillis();
				datareceive.start();
				datareceive.join();
				long end_time =System.currentTimeMillis();
				File newfile = new File(receivefilepath);
				long filelength=newfile.length();
				double speed = filelength/(end_time-start_time);
				dataconnection.close();
				String finalmessage = ftpControlConnection.readLineFromBufferedReader();
				System.out.println(finalmessage + "speed: "+ speed + "kb//s\n");
				clientConnection.close();
				return;
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
