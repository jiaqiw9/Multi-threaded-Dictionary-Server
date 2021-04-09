package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Client {
	public static final String DEFAULT_IP = "localhost";
	public static final int DEFAULT_PORT_NUM = 3005;
	
	private String ip = DEFAULT_IP;
	private int port = DEFAULT_PORT_NUM;
	private ObjectMapper mapper = new ObjectMapper();
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public Client() {
		try {
			socket = new Socket(this.ip, this.port);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public ObjectNode action(String command, String word, String meaning) throws Exception {
		ObjectNode outputObjectNode = mapper.createObjectNode();
		outputObjectNode.put("command", command);
		outputObjectNode.put("word", word);
		outputObjectNode.put("meaning", meaning);
		String outputString = mapper.writeValueAsString(outputObjectNode);
		dataOutputStream.writeUTF(outputString);
		return mapper.readValue(dataInputStream.readUTF(), ObjectNode.class);
	}
}
