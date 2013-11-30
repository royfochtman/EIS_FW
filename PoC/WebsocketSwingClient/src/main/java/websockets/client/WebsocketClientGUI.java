package main.java.websockets.client;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 14.11.13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class WebsocketClientGUI {
    public JFrame frame;
    public static JButton btnOpenConnection;
    public static JTextArea txt_connectionMessages;
    public static JTextArea txt_message;
    public static JTextField txt_room;

    /**
     * Create the application.
     */
    public WebsocketClientGUI() {
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
                WebsocketClient.closeConnection();
            }
        });
        btnCloseConnection.setBounds(297, 228, 127, 23);
        frame.getContentPane().add(btnCloseConnection);

        btnOpenConnection = new JButton("Open Connection");
        btnOpenConnection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                WebsocketClient.openSession();
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
                WebsocketClient.sendMessage(String.valueOf(e.getKeyChar()));
            }
        });
        txt_message.setBounds(10, 60, 414, 90);
        frame.getContentPane().add(txt_message);

        txt_room = new JTextField();
        txt_room.setBounds(10, 161, 191, 20);
        frame.getContentPane().add(txt_room);
        txt_room.setColumns(10);

        JButton btnNewButton = new JButton("Create Room");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                WebsocketClient.getRoomMessage("newRoom");
            }
        });
        btnNewButton.setBounds(211, 160, 127, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Join Room");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WebsocketClient.getRoomMessage("joinRoom");
            }
        });
        btnNewButton_1.setBounds(211, 194, 127, 23);
        frame.getContentPane().add(btnNewButton_1);
    }
}
