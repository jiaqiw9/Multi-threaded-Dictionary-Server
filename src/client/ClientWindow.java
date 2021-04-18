package client;

import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.swing.JScrollPane;

public class ClientWindow {

	private JFrame frame;
	private JTabbedPane tabbedPane = null;
	private Client client = null;
	private ObjectNode inputObjectNode = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Dialog",
					        JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connect();
	}
	
	/**
	 * Establish connection to server.
	 */
	private void connect() {
		JPanel panelConnect = new JPanel(new GridBagLayout());
		JPanel panelInfo = new JPanel();
		frame.getContentPane().add(panelConnect, BorderLayout.CENTER);
		frame.getContentPane().add(panelInfo, BorderLayout.SOUTH);
		

		JLabel lblInfo = new JLabel("Please enter IP and port for the dictionary server.");
		panelInfo.add(lblInfo);
		
		JLabel lblIP = new JLabel("IP");
		panelConnect.add(lblIP);
		
		JTextField textFieldIP = new JTextField();
		panelConnect.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		panelConnect.add(lblPort);
		
		JTextField textFieldPort = new JTextField();
		panelConnect.add(textFieldPort);
		textFieldPort.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client = new Client(textFieldIP.getText(), Integer.parseInt(textFieldPort.getText()));
					initialize();
				} catch(Exception e_) {
					JOptionPane.showMessageDialog(new JFrame(), e_.getMessage(), "Dialog",
				        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelConnect.add(btnConnect);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.getContentPane().removeAll();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Query", new CommandPanel("Query"));
		tabbedPane.addTab("Add", new CommandPanel("Add"));
		tabbedPane.addTab("Remove", new CommandPanel("Remove"));
		tabbedPane.addTab("Update", new CommandPanel("Update"));
		

		tabbedPane.setForegroundAt(0, Color.BLACK);
		tabbedPane.setForegroundAt(1, Color.BLACK);
		tabbedPane.setForegroundAt(2, Color.BLACK);
		tabbedPane.setForegroundAt(3, Color.BLACK);
	}
	
	private class CommandPanel extends JPanel {
		private JLabel lblWord;
		private JTextField textFieldWord;
		private JLabel lblMeaning;
		private JScrollPane scrollPaneMeaning;
		private JTextArea textAreaMeaning;
		private JLabel lblStatus;
		private JTextField textFieldStatus;
		private JButton btnCommand;
		
		public CommandPanel(String command) {
			super();
			this.setLayout(null);
			
			lblWord = new JLabel("Word");
			lblWord.setBounds(23, 6, 61, 16);
			this.add(lblWord);
			
			textFieldWord = new JTextField();
			textFieldWord.setBounds(16, 26, 130, 26);
			this.add(textFieldWord);
			textFieldWord.setColumns(10);
			
			lblMeaning = new JLabel("Meaning");
			lblMeaning.setBounds(26, 64, 61, 16);
			this.add(lblMeaning);
			
			scrollPaneMeaning = new JScrollPane();
			scrollPaneMeaning.setBounds(16, 92, 368, 105);
			this.add(scrollPaneMeaning);
			
			textAreaMeaning = new JTextArea();
			scrollPaneMeaning.setViewportView(textAreaMeaning);
			textAreaMeaning.setLineWrap(true);
			
			lblStatus = new JLabel("Status");
			lblStatus.setBounds(245, 6, 61, 16);
			this.add(lblStatus);
			
			textFieldStatus = new JTextField();
			textFieldStatus.setBounds(240, 26, 150, 26);
			this.add(textFieldStatus);
			textFieldStatus.setColumns(10);
			
			btnCommand = new JButton(command);
			btnCommand.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						inputObjectNode = client.action(command.toLowerCase(), textFieldWord.getText(), textAreaMeaning.getText());
						if(command.equals("Query")) {
							textAreaMeaning.setText(inputObjectNode.get("meaning").asText());
						}
						textFieldStatus.setText(inputObjectNode.get("status").asText());
					} catch(Exception e_) {
						JOptionPane.showMessageDialog(new JFrame(), e_.getMessage(), "Dialog",
					        JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnCommand.setBounds(158, 26, 80, 29);
			this.add(btnCommand);
		}
	}
}
