package org.copinf.cc.view.gamepanel;

import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import org.copinf.cc.controller.TeamBuilderController;

@SuppressWarnings("serial")
public class TeamBuilderPanel extends JPanel {
	private JList<String> availablePlayers;
	public final JButton confirmButton;
	public TeamBuilderPanel(TeamBuilderController controller){
		this.availablePlayers = new JList<>();
		add(this.availablePlayers);

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(controller);
		confirmButton.setVisible(false);
		add(confirmButton);
	}
	
	public void setAvailablePlayers(Collection<String> availablePlayers){
		this.availablePlayers.setListData(availablePlayers.toArray(new String[availablePlayers.size()]));
		if(availablePlayers.size() != 0){
			this.availablePlayers.setSelectedIndex(0);
			this.availablePlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}
	
	public String getTeamMate(){
		return availablePlayers.getSelectedValuesList().get(0);
	}
	
	public void enableTeamBuiding(boolean b){
		confirmButton.setVisible(b);
	}
}
