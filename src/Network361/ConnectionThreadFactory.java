package Network361;

import java.io.IOException;
import java.net.Socket;

enum ConnectionType{
	Echo,
	FTP_CONTROL,
	Stop_And_Wait
};

public class ConnectionThreadFactory {
	public Thread getConnectionThread(ConnectionType _type, Socket _soc) throws IOException{
		Thread newthread;
		switch(_type){
			case Echo:{ newthread = new Thread(new EchoConnection(_soc)); break;}
			case FTP_CONTROL:{ newthread = new Thread(new FTPControlConnection(_soc)); break;}
			case Stop_And_Wait:{ newthread = new Thread(new SWConnection(_soc)); break;}
			default: newthread = null;
			
		}
		return newthread;
	}
}
