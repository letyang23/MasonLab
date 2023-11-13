package khModelLab4;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Int2D;

public class Agent implements Steppable {
	int x;//x,y coordinates
	int y;
	int dirx;//the direction of movement
	int diry;
	//KH model
	public boolean female;//determines whether an agent is a female == true, male == false
	public double attractiveness;//Attractiveness of this agent
	public double dates = 0;//starts with 0 and incremented by 1 with each date.
	public double frustration = 0;
	public boolean dated = false;//flag for dating on each round
	public Stoppable event;//allows to remove an agent from the simulation.

	
	public Agent(int x, int y, int dirx, int diry) {
		super();
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
	}
	
    public Agent(boolean female, double attractiveness) {
		super();
		this.female = female;
		this.attractiveness = attractiveness;
	}

	public Agent(int x, int y, boolean female, double attractiveness) {
		super();
		this.x = x;
		this.y = y;
		this.female = female;
		this.attractiveness = attractiveness;
	}
	
	public Agent(int x, int y, int dirx, int diry, boolean female, double attractiveness) {
		super();
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
		this.female = female;
		this.attractiveness = attractiveness;
	}
     
	public Agent findDate(Environment state) {
		if(female ) {
			if(state.male.numObjs==0)
				return null;
			return (Agent)state.male.objs[state.random.nextInt(state.male.numObjs)];
		}
		else {
			if(state.female.numObjs==0)
				return null;
			return (Agent)state.female.objs[state.random.nextInt(state.female.numObjs)];
		}
	}

	public Agent findLocalDate(Environment state) {
		Bag agents = state.sparseSpace.getMooreNeighbors(x, y, state.dateSearchRadius, state.sparseSpace.TOROIDAL, false);
		if(agents.numObjs == 0) return null; //no agents

		while(agents.numObjs>0) {
			int r = state.random.nextInt(agents.numObjs);
			Agent a = (Agent)agents.objs[r];
			if(!a.dated && female != a.female) {
				return a;
			}
			agents.remove(a);
		}
		return null;
	}

	public double ctRule(Environment state, double p) {
		if(dates>state.maxDates)
			return 1;
		else
		return Math.pow(p, (state.maxDates-dates)/state.maxDates);
	}

	public double p1(Environment state, Agent a) {
		return Math.pow(a.attractiveness, state.choosiness)/Math.pow(state.maxAttractiveness, state.choosiness);
	}

	public double p2(Environment state, Agent a) {
		return Math.pow(state.maxAttractiveness-Math.abs(this.attractiveness - a.attractiveness), state.choosiness)/
				Math.pow(state.maxAttractiveness, state.choosiness);
	}

	public double p3(Environment state, Agent a) {
		return (p1(state,a)+p2(state,a))/2.0;
	}

	public double frRule(Environment state) {
		if (frustration > state.maxFrustration)
			return 0;
		else
		return (state.maxFrustration-frustration)/state.maxFrustration;
	}

	public double p4(Environment state, Agent a) {
		double fr = frRule(state);
		//System.out.println(ct);
		return p1(state,a)*fr+p2(state,a)*(1-fr);
	}

	public void remove(Environment state) {
		if(state.nonSpatialModel) {
			if(female) 
				state.female.remove(this);
			else
				state.male.remove(this);
		}
		state.sparseSpace.remove(this);
		event.stop();
	}

	public void nextPopulationStep(Environment state) {
		dated = true;
		if(female) {
			state.nextFemale.add(this);
			state.female.remove(this);
		}
		else {
			state.nextMale.add(this);
			state.male.remove(this);
		}
	}

	public void date(Environment state, Agent a) {
		double p;
		double q;
		switch(state.rule) {
		case ATTRACTIVE:
			p = p1(state,a);
			q = a.p1(state, this);
			p = ctRule(state,p);
			q = ctRule(state,q);
			break;
		case SIMILAR:
			p = p2(state,a);
			q = a.p2(state, this);
			p = ctRule(state,p);
			q = ctRule(state,q);
			break;
		case MIXED:
			p = p3(state,a);
			q = a.p3(state, this);
			p = ctRule(state,p);
			q = ctRule(state,q);
			break;
		case MIXED2_FRUSTRATION:
			p = p4(state,a);
			q = a.p4(state, this);
			p = ctRule(state,p);
			q = ctRule(state,q);
			break;
		default:
			p = p1(state,a);
			q = a.p1(state, this);
			p = ctRule(state,p);
			q = ctRule(state,q);
			break;	
		}

		if(state.random.nextBoolean(p)&& state.random.nextBoolean(q)) {
			if(female) {
				state.experimenter.getData(this, a);
			}
			else {
				state.experimenter.getData(a, this);
			}
			remove(state);
			a.remove(state);
			if(state.replacement) {
				this.replicate(state);
				a.replicate(state);
			}
		} //end if test
		else {
			this.nextPopulationStep(state);
			a.nextPopulationStep(state);
		}
		if(dates < state.maxDates) {
			dates++;
		}
		if(a.dates < state.maxDates) {
			a.dates++;
		}
		if(frustration < state.maxFrustration) {
			frustration ++;
		}
		if(a.frustration < state.maxFrustration) {
			a.frustration ++;
		}

	}

	public Int2D uniqueXY(Environment state) {
		int x = state.random.nextInt(state.gridWidth);
		int y = state.random.nextInt(state.gridHeight);
		while(state.sparseSpace .getObjectsAtLocation(x, y)!= null) {
			x = state.random.nextInt(state.gridWidth);
			y = state.random.nextInt(state.gridHeight);
		}
		return new Int2D(x,y);
	}

	public Int2D locationXY(Environment state) {
		int x = state.random.nextInt(state.gridWidth);
		int y = state.random.nextInt(state.gridHeight);
		return new Int2D(x,y);
	}

	public Agent replicate(Environment state) {
		double attractiveness = state.random.nextInt((int)state.maxAttractiveness)+1;
		Int2D location;
		if(state.oneCellPerAgent)
			location = uniqueXY(state);
		else
			location = locationXY(state);
		int xdir = state.random.nextInt(3)-1;
		int ydir = state.random.nextInt(3)-1;
		Agent a = new Agent(location.x,location.y,xdir,ydir,female, attractiveness);
		a.event = state.schedule.scheduleRepeating(a);
		state.sparseSpace.setObjectLocation(a,state.random.nextInt(state.gridWidth), state.random.nextInt(state.gridHeight));
		if(state.nonSpatialModel) {
			if(female) {
				state.female.add(a);
			}
			else {
				state.male.add(a);
			}
		}
		if(!state.graphics)
			return a;//we are done
		if(female)
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0, (float)0, (float)(attractiveness/state.maxAttractiveness));
		else
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)0, (float)1, (float)(attractiveness/state.maxAttractiveness));
		return a;
	}

	public void date(Environment state) {
		if(state.nonSpatialModel) {
			Agent a = findDate(state);
			if(a!= null) {
				date(state, a);
			}
		}
		else {
			Agent a = findLocalDate(state);
			if(a!= null) {
				date(state, a);
			}
		}
	}

	public void placeAgent(Environment state) {
        if(state.oneCellPerAgent) {//only one agent per cell
             int tempx = state.sparseSpace.stx(x + dirx);//tempx and tempy location
             int tempy = state.sparseSpace.sty(y + diry);
             Bag b = state.sparseSpace.getObjectsAtLocation(tempx, tempy);
             if(b == null){//if empty, agent moves to new location
                   x = tempx;
                   y = tempy;
                   state.sparseSpace.setObjectLocation(this, x, y);
             }//otherwise it does not move.
        }
        else {               
             x = state.sparseSpace.stx(x + dirx);
             y = state.sparseSpace.sty(y + diry);
             state.sparseSpace.setObjectLocation(this, x, y);
        }
   }
	/**
	 * Agents move randomly to a new location for either one agent per cell or possibly
	 * multiple agents per cell.
	 * @param state
	 */
	public void move(Environment state) {
		if(!state.random.nextBoolean(state.active)) {
			return;
		}
		if(state.random.nextBoolean(state.p)) {
			dirx = state.random.nextInt(3)-1;
			diry = state.random.nextInt(3)-1;
		}
		placeAgent(state);
	}
	
	
	public int decideX(Environment state, Bag neighbors) {
		int posX =0, negX =0;
		for(int i=0;i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			if(a.x > x) {
				posX++;
			}
			else if(a.x < x) {
				negX++;
			}
		}
		if(posX > negX) {
			return 1;
		}
		else if (negX > posX) {
			return -1;
		}
		else {
			return state.random.nextInt(3)-1;
		}
	}
	
	public int decideY(Environment state, Bag neighbors) {
		int posY =0, negY =0;
		for(int i=0;i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			if(a.y > y) {
				posY++;
			}
			else if(a.y < y) {
				negY++;
			}
		}
		if(posY > negY) {
			return 1;
		}
		else if (negY > posY) {
			return -1;
		}
		else {
			return state.random.nextInt(3)-1;
		}
	}
	
	public void aggregate (Environment state) {
		Bag b = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadius, state.sparseSpace.TOROIDAL, false);
		dirx = decideX(state,b);
		diry = decideY(state,b);
		placeAgent(state);
	}

	public void step(SimState state) {
		Environment environment = (Environment)state;
		if(!environment.nonSpatialModel) {
			if(environment.random.nextBoolean(environment.aggregate)) {
				aggregate (environment);
			}
			else {
				move(environment);
			}
		}

		if(!dated) {
			date(environment);
		}
		

	}

}
