package main.java.WebSocket.Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;

public class WebSocketClientSwing {

	public JFrame frame;
	public static JButton btnOpenConnection;
	public static JTextArea txt_connectionMessages;
	public static JTextArea txt_message;

	/**
	 * Create the application.
	 */
	public WebSocketClientSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCloseConnection = new JButton("Close Connection");
		btnCloseConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebSocketClientEndpoint.closeConnection();
			}
		});
		btnCloseConnection.setBounds(297, 228, 127, 23);
		frame.getContentPane().add(btnCloseConnection);
		
		btnOpenConnection = new JButton("Open Connection");
		btnOpenConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebSocketClientEndpoint.openSession();
			}
		});
		btnOpenConnection.setBounds(165, 228, 122, 23);
		frame.getContentPane().add(btnOpenConnection);
		
		txt_connectionMessages = new JTextArea();
		txt_connectionMessages.setBounds(10, 11, 414, 43);
		frame.getContentPane().add(txt_connectionMessages);
		
		txt_message = new JTextArea();
		txt_message.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				WebSocketClientEndpoint.sendMessage(String.valueOf(e.getKeyChar()));
			}
		});
		txt_message.setBounds(10, 60, 414, 157);
		frame.getContentPane().add(txt_message);
	}
}
