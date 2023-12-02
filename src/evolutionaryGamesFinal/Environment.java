package evolutionaryGamesFinal;

import java.util.ArrayList;

import sim.util.Int2D;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
	public static long id = 0;//start id.  for each agent created, increment by 1
	public int _cooperators = 500; //number of cooperators
	public int _defectors = 500;  //number of defectors
	public int _walkawayCooperators = 0;  //number of walkaway cooperators
	public int _walkawayDefectors = 0; //number of walkaway defectors
	public int _tftMobile = 0;// number of TFT mobile
	public int _tftStationary = 0;//number of TFT stationary
	public int _pavlovMobile = 0; //number of pavlov Mobile
	public int _pavlovStationary = 0; //number of pavlov stationary
	public int memorySize = 3; //size of an agent's memory of TFT and PAVLOV strategies
	public double active = 1.0; //probability of random movement
	public boolean groups = false; //Multiple agents can be in the same location or what we will call group or patch
	public double averageAge = 50; //average lifespan of an agent
	public double sdAge = 10; //standard deviation in lifespan
	public double mutationRate = 0.0; //muations rate
	public double resoucesToReproduce = 30; //required resources to reproduce one offspring
	public double minResourcesStart = 0; //minimum starting resources
	public double maxResourcesStart = 30;//maximum starting resources.  Starting resources are randomly determined between minResourcesStart and maxResourcesStart
	public double carryingCapacity = 2000;//the maximum number of agents that the space can support
	public int searchRadiusPlayer = 1;//radius for searching for another player
	public double cooperate_cooperator = 3; //payoff for each of two cooperators
	public double defect_defector = 0; //payoff for each of two defectors
	public double cooperate_defector = -1;//payoff for a cooperator playing a defector
	public double defect_cooperator = 5;//payoff for a defector playing a cooperator
	public int reproductionRadius = 1; //The radius from the parent in which an offspring can be placed
	public double lowerBoundsSurvival = 0;//The minimum resources required to survive if useLowerBoundsSurvival = true;
	public boolean useLowerBoundsSurvival = true;// see comments for lowerBoundsSurvival reproductionRadius of the parent, else they are placed randomly in space
	public boolean localReproduction = false;//If true, agent reproduce within 
	public ArrayList<Strategy> mutationList = new ArrayList();
	public boolean mutationRange = true; //if true, uses the mutation range, which are only the strategies with more than 0 agents to start a simulation
	public boolean charts = true; //Uses charts when true.
	
	
	/*
	 * Getter and setter methods begin here
	 */

	

	public int get_cooperators() {
		return _cooperators;
	}

	public void set_cooperators(int _cooperators) {
		this._cooperators = _cooperators;
	}

	public int get_defectors() {
		return _defectors;
	}

	public void set_defectors(int _defectors) {
		this._defectors = _defectors;
	}

	public int get_walkawayCooperators() {
		return _walkawayCooperators;
	}

	public void set_walkawayCooperators(int _walkawayCooperators) {
		this._walkawayCooperators = _walkawayCooperators;
	}

	public int get_walkawayDefectors() {
		return _walkawayDefectors;
	}

	public void set_walkawayDefectors(int _walkawayDefectors) {
		this._walkawayDefectors = _walkawayDefectors;
	}

	public int get_tftMobile() {
		return _tftMobile;
	}

	public void set_tftMobile(int _tftMobile) {
		this._tftMobile = _tftMobile;
	}

	public int get_tftStationary() {
		return _tftStationary;
	}

	public void set_tftStationary(int _tftStationary) {
		this._tftStationary = _tftStationary;
	}

	public int get_pavlovMobile() {
		return _pavlovMobile;
	}

	public void set_pavlovMobile(int _pavlovMobile) {
		this._pavlovMobile = _pavlovMobile;
	}

	public int get_pavlovStationary() {
		return _pavlovStationary;
	}

	public void set_pavlovStationary(int _pavlovStationary) {
		this._pavlovStationary = _pavlovStationary;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	public double getActive() {
		return active;
	}

	public void setActive(double active) {
		this.active = active;
	}

	public boolean isGroups() {
		return groups;
	}

	public void setGroups(boolean patches) {
		this.groups = patches;
	}

	public double getAverageAge() {
		return averageAge;
	}

	public void setAverageAge(double averageAge) {
		this.averageAge = averageAge;
	}

	public double getSdAge() {
		return sdAge;
	}

	public void setSdAge(double sdAge) {
		this.sdAge = sdAge;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public double getResoucesToReproduce() {
		return resoucesToReproduce;
	}

	public void setResoucesToReproduce(double resoucesToReproduce) {
		this.resoucesToReproduce = resoucesToReproduce;
	}

	public double getMinResourcesStart() {
		return minResourcesStart;
	}

	public void setMinResourcesStart(double minResourcesStart) {
		this.minResourcesStart = minResourcesStart;
	}

	public double getMaxResourcesStart() {
		return maxResourcesStart;
	}

	public void setMaxResourcesStart(double maxResourcesStart) {
		this.maxResourcesStart = maxResourcesStart;
	}

	public double getCarryingCapacity() {
		return carryingCapacity;
	}

	public void setCarryingCapacity(double carryingCapacity) {
		this.carryingCapacity = carryingCapacity;
	}

	public int getSearchRadiusPlayer() {
		return searchRadiusPlayer;
	}

	public void setSearchRadiusPlayer(int searchRadiusPlayer) {
		this.searchRadiusPlayer = searchRadiusPlayer;
	}

	public double getCooperate_cooperator() {
		return cooperate_cooperator;
	}

	public void setCooperate_cooperator(double cooperate_cooperator) {
		this.cooperate_cooperator = cooperate_cooperator;
	}

	public double getDefect_defector() {
		return defect_defector;
	}

	public void setDefect_defector(double defect_defector) {
		this.defect_defector = defect_defector;
	}

	public double getCooperate_defector() {
		return cooperate_defector;
	}

	public void setCooperate_defector(double cooperate_defector) {
		this.cooperate_defector = cooperate_defector;
	}

	public double getDefect_cooperator() {
		return defect_cooperator;
	}

	public void setDefect_cooperator(double defect_cooperator) {
		this.defect_cooperator = defect_cooperator;
	}

	public int getReproductionRadius() {
		return reproductionRadius;
	}

	public void setReproductionRadius(int reproductionRadius) {
		this.reproductionRadius = reproductionRadius;
	}

	public double getLowerBoundsSurvival() {
		return lowerBoundsSurvival;
	}

	public void setLowerBoundsSurvival(double lowerBoundsSurvival) {
		this.lowerBoundsSurvival = lowerBoundsSurvival;
	}

	public boolean isUseLowerBoundsSurvival() {
		return useLowerBoundsSurvival;
	}

	public void setUseLowerBoundsSurvival(boolean useLowerBoundsSurvival) {
		this.useLowerBoundsSurvival = useLowerBoundsSurvival;
	}

	public boolean isLocalReproduction() {
		return localReproduction;
	}

	public void setLocalReproduction(boolean localReproduction) {
		this.localReproduction = localReproduction;
	}

	public boolean isMutationRange() {
		return mutationRange;
	}

	public void setMutationRange(boolean mutationRange) {
		this.mutationRange = mutationRange;
	}
	
	/*
	 * End getter and setter methods
	 */
	
	/**
	 * Constructor method
	 * @param seed
	 * @param observer
	 */

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}	

	/**
	 * Finds a random and  empty location in space and returns it as an Int2D(x,y), where x,y are the coordinates of the empty location
	 * @return
	 */
	public Int2D emptyXY() {
		int x = random.nextInt(gridWidth);
		int y = random.nextInt(gridHeight);
		while(sparseSpace.getObjectsAtLocation(x, y)!= null) {
			x = random.nextInt(gridWidth);
			y = random.nextInt(gridHeight);
		}
		return new Int2D(x,y);
	}
	
	/**
	 * Finds any random location in space, which may or may not be empty
	 * @return
	 */
	public Int2D locationXY() {
		int x = random.nextInt(gridWidth);
		int y = random.nextInt(gridHeight);
		return new Int2D(x,y);
	}
	
	/**
	 * Creates a list of possible mutant strategies only if there are more than 0 agents that have a given strategy 
	 * at the start of a simulation.
	 */

	public void makeMutationList() {
		mutationList.clear();
		if(_cooperators > 0) {
			mutationList.add(Strategy.COOPERATOR);
		}
		if(_defectors > 0) {
			mutationList.add(Strategy.DEFECTOR);
		}
		if(_walkawayCooperators > 0) {
			mutationList.add(Strategy.WALKAWAY_COOPERATOR);
		}
		if(_tftMobile > 0) {
			mutationList.add(Strategy.TFT_MOBILE);
		}
		if(_tftStationary > 0) {
			mutationList.add(Strategy.TFT_SATIONARY);
		}
		if(_pavlovMobile > 0) {
			mutationList.add(Strategy.PAVLOV_MOBILE);
		}
		if(_pavlovStationary > 0) {
			mutationList.add(Strategy.PAVLOV_SATIONARY);
		}
		if(_walkawayDefectors > 0) {
			mutationList.add(Strategy.WALKAWAY_DEFECTOR);
		}
	}
	
	/**
	 * This method creates the agents. It does so, by making all the agents one strategy at a time
	 */
	
	public void makeAgents() {
		if(!groups) { //if patches != true, then make sure there are enough locations for all the agents
			int total =  _cooperators + _defectors + _walkawayCooperators + _tftMobile + _tftStationary + _pavlovMobile + _pavlovStationary + _walkawayDefectors;
			int total2 = gridWidth * gridHeight;
			if(total > total2) {
				System.out.println("Too many agents");
				return; //If there are too many agents, then none will be made and you have to adjust the number of agents you specify at the start
			}
		}
		for(int i=0;i<_cooperators;i++) { //Make all the cooperators
			Int2D location;
			if(!groups)
				location = emptyXY();// if not patches, only empty locations will do
			else
				location = locationXY();//if patches, then more than one agent can be at the same location (i.e., patch)
			int xdir = random.nextInt(3)-1; //random direction of movement
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart; //random initial resources between maxResourcesStart and maxResourcesStart
			long id = this.id++; //unique id for agent, recall that longs eventually recycle if the max long is ever reached
			Agent agent =  new Agent(this,id,Strategy.COOPERATOR,resources,location.x,location.y,xdir,ydir,true); //make each agent
			agent.event = schedule.scheduleRepeating(agent);//schedule the agent
			sparseSpace.setObjectLocation(agent,location.x, location.y);//put it in space
			agent.colorByStrategy(agent.strategy, this, agent);//color the agent by strategy
		}
		for(int i=0;i<_defectors;i++) { //Make all the defectors, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.DEFECTOR,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<_walkawayCooperators;i++) { //Make all the walkawayCooperators, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.WALKAWAY_COOPERATOR,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<_walkawayDefectors;i++) {//Make all the walkawayDefectors, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.WALKAWAY_DEFECTOR,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}

		for(int i=0;i<_tftMobile;i++) {//Make all the tftMobile, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.TFT_MOBILE,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<_tftStationary;i++) {//Make all the tftStationary, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.TFT_SATIONARY,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<_pavlovMobile;i++) {//Make all the pavlovMobile, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.PAVLOV_MOBILE,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		for(int i=0;i<_pavlovStationary;i++) {//Make all the pavlovStationary, see cooperators for detailed comments
			Int2D location;
			if(!groups)
				location = emptyXY();
			else
				location = locationXY();
			int xdir = random.nextInt(3)-1;
			int ydir = random.nextInt(3)-1;
			double resources = (maxResourcesStart-minResourcesStart)*random.nextDouble()+minResourcesStart;
			long id = this.id++;
			Agent agent = new Agent(this,id,Strategy.PAVLOV_SATIONARY,resources,location.x,location.y,xdir,ydir,true);
			agent.event = schedule.scheduleRepeating(agent);
			sparseSpace.setObjectLocation(agent,location.x, location.y);
			agent.colorByStrategy(agent.strategy, this, agent);
		}
		makeMutationList(); //makes the mutation list 
	}

	public void start() {
		super.start();
		makeSpace(gridWidth,gridHeight);//make the space
		makeAgents();//make the agents
		if(observer != null)//initialize the observer (i.e., experimenter
			observer.initialize(space, spaces);
	}
}
