package Network361;
import java.io.IOException;
import java.net.UnknownHostException;


public class EchoServer extends Server {

	public EchoServer(String _servername, int _portnum)
			throws UnknownHostException {
		super(_servername);
		// TODO Auto-generated constructor stub
	}

	@Override
	void ServerRun() throws IOException {
		for(;;){
			System.out.println("Start waiting on Echoing request. \n");
			Thread newthread = new Thread(new EchoConnection(getRequestSocket()));
			newthread.start();
		}
	}

}
