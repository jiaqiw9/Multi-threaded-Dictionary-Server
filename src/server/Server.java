package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Server {
	public static final int DEFAULT_PORT_NUM = 3005;
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static Map<String, String> dictionary;
	private static int clientCount = 0;
	
	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("Incorrect number of arguments. The parameters are initialsed to be the defaults.");
		}
		int port;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.err.println("The port number has to be an integer.");
			port = DEFAULT_PORT_NUM;
			System.out.println("The default port is assigned be " + Integer.toString(port) + ".");
		}
		try {
			dictionary = mapper.readValue(new File(args[1]), Map.class);
		} catch(Exception e) {
			System.err.println("Failure to read the dictionary file.");
			try {
				dictionary = mapper.readValue("{}", Map.class);
				System.out.println("A empty dictionary is created.");
			} catch(Exception e_) {
				System.exit(0);
			}
		}
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			while(true) {
				Socket socket = serverSocket.accept();
				Thread t = new Thread(() -> serveClient(socket));
				t.start();
				clientCount ++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	private static void serveClient(Socket socket) {
		String inputString = null;
		int clientNum = clientCount;
		System.out.println("Client " + Integer.toString(clientNum) + " is connected.");
		try {
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			while((inputString = dataInputStream.readUTF()) != null) {
				try {
					ObjectNode inputObjectNode = mapper.readValue(inputString, ObjectNode.class);
					ObjectNode outputObjectNode = mapper.createObjectNode();
					String command = inputObjectNode.get("command").asText();
					String word = inputObjectNode.get("word").asText().toUpperCase();
					String meaning = inputObjectNode.get("meaning").asText();
					if(word.length() == 0) {
						outputObjectNode.put("status", "No word is provided");
						String outputString = mapper.writeValueAsString(outputObjectNode);
						dataOutputStream.writeUTF(outputString);
						throw new Exception("No word is provided");
					}
					if(command.equals("query")) {
						String outputMeaning = query(word);
						if(outputMeaning.length() == 0) {
							outputObjectNode.put("status", "The word is not found");
						} else {
							outputObjectNode.put("meaning", outputMeaning);
							outputObjectNode.put("status", "Success.");
						}
					} else if(command.equals("add")) {
						if(meaning.length() == 0) {
							outputObjectNode.put("status", "Attempting to add a word without an associated meaning.");
						} else {
							if(containsKey(word)) {
								outputObjectNode.put("status", "Duplicate.");
							} else {
								add(word, meaning);
								outputObjectNode.put("status", "Success.");
							}
						}
					} else if(command.equals("remove")) {
						if(containsKey(word)) {
							remove(word);
							outputObjectNode.put("status", "Success.");
						} else {
							outputObjectNode.put("status", "The word is not found.");
						}
					} else if(command.equals("update")) {
						if(meaning.length() == 0) {
							outputObjectNode.put("status", "Attempting to update a word without an associated meaning.");
						} else {
							if(containsKey(word)) {
								update(word, meaning);
								outputObjectNode.put("status", "Success.");
							} else {
								outputObjectNode.put("status", "The word is not found");
							}
						}
					}
					String outputString = mapper.writeValueAsString(outputObjectNode);
					dataOutputStream.writeUTF(outputString);
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (EOFException eofe) {
			System.out.println("Client " + Integer.toString(clientNum) + " is disconnected.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	
	private synchronized static String query(String word) {
		return dictionary.get(word);
	}
	private synchronized static void add(String word, String meaning) {
		dictionary.put(word, meaning);
	}
	private synchronized static void remove(String word) {
		dictionary.remove("word");
	}
	private synchronized static void update(String word, String meaning) {
		dictionary.replace(word, meaning);
	}
	private synchronized static boolean containsKey(String word) {
		return dictionary.containsKey(word);
	}

}
