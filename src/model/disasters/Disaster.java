package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import simulation.Rescuable;
import simulation.Simulatable;
import model.infrastructure.*;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.UnitState;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException 
	{
		if(this.getTarget() instanceof ResidentialBuilding && ((ResidentialBuilding)this.getTarget()).getStructuralIntegrity()==0){
			throw new BuildingAlreadyCollapsedException(this,"msg");
		}
		if(this.getTarget() instanceof Citizen && ((Citizen)this.getTarget()).getState().equals(CitizenState.DECEASED)){
			throw new CitizenAlreadyDeadException(this,"msg");
		}
		
		target.struckBy(this);
		active=true;
	}
}
