package org.copinf.cc.view.gamepanel;

import org.copinf.cc.view.gamepanel.DrawZone;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class SlidersRotationWindow extends JFrame implements ChangeListener {

	private DrawZone panel;

	private JSlider sliderf0;
	private JSlider sliderf1;
	private JSlider sliderf2;
	private JSlider sliderf3;

	public SlidersRotationWindow(final DrawZone panel) {
		super();
		this.panel = panel;

		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(2, 2, 10, 10));
		sliderf0 = new JSlider(-1000, 1000);
		sliderf1 = new JSlider(-1000, 1000);
		sliderf2 = new JSlider(-1000, 1000);
		sliderf3 = new JSlider(-1000, 1000);
		sliderf0.addChangeListener(this);
		sliderf1.addChangeListener(this);
		sliderf2.addChangeListener(this);
		sliderf3.addChangeListener(this);
		pane.add(sliderf0);
		pane.add(sliderf1);
		pane.add(sliderf2);
		pane.add(sliderf3);

		setContentPane(pane);
		validate();
		pack();
		setVisible(true);
	}

	@Override
	public void stateChanged(final ChangeEvent e) {
		BoardView boardView = panel.getBoardView();
		BoardView.Layout layout = boardView.getLayout();
		BoardView.Orientation orientation = layout.getOrientation();
		orientation.f0 = (double) sliderf0.getValue() / (double) sliderf0.getMaximum() * Math.PI;
		orientation.f1 = (double) sliderf1.getValue() / (double) sliderf1.getMaximum() * Math.PI;
		orientation.f2 = (double) sliderf2.getValue() / (double) sliderf2.getMaximum() * Math.PI;
		orientation.f3 = (double) sliderf3.getValue() / (double) sliderf3.getMaximum() * Math.PI;
		panel.repaint();
	}
}
