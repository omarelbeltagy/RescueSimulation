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

import model.units.*;
import model.people.*;

public class UnitsView extends JFrame {
	JButton unitsB;
	JButton citizenB;
	ArrayList<JButton> unitsList;
	ArrayList<JButton> citizensList;
	ArrayList<Citizen> BaseCitizens;
	ArrayList<Unit> BaseUnits;
	public ArrayList<JButton> getUnitsList() {
		return unitsList;
	}
	public ArrayList<JButton> getCitizensList() {
		return citizensList;
	}
	public UnitsView(ArrayList<Unit> units,ArrayList<Citizen> citizens, ActionListener action){
		this.setVisible(true);
		this.validate();
		this.setTitle("LOCATION(0,0)");
		this.setLocation(400,100);
		this.setSize(700,500);
		this.setLayout(new BorderLayout());
		JPanel grid = new JPanel();
		grid.setSize(new Dimension(500, 500));
		grid.setVisible(true);
		this.add(grid, BorderLayout.CENTER);
		grid.setVisible(true);
		grid.setLayout(new GridLayout(10,10));
	   unitsList = new ArrayList<JButton>();
	   citizensList = new ArrayList<JButton>();
	   BaseUnits = new ArrayList<Unit>();
	   
		for(int i=0 ; i<units.size();i++){
			if(units.get(i).getLocation().getX()==0 && units.get(i).getLocation().getY()==0){
			unitsB = new JButton();
			unitsB.setSize(new Dimension(300, 300));
			
			if (units.get(i) instanceof Ambulance)
				unitsB.setText("AMBULANCE");
			if (units.get(i) instanceof DiseaseControlUnit)
				unitsB.setText("DISEASE CONTROL UNIT");
			if (units.get(i) instanceof Evacuator)
				unitsB.setText("EVACUATOR");
			if (units.get(i) instanceof FireTruck)
				unitsB.setText("FIRE TRUCK");
			if (units.get(i) instanceof GasControlUnit)
				unitsB.setText("GAS CONTROL UNIT");
			unitsB.setBackground(Color.DARK_GRAY);
			unitsB.setForeground(Color.WHITE);
			unitsB.setVisible(true);
			unitsB.addActionListener(action);
			grid.add(unitsB);
			unitsList.add(unitsB);
			BaseUnits.add(units.get(i));}
		}
		for(int i=0 ; i<citizens.size();i++){
			if(citizens.get(i).getLocation().getX()==0 && citizens.get(i).getLocation().getY()==0){
			citizenB = new JButton();
		    citizenB.setSize(new Dimension(300, 300));
			citizenB.setText("CITIZEN");
			citizenB.setBackground(Color.PINK);
			citizenB.setForeground(Color.BLACK);
			citizenB.setVisible(true);
			citizenB.addActionListener(action);
			grid.add(citizenB);
			citizensList.add(citizenB);}
		}
		
		
		
	
}
	public ArrayList<Citizen> getBaseCitizens() {
		return BaseCitizens;
	}
	public ArrayList<Unit> getBaseUnits() {
		return BaseUnits;
	}
}