import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UDPServer {
	static String nodeName = "Unkown node";
	
	public static void createServer() throws Exception{
		try{
			
			String hostName = InetAddress.getLocalHost().getHostName();
			String piNumString = Character.toString(hostName.charAt(2)).concat(Character.toString(hostName.charAt(3)));
			int piNumInt = Integer.parseInt(piNumString) - 9;
			int designatedPort = 9800 + piNumInt;
			System.out.println("Opened server on port: " + designatedPort);
			DatagramSocket serverSocket = new DatagramSocket(designatedPort);
			
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
				if(sentence.trim().equals("*")){
					System.out.println("refresh");
				}
				else{
					String nextWord = nodeName + ": " + wordSearcher(sentence);
					sendData = nextWord.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
					serverSocket.send(sendPacket);
					nextWord = "";
				}
				sentence = "";
			}
		}
		catch(SocketException ex){
			System.out.println("UDP Port 9875 is occupied");
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public static String wordSearcher(String searchWord){
		String trimmedSearchWord = searchWord.trim();
		String allNextWords = "^";
		try{
			File file = new File("/home/pi/TestCorpus/example.txt");
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String nextToken = scanner.next();
				if(nextToken.equalsIgnoreCase(trimmedSearchWord) && scanner.hasNext()){
					System.out.println("Found word!");
					String followingWord = scanner.next();
					allNextWords = allNextWords.concat(followingWord) + "^";
				}
			}
			scanner.close();
			return allNextWords;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error occured while searching for word");
		}
		return "$";
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
