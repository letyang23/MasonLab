package agents_120;

import spaces.Spaces;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
	int n = 500; //number of agents
	double pActive = 1.0; //probability of being active
	double pRandom = 1.0; //probability of moving randomly
	boolean oneAgentperCell = false; 
	double pAggregate = 0.0; //probability of aggregating
	int searchRadius = 2; //view of an agent

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getpActive() {
		return pActive;
	}

	public void setpActive(double pActive) {
		this.pActive = pActive;
	}

	public double getpRandom() {
		return pRandom;
	}

	public void setpRandom(double pRandom) {
		this.pRandom = pRandom;
	}

	public boolean isOneAgentperCell() {
		return oneAgentperCell;
	}

	public void setOneAgentperCell(boolean oneAgentperCell) {
		this.oneAgentperCell = oneAgentperCell;
	}

	public double getpAggraget() {
		return pAggregate;
	}

	public void setpAggraget(double pAggraget) {
		this.pAggregate = pAggraget;
	}

	public int getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(int searchRadius) {
		this.searchRadius = searchRadius;
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
	}

	public void makeAgents() {
		if(this.oneAgentperCell && n > gridWidth*gridHeight) {
			System.out.println("Too many agents, n = "+ n + " the space can hold = " + (gridWidth*gridHeight));
			return;
		}
		for(int i=0;i<n;i++) {
			int x = random.nextInt(gridWidth);
			int y = random.nextInt(gridHeight);
			if(this.oneAgentperCell) {
				while(sparseSpace.getObjectsAtLocation(x, y) != null) {
					x = random.nextInt(gridWidth);
					y = random.nextInt(gridHeight);
				}
			}
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			Agent a = new Agent(x,y,xdir,ydir);
			schedule.scheduleRepeating(a);
			this.sparseSpace.setObjectLocation(a, x, y);
		}

	}

	public void start() {
		super.start();	//use already written code first
		spaces = Spaces.SPARSE;
		this.make2DSpace(spaces, gridWidth, gridHeight);
		makeAgents();
	}
}
