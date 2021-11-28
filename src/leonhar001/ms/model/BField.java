package leonhar001.ms.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class BField {
	
	/*All possible field's status*/
	private boolean openned;
	private boolean mined;
	private boolean flagged;
	
	/*Fied address*/
	private final int row; 
	private final int column;
	
	/*List of field's neighbors*/
	private List<BField> neighbors = new ArrayList<>();
	
	/*Using "set" to avoid duplications*/
	private Set<ObserverField> observers = new LinkedHashSet<>();
	
	public void addObservers (ObserverField observer) {
		observers.add(observer);
	}
	
	public void notifyObservers(EventField event) {
		observers.stream()
			.forEach(o -> o.eventOcurred(this, event));
	}
	
	BField(int row, int colums){
		this.row = row;
		this.column = colums;
	}
	
	boolean addNeighbor(BField neighbor) {
		/*Verify neighbor's position (diagonal or beside)*/
		boolean differentRow = row != neighbor.row;
		boolean differentColumn = column != neighbor.column;
		boolean diagonal = differentRow && differentColumn;
		
		int deltaRow = Math.abs(row - neighbor.row);
		int deltaColumn = Math.abs(column - neighbor.column);
		int deltaGeneral = deltaRow + deltaColumn;
		
		if(deltaGeneral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if(deltaGeneral == 2 && diagonal) {
			neighbors.add(neighbor);
			return  true;
		} else {
			return false;
		}
	}
	public void alternateFlag() {
		if(!openned) {
			flagged = !flagged;
			
			if(flagged) {
				notifyObservers(EventField.MARK);
			}else {
				notifyObservers(EventField.UNMARK);
			}
		}
	}
	
	 public boolean openField() {
		if(!openned && !flagged) {
			
			if(mined) {
				notifyObservers(EventField.EXPLOSION);
				return true;
			}
			setOpenned(true);
			
			if(safeNeighborhood()) {
				neighbors.forEach(n -> n.openField()); /*Recursive*/
			}
			
			return true;
		}else {
			return false;
		}
	}
	
	public boolean safeNeighborhood() {
		return neighbors.stream().noneMatch(n -> n.mined);
	}
	
	boolean isFlagged() {
		return flagged;
	}
	
	public void setOpenned(boolean openned) {
		this.openned = openned;
		
		if(openned)
			notifyObservers(EventField.OPEN);
	}

	boolean isOpenned() {
		return openned;
	}
	
	boolean isClosed() {
		return !isOpenned();
	}
	
	public boolean isMined() {
		return mined;
	}
	
	int getRow() {
		return row;
	}
	
	int getColumn() {
		return column;
	}
	
	void mine() {
		mined = true;
	}
	
	public boolean resolvedField() {
		boolean uncovered = openned && !mined;
		boolean protect = mined && flagged;
		
		return uncovered || protect;
	}
	
	public int minesAroundNeighborhood() {
		return (int) neighbors.stream().filter(n -> n.mined).count();
	}
	
	void restart() {
		openned  = false;
		mined = false;
		flagged = false;
		notifyObservers(EventField.RESTART);
	}
	
}
