package Network361;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

// This class seperate a file to an array of packet


public class FileSplitter {
	private ArrayList<Packet> packetshelf;
	private int numberofpacket;
	private int segmentsize;
	private FileInputStream fileInputStream;
	private File file;
	
	public FileSplitter(String _filename, int _segmentsize) {
		file = new File(_filename);
		segmentsize = _segmentsize;
		if(ValidateFile()){
			SplitingFile();
		}
	}
	private boolean ValidateFile(){
		if(file.isDirectory()||!file.exists()){
			return false;
		}
		else{
			return true;
		}
	}
	private void SplitingFile(){
		byte[] buffer = new byte[segmentsize];
		int _SequnceNumber = 0;
		numberofpacket = 0;
		for(;;){
			try {
				int byteread = fileInputStream.read(buffer);
				if(byteread > 0){
					_SequnceNumber += (byteread + 1);
					Packet newpacket = new Packet(_SequnceNumber);
					newpacket.LoadPsacket(buffer, byteread);
					numberofpacket++;
					packetshelf.add(newpacket);
				}
				else{
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Unexpected Error when spliting file");
				e.printStackTrace();
			}
		}
		System.out.println("File Successfully Splited");
		try {
			fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private ArrayList<Packet> CheckOutPackets(){
		return packetshelf;
	}
}
