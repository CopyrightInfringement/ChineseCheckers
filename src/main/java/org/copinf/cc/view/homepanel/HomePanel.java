package org.copinf.cc.view.homepanel;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	private JButton btnJoin;
	private JButton btnHost;
	private JTextField hostTextField;
	private JTextField portTextField;
	private JLabel lblErrors;

	private final int OFFSET_X = 10;
	private final int OFFSET_Y = 10;

	private final String DEBUG_HOST = "localhost";
	private final String DEBUG_PORT = "25565";

	public HomePanel() {
		super();
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblTitle = new JLabel("<html><p style='font-size:20px;text-align:center;'>Chinese Checkers</p></html>");
		springLayout.putConstraint(SpringLayout.NORTH, lblTitle, OFFSET_Y, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblTitle);

		JLabel lblHost = new JLabel("Host");
		springLayout.putConstraint(SpringLayout.NORTH, lblHost, OFFSET_Y, SpringLayout.SOUTH, lblTitle);
		springLayout.putConstraint(SpringLayout.WEST, lblHost, OFFSET_X, SpringLayout.WEST, this);
		add(lblHost);

		hostTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, hostTextField, 0, SpringLayout.NORTH, lblHost);
		springLayout.putConstraint(SpringLayout.WEST, hostTextField, OFFSET_X, SpringLayout.EAST, lblHost);
		add(hostTextField);
		hostTextField.setColumns(20);
		hostTextField.setText(DEBUG_HOST);

		JLabel lblPort = new JLabel("Port");
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, OFFSET_Y, SpringLayout.SOUTH, lblHost);
		springLayout.putConstraint(SpringLayout.WEST, lblPort, OFFSET_X, SpringLayout.WEST, this);
		add(lblPort);

		portTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, portTextField, 0, SpringLayout.NORTH, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, portTextField, 0, SpringLayout.WEST, hostTextField);
		springLayout.putConstraint(SpringLayout.EAST, portTextField, 0, SpringLayout.EAST, hostTextField);
		add(portTextField);
		portTextField.setColumns(20);
		portTextField.setText(DEBUG_PORT);

		btnJoin = new JButton("Join");
		springLayout.putConstraint(SpringLayout.NORTH, btnJoin, OFFSET_Y, SpringLayout.SOUTH, lblPort);
		springLayout.putConstraint(SpringLayout.EAST, btnJoin, -OFFSET_X, SpringLayout.HORIZONTAL_CENTER, this);
		add(btnJoin);

		btnHost = new JButton("Host");
		springLayout.putConstraint(SpringLayout.NORTH, btnHost, 0, SpringLayout.NORTH, btnJoin);
		springLayout.putConstraint(SpringLayout.WEST, btnHost, OFFSET_X, SpringLayout.HORIZONTAL_CENTER, this);
		add(btnHost);

		lblErrors = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, lblErrors, OFFSET_Y, SpringLayout.SOUTH, btnJoin);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblErrors, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblErrors);
		springLayout.putConstraint(SpringLayout.WEST, lblErrors, OFFSET_X, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblErrors, -OFFSET_X, SpringLayout.EAST, this);

		JLabel lblCredits = new JLabel("<html><p style='text-align: center;'>Louis Bal dit Sollier<br>Clara Bringer<br>Antonin Décimo<br>Pierre Gervais</p></html>");
		springLayout.putConstraint(SpringLayout.NORTH, lblCredits, OFFSET_Y, SpringLayout.SOUTH, lblErrors);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblCredits, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblCredits);

		springLayout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, hostTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, lblCredits);
	}

	public JButton getHostButton() {
		return btnHost;
	}

	public JButton getJoinButton() {
		return btnJoin;
	}

	public String getHost() {
		return hostTextField.getText();
	}

	public int getPort() {
		return Integer.parseInt(portTextField.getText());
	}

	public void displayErrorMessage(String msg) {
		lblErrors.setText("<html><p style='color:red;'>" + msg + "</p></html>");
	}

	public void resetErrorMessage() {
		lblErrors.setText("");
	}
}
