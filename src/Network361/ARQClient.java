package Network361;

public abstract class ARQClient extends SimpleClient {
	protected volatile int lastACK;
	protected volatile int numOfAckReceived;
	protected int totalNumberOfPacket;
	protected ACKListener _AckListener;
	public ARQClient() {
		
		// TODO Auto-generated constructor stub
	}
	public int getLastACK() {
		return lastACK;
	}
	public int getNumOfAckReceived(){
		return numOfAckReceived;
	}
	public void setNumOfAckReceived(int _num){
		numOfAckReceived = _num;
	}
	public void setLastACK(int lastACK) {
		this.lastACK = lastACK;
	}
	protected void InitializeACKListener(){
		_AckListener = new ACKListener(getSocket(), this,Thread.currentThread());
		Thread newthread = new Thread(_AckListener);
		newthread.start();
	}
	public int getTotalACK(){
		return totalNumberOfPacket;
	}
	public void setTotalACK(int num){
		totalNumberOfPacket = num;
	}
	public void setNotifyACK(int ack){
		_AckListener.SetNotifyACK(ack);
	}
}
