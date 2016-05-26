import java.io.*;
import java.net.*;

public class UDPServer {
	
	public static void createServer() throws Exception{
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String modifiedSentence = sentence + " NODE MODIFIED";
			sendData = modifiedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
			serverSocket.send(sendPacket);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
				createServer();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
