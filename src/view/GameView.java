package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import controller.CommandCenter;
import controller.CommandCenter.handeler;
import simulation.Address;
import model.units.*;
import model.people.*;

public class GameView extends JFrame {
	// CommandCenter c = new CommandCenter();
	private JPanel grid;
	private JButton Building;
	private JTextArea simInfo;
	private JPanel info;
	private JButton next;
	private JTextArea infoUp;
	private JPanel unitPanel;
	private JButton unit;
	private JButton x;
	private JButton base;
	private JButton citizen = new JButton();
	private ArrayList<JButton> BuildingsList;
	private ArrayList<JButton> UnitsList;
	private ArrayList<JButton> citizenList;
	private ArrayList<JButton> xButtonsList;
	
	public JButton getCitizen() {
		return citizen;
	}

	public ArrayList<JButton> getCitizenList() {
		return citizenList;
	}

	public JPanel getGrid() {
		return grid;
	}

	public JButton getBuilding() {
		return Building;
	}

	public JTextArea getSimInfo() {
		return simInfo;
	}

	public JPanel getInfo() {
		return info;
	}

	public JButton getNext() {
		return next;
	}

	public JTextArea getInfoUp() {
		return infoUp;
	}

	public JPanel getUnitPanel() {
		return unitPanel;
	}

	public JButton getUnit() {
		return unit;
	}

	public boolean buildingLocation(ArrayList<ResidentialBuilding> buildings,int x , int y) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation().getX()==x &&buildings.get(i).getLocation().getY()==y) {
				return true;
			}
		}
		return false;
	}
	public int buildingLocationNum(ArrayList<ResidentialBuilding> buildings,int x , int y) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation().getX()==x &&buildings.get(i).getLocation().getY()==y) {
				return i;
			}
		}
		return -1;
	}

	public boolean CitizenLocation(ArrayList<Citizen> citizens, int x ,int y) {
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getLocation().getX()==x && citizens.get(i).getLocation().getY()==y)
				return true;
		}

		return false;

	}

	public GameView(ActionListener action,
			ArrayList<ResidentialBuilding> buildings, ArrayList<Unit> unitscsv,
			ArrayList<Citizen> citizenscsv) throws Exception {
		this.setVisible(true);
		this.validate();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("EL DEMENTE");
		this.setLocation(400, 200);
		this.setSize(1650, 1080);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());

		grid = new JPanel();
		grid.setSize(new Dimension(500, 500));
		grid.setVisible(true);
		this.add(grid, BorderLayout.CENTER);
		grid.setVisible(true);
		GridLayout world = new GridLayout(10,10);
		grid.setLayout(world);
		BuildingsList = new ArrayList<JButton>();
		citizenList = new ArrayList<JButton>();
		xButtonsList = new ArrayList<JButton>();
		
		
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (buildingLocation(buildings,i,j)) {
					Building = new JButton();
					Building.setSize(new Dimension(300, 300));
					Building.setText("BUILDING");
					Building.setBackground(Color.BLACK);
					Building.setForeground(Color.GRAY);
					Building.addActionListener(action);
					Building.setVisible(true);
					grid.add(Building);
					BuildingsList.add(Building);
					
				} 
				
//					if (CitizenLocation(citizenscsv, i,j)) {
//						if (buildingLocation(buildings,i,j)) 
//							Building.setText(Building.getText() + ","+"citizen");
//						
//						else{
//						citizen = new JButton();
//						citizen.setSize(new Dimension(150, 150));
//						citizen.setText("CITIZEN");
//						citizen.setBackground(Color.GREEN);
//						citizen.setForeground(Color.BLACK);
//						citizen.addActionListener(action);
//						citizen.setVisible(true);
//						grid.add(citizen);
//						citizenList.add(citizen);}
//					}
					
					else {
					x = new JButton();
					x.setSize(new Dimension(300, 300));
					x.addActionListener(action);
					x.setVisible(true); // ask game room
					x.setText(i+","+j);
					grid.add(x);
					xButtonsList.add(x);
				

			}
						}
					}
		
//		for(int i=0 ; i<10 ; i++){
//			for(int j=0 ; j<10;j++){
//				if(CitizenLocation(citizenscsv, i, j)){
//					if(buildingLocation(buildings, i, j)){
//						
//						int k =0;
//						for(k=0 ; k<buildings.size();k++){
//							if(buildings.get(k).getLocation().getX()==i &&buildings.get(k).getLocation().getY()==j)
//								break;
//						}
//						for(int h = 0;h<buildings.get(k).getOccupants().size();h++ ){
//						BuildingsList.get(k).setText(BuildingsList.get(k).getText()+","+"Citizen");
//						citizen = new JButton();
//						citizenList.add(citizen);
//						
//						}
//						
//					}
//					else{
//						for(int k=0 ; k<xButtonsList.size() ; k++){
//							if(xButtonsList.get(k).getText().equals(i+","+j)){
//								//System.out.println(xButtonsList.get(k).getText());
//						           xButtonsList.get(k).setBackground(Color.green);
//						           xButtonsList.get(k).setText("CITIZEN");citizen.setForeground(Color.BLACK);
//						           xButtonsList.get(k).setVisible(true);
//						           citizen = xButtonsList.get(k);
//								   citizenList.add(citizen);
//								   
//							}
//					
//						}
//						
//					}
//				}
//			}
//		}
		
		for(int i=0 ; i<citizenscsv.size();i++){
			if(buildingLocation(buildings, citizenscsv.get(i).getLocation().getX(),citizenscsv.get(i).getLocation().getY() )){
				int x = buildingLocationNum(buildings, citizenscsv.get(i).getLocation().getX(),citizenscsv.get(i).getLocation().getY());
				if(x>-1){
				    if(BuildingsList.get(x).getText().equals("BUILDING")){
					BuildingsList.get(x).setText(BuildingsList.get(x).getText()+","+"Citizen");}
					citizen = new JButton();
					citizenList.add(citizen);
				   // System.out.print(x + " ");
					
				
				}
				
			}
			else{
				for(int k=0 ; k<xButtonsList.size() ; k++){
					if(xButtonsList.get(k).getText().equals(citizenscsv.get(i).getLocation().getX()+","+citizenscsv.get(i).getLocation().getY())){
					//	System.out.println(xButtonsList.get(k).getText());
				           xButtonsList.get(k).setBackground(Color.PINK);
				           xButtonsList.get(k).setForeground(Color.BLACK);
				           xButtonsList.get(k).setText("CITIZEN");
				           xButtonsList.get(k).setVisible(true);
				           citizen = xButtonsList.get(k);
						   citizenList.add(citizen);
						   
					}
			
				}
				
				
			}
		}
		//System.out.println();
		//System.out.println(citizenList.size());
		for(int i=0 ; i<xButtonsList.size();i++){
			if(xButtonsList.get(i).getText().equals("0,0")){
				xButtonsList.get(i).setText("BASE");
				xButtonsList.get(i).setBackground(Color.BLUE);
				xButtonsList.get(i).setForeground(Color.WHITE);
				
				
			}
		}
		
		

	

		simInfo = new JTextArea();
		this.add(simInfo, BorderLayout.EAST);
		simInfo.setVisible(true);
		simInfo.setPreferredSize(new Dimension(500, this.getHeight()));
		simInfo.setText("SIMULATION INFO \n");

		simInfo.setEditable(false);
		simInfo.setFont(new Font("", Font.BOLD, 20));
		;

		info = new JPanel();
		this.add(info, BorderLayout.NORTH);
		info.setSize(new Dimension(500, this.getWidth()));
		info.setVisible(true);

		next = new JButton();
		next.addActionListener(action);
		next.setSize(new Dimension(100, 100));
		next.setText("NEXT CYCLE");
		next.setBackground(Color.BLUE);
		next.setForeground(Color.WHITE);

		infoUp = new JTextArea();
		infoUp.setVisible(true);
		infoUp.setPreferredSize(new Dimension(this.getWidth() - 20, 200));
		infoUp.setText("INFO" + "\n");
		infoUp.setEditable(false);
		infoUp.setFont(new Font("", Font.BOLD, 16));
		infoUp.setBackground(Color.DARK_GRAY);
		infoUp.setForeground(Color.WHITE);
		info.add(next);
		info.add(infoUp);

		UnitsList = new ArrayList<JButton>();
		unitPanel = new JPanel();
		unitPanel.setSize(new Dimension(500, 500));
		unitPanel.setVisible(true);
		this.add(unitPanel, BorderLayout.SOUTH);
		unitPanel.setVisible(true);
		GridLayout units = new GridLayout(5, 10);
		unitPanel.setLayout(units);
		for (int j = 0; j < unitscsv.size(); j++) {

			unit = new JButton();
			unit.setSize(new Dimension(300, 300));
			if (unitscsv.get(j) instanceof Ambulance)
				unit.setText("AMBULANCE");
			if (unitscsv.get(j) instanceof DiseaseControlUnit)
				unit.setText("DISEASE CONTROL UNIT");
			if (unitscsv.get(j) instanceof Evacuator)
				unit.setText("EVACUATOR");
			if (unitscsv.get(j) instanceof FireTruck)
				unit.setText("FIRE TRUCK");
			if (unitscsv.get(j) instanceof GasControlUnit)
				unit.setText("GAS CONTROL UNIT");

			unit.setBackground(Color.DARK_GRAY);
			unit.setForeground(Color.WHITE);
			unit.addActionListener(action);
			UnitsList.add(unit);
			unitPanel.add(unit);

		}

	}

	public ArrayList<JButton> getxButtonsList() {
		return xButtonsList;
	}

	public ArrayList<JButton> getUnitsList() {
		return UnitsList;
	}

	public ArrayList<JButton> getBuildingsList() {
		return BuildingsList;
	}

	public static void main(String[] args) throws Exception {
		// GameView m = new GameView();
		CommandCenter c = new CommandCenter();
		handeler x = c.new handeler();
	}

}
