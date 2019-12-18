package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.people.*;
import controller.*;


public class BuildingsView extends JFrame {
	JButton c;
	ArrayList<JButton> cList;

	public ArrayList<JButton> getcList() {
		return cList;
	}

	public BuildingsView(int x , int y ,ArrayList<Citizen> citizens,ActionListener action){
		cList = new ArrayList<JButton>();
		this.setVisible(true);
		this.validate();
		this.setTitle("BUILDING AT LOCATION (" +x+","+y+")");
		this.setLocation(400,100);
		this.setSize(700,500);
		this.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		JPanel grid = new JPanel();
		grid.setSize(new Dimension(500, 500));
		grid.setVisible(true);
		this.add(grid, BorderLayout.CENTER);
		grid.setVisible(true);
		grid.setLayout(new GridLayout(10,10));
		for(int i=0 ; i<citizens.size();i++){
			c = new JButton();
			c.setSize(new Dimension(300, 300));
			c.setText("CITIZEN");
			c.setBackground(Color.PINK);
			c.setForeground(Color.BLACK);
			c.setVisible(true);
			c.addActionListener(action);
			grid.add(c);
			cList.add(c);
			
		}
		
	}
	
}
