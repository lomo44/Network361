package Network361;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

// This class seperate a file to an array of packets


public class FileSplitter {
	private ArrayList<Packet> packetshelf;
	private int numberofpacket;
	private int segmentsize;
	private int filesize;
	private FileInputStream fileInputStream;
	private File file;
	
	public FileSplitter(String _filename, int _segmentsize) {
		file = new File(_filename);
		segmentsize = _segmentsize;
		packetshelf = new ArrayList<Packet>();
		if(ValidateFile()){
			try {
				fileInputStream = new FileInputStream(file);
				SplitingFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public int getFileLength(){
		return filesize;
	}
	private boolean ValidateFile(){
		if(file.isDirectory()||!file.exists()){
			return false;
		}
		else{
			return true;
		}
	}
	private void SplitingFile() throws IOException{
		byte[] buffer = new byte[segmentsize];
		int _SequnceNumber = 0;
		numberofpacket = 0;
		for(;;){
			int byteread = fileInputStream.read(buffer);
			if(byteread > 0){
				_SequnceNumber += byteread;
				numberofpacket++;
				Packet newpacket = new Packet(numberofpacket);
				newpacket.LoadPsacket(buffer, byteread);
				filesize += byteread;
				packetshelf.add(newpacket);
			}
			else{
				break;
			}
		}
		System.out.println("File Successfully Splited" + packetshelf.size());
		fileInputStream.close();
	}
	public ArrayList<Packet> CheckOutPackets(){
		return packetshelf;
	}
}
