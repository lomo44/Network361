package Network361;

public abstract class ARQClient extends SimpleClient {
	protected volatile int lastACK;
	protected int nofPackets;
	protected ACKListener _AckListener;
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
		_AckListener = new ACKListener(getSocket(), this,Thread.currentThread());
		Thread newthread = new Thread(_AckListener);
		newthread.start();
	}
	public int getNumberOfPacket(){
		return nofPackets;
	}
	public void setNumberOfPacket(int num){
		nofPackets = num;
	}
	public void setNotifyACK(int ack){
		_AckListener.SetNotifyACK(ack);
	}
}
