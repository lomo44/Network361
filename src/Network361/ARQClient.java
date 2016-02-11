package Network361;

public abstract class ARQClient extends SimpleClient {
	protected volatile int lastACK;
<<<<<<< HEAD
	protected int nofPackets;
=======
	protected volatile int nofPackets;
>>>>>>> branch 'master' of https://github.com/lomo44/Network361.git
	public ARQClient() {
		// TODO Auto-generated constructor stub
	}
	public int getLastACK() {
		return lastACK;
	}
	public void setLastACK(int lastACK) {
		this.lastACK = lastACK;
	}
	protected void InitializeACKListener(){
		Thread newthread = new Thread(new ACKListener(getSocket(), this));
		newthread.start();
	}
	public int getNumberOfPacket(){
		return nofPackets;
	}
	public void setNumberOfPacket(int num){
		nofPackets = num;
	}
}
