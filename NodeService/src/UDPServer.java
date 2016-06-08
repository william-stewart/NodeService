import java.io.*;
import java.net.*;

public class UDPServer {
	static String nodeName = "Unkown node";
	
	public static void createServer() throws Exception{
		try{
		DatagramSocket serverSocket = new DatagramSocket(9875);
		
		while(true){
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			InetAddress localAddr;
			localAddr = InetAddress.getLocalHost();
			nodeName = localAddr.getHostName();
			int port = receivePacket.getPort();
			String modifiedSentence = "Made it to worker(" + nodeName + "): " + sentence ;
			System.out.println(modifiedSentence);
			sendData = modifiedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
			serverSocket.send(sendPacket);
			modifiedSentence = "";
			sentence = "";
		}
		}
		catch(SocketException ex){
			System.out.println("UDP Port 9875 is occupied");
			ex.printStackTrace();
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
