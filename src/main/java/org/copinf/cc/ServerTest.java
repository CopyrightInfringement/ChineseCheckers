package org.copinf.cc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Movement;
import org.copinf.cc.net.Request;

public class ServerTest {
	static ObjectOutputStream out;
	static ObjectInputStream in;
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(25565);

		System.out.println("Started listening on " + serverSocket);

		Socket socket = serverSocket.accept();
		System.out.println(socket.toString() + " just connected !");

		out = new ObjectOutputStream(socket.getOutputStream());
		in  = new ObjectInputStream(socket.getInputStream());

		ClientWindowTest win = new ClientWindowTest();
		win.setVisible(true);

		socket.close();
		serverSocket.close();
	}

	@SuppressWarnings("serial")
	static class ClientWindowTest extends JFrame{
		JTextField identifierField;
		JTextField contentField;
		JComboBox<String> typeBox;
		JButton sendButton;
		Pattern coordinatesPattern = Pattern.compile("\\((\\d+),(\\d+),(\\d+)\\)");
		public ClientWindowTest(){
			Container pane = this.getContentPane();
			pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

			identifierField = new JTextField();
			contentField = new JTextField();
			typeBox = new JComboBox<String>();
			sendButton= new JButton("Send");
			typeBox.addItem("Movement");
			typeBox.addItem("Boolean");
			typeBox.addItem("null");
			typeBox.addItem("Integer");

			pane.add(identifierField);
			pane.add(typeBox);
			pane.add(contentField);
			pane.add(sendButton);

			sendButton.addActionListener(new Controller());

			setPreferredSize(new Dimension(800,500));

			pack();
		}

		class Controller implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String type = (String) typeBox.getSelectedItem();
				Object content;
				if (type.equals("Boolean"))
					content = Boolean.valueOf(contentField.getText());
				else if (type.equals("null"))
					content = null;
				else if(type.equals("Integer"))
					content = Integer.valueOf(contentField.getText());
				else{
					String[] tab = contentField.getText().split(";");
					Movement movement = new Movement();
					for(String s : tab){
						Matcher match = coordinatesPattern.matcher(s);
						movement.add(new Coordinates(Integer.valueOf(match.group(1)), Integer.valueOf(match.group(2)), Integer.valueOf(match.group(1))));
					}
					content = movement;
				}
				try {
					out.writeObject(new Request(identifierField.getText(), (Serializable) content));
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
