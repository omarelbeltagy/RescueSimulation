package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javafx.scene.control.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ExceptionView extends JFrame { 
	public ExceptionView(String m ){
	this.setVisible(true);
	this.validate();
	this.setTitle("SORRY");
	this.setLocation(400,100);
	this.setSize(600,400);

	 JTextArea x = new JTextArea(); 
	 this.add(x,BorderLayout.CENTER);
	 x.setVisible(true);
	 x.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
	 x.setText(m);
	 
	 x.setEditable(false);
	 x.setFont(new Font("" , Font.BOLD , 20));
	  
	
	}
	}


