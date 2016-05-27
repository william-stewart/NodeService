import java.io.*;
import java.net.*;

public class UDPServer {
	
	public static void createServer() throws Exception{
		try{
		DatagramSocket serverSocket = new DatagramSocket(9875);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String modifiedSentence = sentence + " NODE MODIFIED";
			System.out.println(modifiedSentence);
			sendData = modifiedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
			serverSocket.send(sendPacket);
			serverSocket.close();
		}
		}
		catch(SocketException ex){
			System.out.println("UDP Port 9875 is occupied");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Pulled from mac
		//And from pc
		
		try{
				createServer();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
