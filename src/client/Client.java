package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Client {
	private ObjectMapper mapper = new ObjectMapper();
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public Client(String ip, int port) throws Exception {
		socket = new Socket(ip, port);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	
	public ObjectNode action(String command, String word, String meaning) throws Exception {
		ObjectNode outputObjectNode = mapper.createObjectNode();
		outputObjectNode.put("command", command);
		outputObjectNode.put("word", word);
		outputObjectNode.put("meaning", meaning);
		String outputString = mapper.writeValueAsString(outputObjectNode);
		dataOutputStream.writeUTF(outputString);
		dataOutputStream.flush();
		return mapper.readValue(dataInputStream.readUTF(), ObjectNode.class);
	}
}
