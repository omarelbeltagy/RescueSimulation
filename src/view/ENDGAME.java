package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ENDGAME extends JFrame{
   public ENDGAME(int x){
	   this.setVisible(true);
		this.validate();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("THANK YOU");
		this.setLocation(40000,5000);
		this.setSize(2000,2000);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		JTextArea k = new JTextArea();
		JPanel kk = new JPanel();
		this.add(kk , BorderLayout.CENTER );
	
		k.setVisible(true);
		k.setPreferredSize(new Dimension(this.getWidth() - 20, 200));
		k.setText("GAME OVER     (︶︹︺) "+"\n"+"\n"+"\n"+"YOUR SCORE: " + x + "\n" + "\n" + "\n"+ "\n" + "SEE YOU!");
		k.setEditable(false);
		k.setFont(new Font("", Font.BOLD, 16));
		k.setBackground(Color.BLACK);
		k.setForeground(Color.WHITE);
		kk.add(k);
		
   }
}
