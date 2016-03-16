package Network361;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Packet {
	private int m_iSequenceNumber;
	private int m_iPacketLength;
	private int m_iPayloadLenth;
	private byte[] m_cPayload;
	
	public Packet(int _SequnceNumber) {
		m_iSequenceNumber = _SequnceNumber ;
	}
	public void LoadPsacket(byte[] _payload, int _payloadlength){
		byte[] _int = int_to_4Byte(m_iSequenceNumber);
		m_cPayload = new byte[_int.length+_payloadlength];
		System.arraycopy(_int, 0, m_cPayload, 0, _int.length);
		System.arraycopy(_payload, 0, m_cPayload, _int.length, _payloadlength);
		m_iPacketLength = _payloadlength + _int.length;
		m_iPayloadLenth = _payloadlength;
	}
	public byte[] UnloadPacket(){
		return m_cPayload;
	}
	public void SendViaDataStream(DataOutputStream _OutputStream){
		try {
			_OutputStream.write(m_cPayload, 0, m_iPacketLength);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Packet Send Fail");
			e.printStackTrace();
		}
	}
	public int getPacketSize(){
		return m_iPacketLength;
	}
	public int getPayloadLength(){
		return m_iPayloadLenth;
	}
	private byte[] int_to_4Byte(int i){
		return ByteBuffer.allocate(4).putInt(i).array();
	}
}
