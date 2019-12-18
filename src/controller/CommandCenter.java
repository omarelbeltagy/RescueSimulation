package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javafx.scene.control.TextArea;

import javax.swing.JFrame;
import javax.swing.text.View;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import model.disasters.*;
import view.GameView;
import model.units.*;
import view.*;

public class CommandCenter implements SOSListener {

	 
	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;

	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();

	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}
	
	public class handeler implements ActionListener{
		GameView view = new GameView(this,engine.getBuildings() , engine.getEmergencyUnits(),engine.getCitizens());
		BuildingsView BView;
		boolean flag;
		Unit respondingUnit;
		ResidentialBuilding clicked;
		UnitsView BaseView;
		public handeler() throws Exception{
			
		}
		public boolean isOccupant(Citizen c){
			for(int i=0; i<engine.getBuildings().size();i++){
			if(engine.getBuildings().get(i).getLocation().getX()==c.getLocation().getX()&&engine.getBuildings().get(i).getLocation().getY()==c.getLocation().getY()){
				return true;
				}
			}
			return false;
		}

		
		public void actionPerformed(ActionEvent e){
			
			for(int i=0;i<view.getxButtonsList().size();i++){
				if(e.getSource() == view.getxButtonsList().get(i)){
				if(view.getxButtonsList().get(i).getText().equals("BASE")){
					UnitsView x = new UnitsView(engine.getEmergencyUnits() , engine.getCitizens(),this);
					/////////////***************************8\\\\\\\\\\\\\\\\
				}}
			}
			
			
	   
			if(e.getSource()==view.getNext()){
				if(engine.checkGameOver()){
					ENDGAME X = new ENDGAME(engine.calculateCasualties());
					view.setVisible(false);
				}
	             flag =false; 	 
				try {
						engine.nextCycle();
					} catch (BuildingAlreadyCollapsedException
							| CitizenAlreadyDeadException e1) {
						// TODO Auto-generated catch block
						//view.getInfoUp().setText(e1.getMessage());
						ExceptionView ee = new ExceptionView("");
						
					}
				
				for(int i=0 ; i<view.getUnitsList().size();i++){
					if(engine.getEmergencyUnits().get(i).getState() != UnitState.IDLE){
						view.getUnitsList().get(i).setBackground(Color.LIGHT_GRAY);
					}
					if(engine.getEmergencyUnits().get(i).getState() == UnitState.IDLE){
						view.getUnitsList().get(i).setBackground(Color.DARK_GRAY);
					}
					
				}
	             	view.getSimInfo().setText("Current Cycle:" + engine.getCurrentCycle()+"\n"); 
	                view.getSimInfo().append("CASUALTIES: "  + engine.calculateCasualties()+"\n");
	                view.getInfoUp().setText("");
	                for(int i=0 ; i<view.getBuildingsList().size();i++){
	                	if(engine.getBuildings().get(i).getDisaster() != null){
	                		view.getBuildingsList().get(i).setBackground(Color.RED);
	                	}
	                	if(engine.getBuildings().get(i).getFireDamage()==0 &&engine.getBuildings().get(i).getGasLevel()==0 && engine.getBuildings().get(i).getFoundationDamage()==0){
	                		view.getBuildingsList().get(i).setBackground(Color.BLACK);
	                	}
	                	if(engine.getBuildings().get(i).getStructuralIntegrity() ==0){
	                		view.getBuildingsList().get(i).setBackground(Color.BLUE);
	                		view.getBuildingsList().get(i).setText("Collapsed one");
	                		//view.getSimInfo().append("Citizens at Location(" + engine.getBuildings().get(i).getLocation().getX() +"," + engine.getBuildings().get(i).getLocation().getY()+") have passeed away \n");
	                	}
	                	
	                }
	                for(int i=0 ; i<view.getCitizenList().size();i++){
	                	if(engine.getCitizens().get(i).getDisaster() != null){
	                		view.getCitizenList().get(i).setBackground(Color.RED);
	                		
	                	}
	                		
	
	                	if(engine.getCitizens().get(i).getHp()==0){
	                		view.getCitizenList().get(i).setBackground(Color.BLACK);
	                		view.getCitizenList().get(i).setForeground(Color.WHITE);
	                		view.getCitizenList().get(i).setText("RIP");
	                		//view.getSimInfo().append("Citizen at Location(" + engine.getCitizens().get(i).getLocation().getX() +"," + engine.getCitizens().get(i).getLocation().getY()+") has passeed away \n");
	                		
	                	}
	                }
	                String dis = "Current Cycle: " + engine.getCurrentCycle()+"\n";
	                dis = dis + "Casualties number: " + engine.calculateCasualties()+"\n";
	                 dis = dis+"Currently Active Disasters: \n";
	                for(int i=0 ; i<engine.getExecutedDisasters().size();i++){
	                	
	                	if(engine.getExecutedDisasters().get(i).isActive()){
	                		if(engine.getExecutedDisasters().get(i) instanceof Collapse){
	                			dis = dis + "((Collapse) Target: " ;
	                			if(engine.getExecutedDisasters().get(i).getTarget() instanceof ResidentialBuilding)
		                			dis = dis+"Building at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
		                		
		                		else
		                			dis = dis+"Citizen at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
	                			
	                			}
	                		if(engine.getExecutedDisasters().get(i) instanceof Fire){
	                			dis = dis + "((Fire) " ;
	                			if(engine.getExecutedDisasters().get(i).getTarget() instanceof ResidentialBuilding)
		                			dis = dis+"Building at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
		                		
		                		else
		                			dis = dis+"Citizen at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
}
	                		if(engine.getExecutedDisasters().get(i) instanceof GasLeak){
	                			dis = dis + "((Gas Leak)" ;
	                			if(engine.getExecutedDisasters().get(i).getTarget() instanceof ResidentialBuilding)
		                			dis = dis+"Building at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
		                		
		                		else
		                			dis = dis+"Citizen at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
	                			}
	                		if(engine.getExecutedDisasters().get(i) instanceof Infection){
	                			if(engine.getExecutedDisasters().get(i).getTarget() instanceof ResidentialBuilding)
		                			dis = dis+"Building at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
		                		
		                		else
		                			dis = dis+"Citizen at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
	                			dis = dis + "((Infection) \n" ;}
	                		if(engine.getExecutedDisasters().get(i) instanceof Injury){
		                		dis = dis + "((Injury) Target: " ;
		                		if(engine.getExecutedDisasters().get(i).getTarget() instanceof ResidentialBuilding)
		                			dis = dis+"Building at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
		                		
		                		else
		                			dis = dis+"Citizen at location(" +engine.getExecutedDisasters().get(i).getTarget().getLocation().getX()+"," + engine.getExecutedDisasters().get(i).getTarget().getLocation().getY()+"))\n"   ;
		                		}
	                		
	                		
	                	}
	                }
	                for(int i=0 ; i<engine.getBuildings().size();i++){
	                	if(engine.getBuildings().get(i).getStructuralIntegrity()==0){
	                		dis = dis+"Citizens at Location(" + engine.getBuildings().get(i).getLocation().getX()+","+engine.getBuildings().get(i).getLocation().getY()+") have passed away \n";
	                	}
	                }
	                for(int i=0 ; i<engine.getCitizens().size();i++){
	                	if(engine.getCitizens().get(i).getHp()==0){
	                		dis = dis+"Citizen at Location(" + engine.getCitizens().get(i).getLocation().getX()+","+engine.getCitizens().get(i).getLocation().getY()+") has passed away \n";
	                		
	                	}
	                }
	                view.getSimInfo().setText(dis);
	             	 
	             	 
	     }
			for(int i=0 ; i<view.getCitizenList().size();i++){
				
				String out = "";
				if(e.getSource() == view.getCitizenList().get(i)){
					if(flag == true){
						flag = false;
						try {
							respondingUnit.respond(engine.getCitizens().get(i));
							view.getInfoUp().setText("Unit is Responding");
						} catch (IncompatibleTargetException
								| CannotTreatException e1) {
							view.getInfoUp().setText(e1.getMessage());
							ExceptionView ee = new ExceptionView(e1.getMessage());
							// TODO Auto-generated catch block
							
						}
					}else{
		
					out = out +"Location (" +engine.getCitizens().get(i).getLocation().getX() + "," +engine.getCitizens().get(i).getLocation().getY()+")\n";
					out = out+ "Name: "+engine.getCitizens().get(i).getName()+"   Age: "+engine.getCitizens().get(i).getAge()+"    National ID: " +engine.getCitizens().get(i).getNationalID()+"\n";
					out = out+ "HP: " +engine.getCitizens().get(i).getHp()+"\n" ;
					out =  out+"Blood Loss: " + engine.getCitizens().get(i).getBloodLoss() + "\n";
					out = out+"Toxicity: " +engine.getCitizens().get(i).getToxicity()+"\n";
					out = out+"State: " + engine.getCitizens().get(i).getState()+"\n";
					if(engine.getCitizens().get(i).getDisaster() != null){
						if(engine.getCitizens().get(i).getDisaster() instanceof Infection)
						out = out+"Disaster: Infection \n";
						else
							out = out+"Disaster: Injury \n";
						
					}
					view.getInfoUp().setText(out);}
				}
				
			}
			
			for(int i=0;i<view.getBuildingsList().size();i++){
			if(e.getSource()==view.getBuildingsList().get(i)){
				String out ="";
				if(flag == true){
					flag = false;
					try {
						if(respondingUnit instanceof MedicalUnit && engine.getBuildings().get(i).getOccupants().size() !=0 ){
							for(int j=0 ; j<engine.getBuildings().get(i).getOccupants().size();j++){
							if(respondingUnit.canTreat(engine.getBuildings().get(i).getOccupants().get(j))){
								respondingUnit.respond(engine.getBuildings().get(i).getOccupants().get(j));
								view.getInfoUp().setText("Unit is Responding");
							}
							
							
						}
							if(!view.getInfoUp().getText().equals("Unit is Responding")){
								ExceptionView ee = new ExceptionView("THIS UNIT CAN NOT BE USED TO RESCUE THIS CITIZEN");

							}
							}
						else{
						respondingUnit.respond(engine.getBuildings().get(i));
						view.getInfoUp().setText("Unit is Responding");
						}
					} catch (IncompatibleTargetException | CannotTreatException e1) {
						// TODO Auto-generated catch block
						view.getInfoUp().setText(e1.getMessage());
						ExceptionView ee = new ExceptionView(e1.getLocalizedMessage());
						
						
					}
				}
				else{
					if(engine.getBuildings().get(i).getOccupants().size()!=0){
						 BView = new BuildingsView (engine.getBuildings().get(i).getLocation().getX(),engine.getBuildings().get(i).getLocation().getY(),engine.getBuildings().get(i).getOccupants(),this);
						 clicked = engine.getBuildings().get(i);						
					}
			     out = out+ "Location: (" + engine.getBuildings().get(i).getLocation().getX() + "," +engine.getBuildings().get(i).getLocation().getY()+")\n" ;
			     out = out + "Structural Integrity: " + engine.getBuildings().get(i).getStructuralIntegrity()+"\n";
			     out = out+"Fire Damage: "+engine.getBuildings().get(i).getFireDamage()+"\n";
			     out = out+"Gas Level: "+engine.getBuildings().get(i).getGasLevel()+"\n" ;
			     out =  out+"Foundation Damage: "+engine.getBuildings().get(i).getFoundationDamage()+"\n";
			     out = out+"Fire Damage: "+engine.getBuildings().get(i).getFireDamage()+"\n";
  			     out = out+"Occupants number: "+engine.getBuildings().get(i).getOccupants().size();
  			     if(engine.getBuildings().get(i).getOccupants().size()!=0)
  			    	 out = out+" (";
  			     else
  			    	 out = out+"\n";
  			     for(int j=0 ; j<engine.getBuildings().get(i).getOccupants().size();j++){
  			    	 out = out + " (Occupant ("+(j+1)+")"; out = out+ " Name: "+ engine.getBuildings().get(i).getOccupants().get(j).getName()+"   Age: "+engine.getBuildings().get(i).getOccupants().get(j).getAge()+"    National ID: " +engine.getBuildings().get(i).getOccupants().get(j).getNationalID();
 					out = out+ " HP: " +engine.getBuildings().get(i).getOccupants().get(j).getHp()+" " ;
 					out =  out+"Blood Loss: " + engine.getBuildings().get(i).getOccupants().get(j).getBloodLoss() ;
 					out = out+" Toxicity: " +engine.getBuildings().get(i).getOccupants().get(j).getToxicity();
 					out = out+" State: " + engine.getBuildings().get(i).getOccupants().get(j).getState()+")"; 
  			     }
  			    if(engine.getBuildings().get(i).getOccupants().size() != 0)
  			     out = out+")\n";
  			     if(engine.getBuildings().get(i).getDisaster() != null){
  			    	if(engine.getBuildings().get(i).getDisaster() instanceof Collapse){
  			    		out = out+"Disaster: " + "Collapse";
  			    	} 
  			    	else{
  			    		if(engine.getBuildings().get(i).getDisaster() instanceof Fire){
  			    			out = out+"Disaster: " + "Fire";	
  			    		}
  			    		else{
  			    			if(engine.getBuildings().get(i).getDisaster() instanceof GasLeak){
  			    				out = out+"Disaster: " + "GasLeak";
  			    			}
  			    		}
  			    	}
  			     }
			     view.getInfoUp().setText(out);
			    		 
			}}
			}
		
			for(int i=0 ; i<view.getUnitsList().size() ; i++){
				if(e.getSource()==view.getUnitsList().get(i)){
					flag = true;
					respondingUnit = engine.getEmergencyUnits().get(i);
					String out ="";
					out = out+"ID: " + engine.getEmergencyUnits().get(i).getUnitID() + "\n" ;
					out = out+"Unit Type: " +view.getUnitsList().get(i).getText()+"\n";
					out = out+ "Location: (" + engine.getEmergencyUnits().get(i).getLocation().getX() + "," +engine.getEmergencyUnits().get(i).getLocation().getX()+")\n" ;
					out = out+"Steps Per Cycle: " + engine.getEmergencyUnits().get(i).getStepsPerCycle() + "\n" ;
					if(engine.getEmergencyUnits().get(i).getTarget() != null){
						if(engine.getEmergencyUnits().get(i).getTarget() instanceof ResidentialBuilding)
						out = out+"Target: " + "Building at Location: (" +engine.getEmergencyUnits().get(i).getTarget().getLocation().getX()+","+engine.getEmergencyUnits().get(i).getTarget().getLocation().getY() +")\n" ; 
						else
							out = out+"Target: " + "Citizen at Location: (" +engine.getEmergencyUnits().get(i).getTarget().getLocation().getX()+","+engine.getEmergencyUnits().get(i).getTarget().getLocation().getY() +")\n" ; 
					}
				      out =out+"Unit state: " + engine.getEmergencyUnits().get(i).getState()+"\n" ;
				      if(engine.getEmergencyUnits().get(i) instanceof Evacuator){
				    	  int m=0;
				    	  out = out+"Passengers number: " + ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().size()+"\n";
				    	  for(int j=0 ; j<((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().size();j++){
				    		  m=j+1;
				    	  out = out + "(Passenger("+m+") Name:"+ ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getName();
				    	  out = out+" Age:" +((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getAge();
				    	  out = out + " National ID:" + ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getNationalID();
				    	  out = out + " HP:" + ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getHp();
				    	  out = out + " Blood Loss:" + ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getBloodLoss();
				    	  out = out + " Toxicity:" + ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getToxicity();
				    	  out = out + " State:" + ((Evacuator)engine.getEmergencyUnits().get(i)).getPassengers().get(j).getState()+") ";
				    			  }
				    	  
				      }
				     view.getInfoUp().setText(out);
					
				}
			
				
			}
			
		
		
		
			}
		
	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
