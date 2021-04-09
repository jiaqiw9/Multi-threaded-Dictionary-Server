package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class ClientWindow {

	private JFrame frame;
	private Client client = new Client();
	private ObjectMapper mapper = new ObjectMapper();

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
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_Query = new JPanel();
		tabbedPane.addTab("Query", null, panel_Query, null);
		panel_Query.setLayout(null);
		
		JTextField textFieldWord_Query = new JTextField();
		textFieldWord_Query.setBounds(16, 26, 130, 26);
		panel_Query.add(textFieldWord_Query);
		textFieldWord_Query.setColumns(10);
		
		JLabel lblWord_Query = new JLabel("Word");
		lblWord_Query.setBounds(23, 6, 61, 16);
		panel_Query.add(lblWord_Query);
		
		JLabel lblMeaning_Query = new JLabel("Meaning");
		lblMeaning_Query.setBounds(26, 64, 61, 16);
		panel_Query.add(lblMeaning_Query);
		
		JTextArea textAreaMeaning_Query = new JTextArea();
		textAreaMeaning_Query.setBounds(16, 92, 368, 105);
		textAreaMeaning_Query.setLineWrap(true);
		panel_Query.add(textAreaMeaning_Query);
		
		JLabel lblStatus_Query = new JLabel("Status");
		lblStatus_Query.setBounds(285, 6, 61, 16);
		panel_Query.add(lblStatus_Query);
		
		JTextField textFieldStatus_Query = new JTextField();
		textFieldStatus_Query.setBounds(282, 26, 102, 26);
		panel_Query.add(textFieldStatus_Query);
		textFieldStatus_Query.setColumns(10);
		
		JButton btn_Query = new JButton("Query");
		btn_Query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JsonNode outputJsonNode = client.action("query", textFieldWord_Query.getText(), "");
					textAreaMeaning_Query.setText(outputJsonNode.get("meaning").asText());
					textFieldStatus_Query.setText(outputJsonNode.get("status").asText());
				} catch(Exception ex) {
					
				}
			}
		});
		btn_Query.setBounds(158, 26, 67, 29);
		panel_Query.add(btn_Query);
		
		JPanel panel_Add = new JPanel();
		tabbedPane.addTab("Add", null, panel_Add, null);
		panel_Add.setLayout(null);
		
		JTextField textFieldWord_Add = new JTextField();
		textFieldWord_Add.setColumns(10);
		textFieldWord_Add.setBounds(16, 26, 130, 26);
		panel_Add.add(textFieldWord_Add);
		
		JLabel lblWord_Add = new JLabel("Word");
		lblWord_Add.setBounds(23, 6, 61, 16);
		panel_Add.add(lblWord_Add);
		
		JLabel lblMeaning_Add = new JLabel("Meaning");
		lblMeaning_Add.setBounds(26, 64, 61, 16);
		panel_Add.add(lblMeaning_Add);
		
		JTextArea textAreaMeaning_Add = new JTextArea();
		textAreaMeaning_Add.setBounds(16, 92, 368, 105);
		textAreaMeaning_Add.setLineWrap(true);
		panel_Add.add(textAreaMeaning_Add);
		
		JLabel lblStatus_Add = new JLabel("Status");
		lblStatus_Add.setBounds(285, 6, 61, 16);
		panel_Add.add(lblStatus_Add);
		
		JTextField textFieldStatus_Add = new JTextField();
		textFieldStatus_Add.setBounds(282, 26, 102, 26);
		panel_Add.add(textFieldStatus_Add);
		textFieldStatus_Add.setColumns(10);
		
		JButton btn_Add = new JButton("Add");
		btn_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JsonNode outputJsonNode = client.action("add", textFieldWord_Add.getText(), textAreaMeaning_Add.getText());
					textFieldStatus_Add.setText(outputJsonNode.get("status").asText());
				} catch(Exception ex) {
					
				}
			}
		});
		btn_Add.setBounds(158, 26, 67, 29);
		panel_Add.add(btn_Add);
		
		JPanel panel_Remove = new JPanel();
		tabbedPane.addTab("Remove", null, panel_Remove, null);
		panel_Remove.setLayout(null);
		
		JTextField textFieldWord_Remove = new JTextField();
		textFieldWord_Remove.setColumns(10);
		textFieldWord_Remove.setBounds(16, 26, 130, 26);
		panel_Remove.add(textFieldWord_Remove);
		
		JLabel lblWord_Remove = new JLabel("Word");
		lblWord_Remove.setBounds(23, 6, 61, 16);
		panel_Remove.add(lblWord_Remove);
		
		JLabel lblStatus_Remove = new JLabel("Status");
		lblStatus_Remove.setBounds(285, 6, 61, 16);
		panel_Remove.add(lblStatus_Remove);
		
		JTextField textFieldStatus_Remove = new JTextField();
		textFieldStatus_Remove.setBounds(282, 26, 102, 26);
		panel_Remove.add(textFieldStatus_Remove);
		textFieldStatus_Remove.setColumns(10);
		
		JButton btn_Remove = new JButton("Remove");
		btn_Remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JsonNode outputJsonNode = client.action("remove", textFieldWord_Remove.getText(), "");
					textFieldStatus_Remove.setText(outputJsonNode.get("status").asText());
				} catch(Exception ex) {
					
				}
				
			}
		});
		btn_Remove.setBounds(158, 26, 78, 29);
		panel_Remove.add(btn_Remove);
		
		JPanel panel_Update = new JPanel();
		tabbedPane.addTab("Update", null, panel_Update, null);
		panel_Update.setLayout(null);
		
		JTextField textFieldWord_Update = new JTextField();
		textFieldWord_Update.setColumns(10);
		textFieldWord_Update.setBounds(16, 26, 130, 26);
		panel_Update.add(textFieldWord_Update);
		
		JLabel lblWord_Update = new JLabel("Word");
		lblWord_Update.setBounds(23, 6, 61, 16);
		panel_Update.add(lblWord_Update);
		
		JLabel lblMeaning_Update = new JLabel("Meaning");
		lblMeaning_Update.setBounds(26, 64, 61, 16);
		panel_Update.add(lblMeaning_Update);
		
		JTextArea textAreaMeaning_Update = new JTextArea();
		textAreaMeaning_Update.setBounds(16, 92, 368, 105);
		textAreaMeaning_Update.setLineWrap(true);
		panel_Update.add(textAreaMeaning_Update);
		
		JLabel lblStatus_Update = new JLabel("Status");
		lblStatus_Update.setBounds(285, 6, 61, 16);
		panel_Update.add(lblStatus_Update);
		
		JTextField textFieldStatus_Update = new JTextField();
		textFieldStatus_Update.setBounds(282, 26, 102, 26);
		panel_Update.add(textFieldStatus_Update);
		textFieldStatus_Update.setColumns(10);
		
		JButton btn_Update = new JButton("Update");
		btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JsonNode outputJsonNode = client.action("Update", textFieldWord_Update.getText(), textAreaMeaning_Update.getText());
					textFieldStatus_Update.setText(outputJsonNode.get("status").asText());
				} catch(Exception ex) {
					
				}
			}
		});
		btn_Update.setBounds(158, 26, 88, 29);
		panel_Update.add(btn_Update);
	}

}
