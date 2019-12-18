package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}
	public boolean canTreat(Rescuable r){
		if(r instanceof Citizen){
			if((((Citizen)r).getBloodLoss()==0 && ((Citizen)r).getToxicity()==0 ))
			return false;
			if(this instanceof Ambulance && !(r.getDisaster() instanceof Injury))
				return false;
			if(this instanceof DiseaseControlUnit && !(r.getDisaster() instanceof Infection))
				return false;
		}
		else{
			if(((ResidentialBuilding)r).getGasLevel() ==0 &&((ResidentialBuilding)r).getFireDamage() ==0 && ((ResidentialBuilding)r).getFoundationDamage() ==0)
				return false;
			if(this instanceof PoliceUnit && !(r.getDisaster() instanceof Collapse))
			    return false;
			if(this instanceof FireTruck && !(r.getDisaster() instanceof Fire))
				return false;
			if(this instanceof GasControlUnit && !(r.getDisaster() instanceof GasLeak))
				return false;
		
		}
		
		return true;
	}
    
	@Override
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
        if(r instanceof ResidentialBuilding && (this instanceof Ambulance)){
        	throw new IncompatibleTargetException(this,r,"AMBULANCE CAN NOT BE USED TO RESCUE A BUILDING");
        }
        if(r instanceof Citizen && !(this instanceof MedicalUnit)){
        	throw new IncompatibleTargetException(this,r,"THIS UNIT CAN NOT BE USED TO RESCUE A CITIZEN");
        }
        if(!canTreat(r)){
        	if(r instanceof Citizen)
        	throw new CannotTreatException(this,r,"THIS UNIT CAN NOT TREAT THIS CITIZEN");
        	else
            	throw new CannotTreatException(this,r,"THIS UNIT CAN NOT TREAT THIS BUILDING");
        }
        
		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);

	}

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}
}
