package exceptions;

import simulation.Rescuable;
import model.units.Unit;

public abstract class UnitException extends SimulationException {
	private Unit unit;
	private Rescuable target;

	public Unit getUnit() {
		return unit;
	}
	public Rescuable getTarget() {
		return target;
	}

  public UnitException(Unit unit, Rescuable target){
		super();
	}
  public UnitException(Unit unit, Rescuable target, String message){
	  super(message);
  }
	
}
