package Network361;
import java.io.IOException;
import java.net.UnknownHostException;


public class FTPServer extends Server {

	public FTPServer(String _servername)
			throws UnknownHostException {
		super(_servername);
		// TODO Auto-generated constructor stub
	}

	@Override
	void ServerRun() throws IOException {
		// TODO Auto-generated method stub
		this.listento(5555);
		for(;;){
			System.out.println("Start waiting on FTP request. \n");
			Thread ftpthread = new Thread(new FTPControlConnection(getRequestSocket()));
			ftpthread.start();
		}
	}
}
