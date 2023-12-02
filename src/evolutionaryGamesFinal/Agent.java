package evolutionaryGamesFinal;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Int2D;

public class Agent implements Steppable {
	public Stoppable event; // call event.stop to remove the agent from the schedule
	long id; // agent id
	Strategy strategy;// the agent's strategy
	double resources;// the agent's current resources
	boolean played = false;// if true, the agent played on the current step otherwise it did not. It is
							// reset by the experimenter after each step
	Memory memory = null;// the memory of the agent. An agent only has memory if it requires memory
	int x; // location on the x-axis
	int y; // location on the y-axis
	int xdir; // x direction of change
	int ydir; // y direction of change
	int age; // current age of agent
	int maxAge;// age at which agent dies

	/**
	 * Agent constructor method
	 * 
	 * @param state
	 * @param id
	 * @param strategy
	 * @param resources
	 * @param x
	 * @param y
	 * @param xdir
	 * @param ydir
	 * @param startup
	 */
	public Agent(Environment state, long id, Strategy strategy, double resources, int x, int y, int xdir, int ydir,
			boolean startup) {
		super();
		this.id = id;// assign unique id
		this.strategy = strategy;// assign strategy
		this.resources = resources;// assign initial resources
		this.x = x;// assign x location
		this.y = y;// assign y location
		this.xdir = xdir;// assign ydir of movement
		this.ydir = ydir;// assign xdir of movement
		maxAge = (int) (state.averageAge + state.random.nextGaussian() * state.sdAge);// lifespan of agent, with mean =
																						// averageAge and standard
																						// deviation = sdAge
		if (startup) {
			age = state.random.nextInt((int) state.averageAge);// only used at the start of a simulation and is the
																// random current age
		} else {
			age = 0; // when agents are born, they have age 0
		}
		switch (strategy) { // creates memory for only the stratgies that require memory
		case TFT_MOBILE:
			;
		case TFT_SATIONARY:
			;
		case PAVLOV_MOBILE:
			;
		case PAVLOV_SATIONARY:
			memory = new Memory(state.memorySize);
		}
	}

	/**
	 * Returns the strategy of an opponent
	 * 
	 * @param opponent
	 * @return
	 */
	public Strategy getStrategy(Agent opponent) {
		switch (strategy) {
		case COOPERATOR:
			return Strategy.COOPERATOR;
		case DEFECTOR:
			return Strategy.DEFECTOR;
		case WALKAWAY_COOPERATOR:
			return Strategy.COOPERATOR;
		case TFT_MOBILE:
			;
		case TFT_SATIONARY:
			return getStrategyTFT(opponent);
		case PAVLOV_MOBILE:
			;
		case PAVLOV_SATIONARY:
			return getStrategyPAVLOV(opponent);
		case WALKAWAY_DEFECTOR:
			return Strategy.DEFECTOR;
		default:
			return Strategy.COOPERATOR;
		}
	}

	/**
	 * TFT finds a strategy to play against an opponent based on memory of the most
	 * recent previous encounter, if there was a previous encounter.
	 * 
	 * @param opponent
	 * @return
	 */

	public Strategy getStrategyTFT(Agent opponent) {
		Triple m = memory.getLastMemory(opponent); // search for a memory of what this opponent last played against you
		if (m == null) {// If there is no memory, then TFT cooperates
			return Strategy.COOPERATOR;
		}
		switch (m.opponentStrategy) {// if there is a memory, then TFT plays conditional on what the opponent played
										// the last time
		case COOPERATOR:// if cooperate, then cooperate
			return Strategy.COOPERATOR;
		case DEFECTOR:// if defect, then defect
			return Strategy.DEFECTOR;
		default:// logically, this default never occurs, but is required for completeness
			return Strategy.COOPERATOR;
		}
	}

	/**
	 * PAVLOV finds a strategy to play against an opponent based on memory of the
	 * most recent previous encounter, if there was a previous encounter.
	 * 
	 * @param opponent
	 * @return
	 */
	public Strategy getStrategyPAVLOV(Agent opponent) {
		Triple m = memory.getLastMemory(opponent);// search for a memory of what this opponent last played against you
		if (m == null) {// If there is no memory, then PAVLOV cooperates
			return Strategy.COOPERATOR;
		}
		switch (m.opponentStrategy) {
		case COOPERATOR:
			return m.myStrategy; // If opponent cooperates, use the strategy it last played against the opponent
		case DEFECTOR:// if the opponent defects,its more complicated
			switch (m.myStrategy) {
			case COOPERATOR: // if this agent cooperated and opponent defected, then of course defect
				return Strategy.DEFECTOR;
			case DEFECTOR: // if both of us defected the last time we played, try cooperating
				return Strategy.COOPERATOR;
			}
		default:// logically, this default never occurs, but is required for completeness
			return Strategy.COOPERATOR;
		}
	}

	/**
	 * Calculates an agent's payoff given the strategy it played and the strategy of
	 * its opponent. payoffs are R = cooperators' payoff, T = temptation to defect,
	 * S = sucker's payoff, D = defectors' payoff
	 * 
	 * @param state
	 * @param opponent
	 * @return
	 */
	public Strategy play(Environment state, Agent opponent) {
		Strategy myStrategy = getStrategy(opponent);
		Strategy myOpponentStrategy = opponent.getStrategy(this);
		switch (myOpponentStrategy) {// opponent strategy
		case COOPERATOR:
			switch (myStrategy) {
			case COOPERATOR:
				resources += state.cooperate_cooperator; // payoff (R) for a cooperator (this agent) playing another
															// cooperator
				break;
			case DEFECTOR:
				resources += state.defect_cooperator;// payoff (T) for a defector (this agent) playing a cooperator
				break;
			}
			break;
		case DEFECTOR:
			switch (myStrategy) {
			case COOPERATOR:
				resources += state.cooperate_defector; // payoff (S) for a cooperator (this agent) playing another
														// defector
				break;
			case DEFECTOR:
				resources += state.defect_defector;// payoff (D) for a defector (this agent) playing another defector
				break;
			}
			break;
		}
		this.played = true;// this agent as played for this round
		switch (strategy) {// update memory for memory agents
		case TFT_MOBILE:
		case TFT_SATIONARY:
		case PAVLOV_MOBILE:
		case PAVLOV_SATIONARY:
			memory.storeMemory(opponent, myOpponentStrategy, myStrategy);
		}
		return myOpponentStrategy;// return opponent's strategy
	}

	/**
	 * An agent using a mobile strategy first searches for agents in its search
	 * radius. If there are none, then it randomly moves then searches again for an
	 * opponent to play. If so they play.
	 * 
	 * @param state
	 */
	public void mobileStrategy(Environment state) {
		Bag agents = search(state);// search for possible opponents
		if (agents == null || agents.numObjs == 0) // if an opponent cannot be found, then move randomly to a new
													// location
		{
			if (state.random.nextBoolean(state.active)) {
				xdir = state.random.nextInt(3) - 1;
				ydir = state.random.nextInt(3) - 1;
			}
			placeAgent(state);// this handles whether agent moves to a patch (patches = true) or an empty
								// location (patches = false)
			agents = search(state); // now again look for an agent
			if (agents == null) {
				return;
			} // if there are no agents to play, give up for this time step
			Agent opponent = findOpponent(state, agents);// if there are agents, find a random on who has not played
			if (opponent == null) {
				return;
			} // if there are none, give up on this time step
			play(state, opponent);// If there is one, go ahead and play
			opponent.play(state, this);// also, remember the opponent plays this agent
		} else {
			Agent opponent = findOpponent(state, agents);// the first search found agents! Now find one who has not
															// played
			if (opponent == null) {
				return;
			} // if there are none, give up on this time step
			play(state, opponent);// If there is one, go ahead and play
			opponent.play(state, this);// also, remember the opponent plays this agent
		}
	}

	/**
	 * A walkaway strategy is just like a mobile strategy except that if a defection
	 * is encountered, it moves randomly.
	 * 
	 * @param state
	 */
	public void walkawayStrategy(Environment state) {
		Bag agents = search(state); // search for possible opponents
		Agent opponent;
		Strategy opponentStrategy;
		if (agents == null || agents.numObjs == 0) {// if an opponent cannot be found, then move randomly to a new
			// location
			if (state.random.nextBoolean(state.active)) {
				xdir = state.random.nextInt(3) - 1;
				ydir = state.random.nextInt(3) - 1;
			}
			placeAgent(state);// this handles whether agent moves to a patch (patches = true) or an empty
			// location (patches = false)
			agents = search(state); // now again look for an agent
			if (agents == null) {
				return;
			} // if there are no agents to play, give up for this time step
			opponent = findOpponent(state, agents);// if there are agents, find a random on who has not played
			if (opponent == null) {
				return;
			} // if there are none, give up on this time step
			opponentStrategy = play(state, opponent);// If there is one, go ahead and play
			opponent.play(state, this);// also, remember the opponent plays this agent
		} else {
			opponent = findOpponent(state, agents);// the first search found agents! Now find one who has not played
			if (opponent == null) {
				return;
			} // if there are none, give up on this time step
			opponentStrategy = play(state, opponent);// If there is one, go ahead and play
			opponent.play(state, this);// also, remember the opponent plays this agent
		}
		if (opponentStrategy == Strategy.DEFECTOR) { // If the opponent defected, then just walkaway
			if (state.random.nextBoolean(state.active)) {// Get a new random direction to move
				xdir = state.random.nextInt(3) - 1;
				ydir = state.random.nextInt(3) - 1;
			}
			placeAgent(state);// this handles whether agent moves to a patch (patches = true) or an empty
			// location (patches = false)
		}
	}

	public void stationaryStrategy(Environment state) {
		Bag agents = search(state);// search for possible opponents
		Agent opponent;
		if (agents == null || agents.numObjs == 0) {// if an opponent cannot be found, then just give up on this time
													// step and stay where you are
			return;
		} else {
			opponent = findOpponent(state, agents);// the first search found agents! Now find one who has not played
			if (opponent == null) {
				return;
			} // if there are none, give up on this time step
			play(state, opponent);// If there is one, go ahead and play
			opponent.play(state, this);// also, remember the opponent plays this agent
		}
	}

	/**
	 * This is the play method which determines which strategy an agent playes based
	 * on the strategy it was born with
	 * 
	 * @param state
	 */
	public void play(Environment state) {
		switch (strategy) {
		case COOPERATOR:
			;
		case DEFECTOR:
			;
		case TFT_MOBILE:
			;
		case PAVLOV_MOBILE:// These are all mobile strategies, so use the mobile strategy method
			mobileStrategy(state);
			break;
		case WALKAWAY_COOPERATOR:
			;
		case WALKAWAY_DEFECTOR:// these are walkaway strategies, so you the walkaway method
			walkawayStrategy(state);
			break;
		case TFT_SATIONARY:
			;
		case PAVLOV_SATIONARY:// these are stationary, so use the stationary strategy
			stationaryStrategy(state);
		}
	}

	/*	*//**
			 * Given a bag of possible opponents, it finds a random agent that has not
			 * played on this time step if one exists
			 * 
			 * @param state
			 * @param agents
			 * @return
			 *//*
				 * 
				 * public Agent findOpponent(Environment state, Bag agents) { if(agents == null
				 * || agents.numObjs == 0) return null;//bag is empty or null, just return null
				 * Agent a = (Agent)agents.objs[state.random.nextInt(agents.numObjs)];//get a
				 * random agent while(a.played && a.equals(this)) {// a.played == true AND a ==
				 * this agent,keep going through the bag to find an agent
				 * agents.remove(a);//remove an agent that does not qualify if(agents.numObjs ==
				 * 0) {//if the bag is empty stop and return null return null; //none to be
				 * found } a = (Agent)agents.objs[state.random.nextInt(agents.numObjs)];//get
				 * another random agent } return a;//success, an agent was found! }
				 */
	/**
	 * Given a bag of possible opponents, it finds a random agent that has not played on this time step if one exists
	 * @param state
	 * @param agents
	 * @return
	 */
	
	public Agent findOpponent(Environment state, Bag agents) {
		if(agents == null || agents.numObjs == 0) return null;//bag is empty or null, just return null
		agents.shuffle(state.random);
		for(int i = 0; i< agents.numObjs;i++) {
			Agent a = (Agent)agents.objs[i];
			if(!(a.played && a.equals(this))) {// a.played == true AND a == this agent,keep going through the bag to find an agent
				return a;
			}
		}
		return null;//No agent was found!
	}
	
	/**
	 * This strategy generates random mutations based the mutation list
	 * 
	 * @param state
	 * @return
	 */

	public Strategy mutation(Environment state) {
		Strategy newStrategy;
		if (state.mutationRange) {// if mutationRange == true, then only strategis
			if (state.random.nextBoolean(state.mutationRate)) {// if true is returned by
																// random.nextBoolean(state.mutationRate)
				newStrategy = state.mutationList.get(state.random.nextInt(state.mutationList.size()));
				while (state.mutationList.size() > 1 && newStrategy == this.strategy) { // find a strategy different
																						// from the parent
					newStrategy = state.mutationList.get(state.random.nextInt(state.mutationList.size()));
				}
				return newStrategy; // return the new strategy
			} else {
				return this.strategy; // the current agent's strategy when random.nextBoolean(state.mutationRate) ==
										// false
			}
		} else {// if state.mutationRange == false
			if (state.random.nextBoolean(state.mutationRate)) {// any of the possible strategies will do
				newStrategy = Strategy.values()[state.random.nextInt(Strategy.values().length)];// get a random one
				while (state.mutationList.size() > 1 && newStrategy == this.strategy) { // find a strategy different
																						// from the parent
					newStrategy = Strategy.values()[state.random.nextInt(Strategy.values().length)];// keep generating
																									// random ones
				}
				return newStrategy;// return it
			} else {
				return this.strategy; // the current agent
			}
		}
	}

	/**
	 * This method finds locations for agents using Int2D, which is a pair of
	 * coordinates (x,y). This method is used during reproduction to find a new
	 * location for a parent's offspring.
	 * 
	 * @param state
	 * @return
	 */
	public Int2D findLocation(Environment state) {
		if (!state.groups) {// when patches == false
			Int2D location = state.sparseSpace.getRandomEmptyMooreLocation(state, x, y, state.reproductionRadius,
					state.sparseSpace.TOROIDAL, false);
			return location;// this is a random empty location within the reproduction radius of a parent
							// agent
		} else {// else patches == true
			if (state.reproductionRadius == 0) {// if the reproduction radius is 0, offspring always stay in the parents
												// patch
				return new Int2D(x, y);
			} else {// if the reproductionRadius > 0, then find a random location within the
					// parent's reproductionRadius
				int xch = state.random.nextInt(state.reproductionRadius + 1);
				if(state.random.nextBoolean(0.5)) xch = - xch; //50% chance its negative, zeros are not changed
				int ych = state.random.nextInt(state.reproductionRadius + 1);
				if(state.random.nextBoolean(0.5)) ych = - ych; //50% chance its negative, zeros are not changed
				int newx = state.sparseSpace.stx(x + xch);// adjust of a toroidal space
				int newy = state.sparseSpace.sty(y + ych);
				return new Int2D(newx, newy);
			}
		}
	}

	/**
	 * The reproduction method for an agent. When an agent has sufficient resources
	 * to reproduce, it may be able to reproduce an agent so long as the carrying
	 * capacity is not exceeded.
	 * 
	 * @param state
	 * @return
	 */

	public Agent replicate(Environment state) {
		Int2D location;
		if (state.localReproduction) {// if local reproduction is true
			location = this.findLocation(state); // find a location
		} else {
			if (!state.groups)// if patches == false
				location = state.emptyXY();// get any empty location for the offspring
			else
				location = state.locationXY();// otherwise find a random patch
		}
		if (location == null) {// if there is no location, too bad
			resources = 0.0; // reproduction failed
			return null; // reproduction cannot proceed because there is not place for the agent
		}
		if (carryingCapacityExceeded(state)) {// if the carrying capacity is exceeded, too bad
			resources = 0.0; // reproduction failed
			return null; // exceeds
		}
		resources = 0.0;
		// If we get this far, reproduction will be successful!
		int xdir = state.random.nextInt(3) - 1;// get random direcrections of movement
		int ydir = state.random.nextInt(3) - 1;
		Strategy newStrategy = mutation(state);// get a strategy for the offspring. It will be the parents, unless there
												// is a mutation
		long newId = state.id++;// get a new id for the offspring
		Agent a = new Agent(state, newId, newStrategy, 0, location.x, location.y, xdir, ydir, false);// make the
																										// offspring
		a.event = state.schedule.scheduleRepeating(a);// schedule it as repeating
		state.sparseSpace.setObjectLocation(a, location.x, location.y);// put it in the chosen location
		colorByStrategy(a.strategy, state, a);// color it by strategy
		return a;// return the agent
	}

	/**
	 * This strategy colors agents by strategy using an R (red), G (green), B (blue)
	 * color scheme, where the last number is opacity
	 * state.gui.setOvalPortrayal2DColor(a, R (0 to 1.0), G (0 to 1.0, B (0 to 0.1,
	 * Opacity (0 to 1.0));
	 * 
	 * @param strategy
	 * @param state
	 * @param a
	 */

	public void colorByStrategy(Strategy strategy, Environment state, Agent a) {
		switch (strategy) {
		case COOPERATOR:
			state.gui.setOvalPortrayal2DColor(a, (float) 0, (float) 0, (float) 1, (float) 1);
			break;
		case DEFECTOR:
			state.gui.setOvalPortrayal2DColor(a, (float) 1, (float) 0, (float) 0, (float) 1);
			break;
		case WALKAWAY_COOPERATOR:
			state.gui.setOvalPortrayal2DColor(a, (float) 0, (float) 1, (float) 0, (float) 1);
			break;
		case TFT_MOBILE:
			state.gui.setOvalPortrayal2DColor(a, (float) 1, (float) 0, (float) 1, (float) 1);
			break;
		case TFT_SATIONARY:
			state.gui.setOvalPortrayal2DColor(a, (float) 0, (float) 1, (float) 1, (float) 1);
			break;
		case WALKAWAY_DEFECTOR:
			state.gui.setOvalPortrayal2DColor(a, (float) 1, (float) 0.6, (float) 0, (float) 1);
			break;
		case PAVLOV_MOBILE:
			state.gui.setOvalPortrayal2DColor(a, (float) 1, (float) 1, (float) 0.5, (float) 1);
			break;
		case PAVLOV_SATIONARY:
			state.gui.setOvalPortrayal2DColor(a, (float) 1.0, (float) 0.5, (float) 0.5, (float) 1);
			break;
		default:
			state.gui.setOvalPortrayal2DColor(a, (float) 1, (float) 1, (float) 1, (float) 1);
			break;
		}
	}

	/**
	 * The place agent method.
	 * 
	 * @param state
	 */

	public void placeAgent(Environment state) {
		if (!state.groups) { // handle the case with no patches. Must find and empty location
			int tempx = state.sparseSpace.stx(x + xdir);
			int tempy = state.sparseSpace.sty(y + ydir);
			Bag b = state.sparseSpace.getObjectsAtLocation(tempx, tempy);
			if (b == null || b.numObjs == 0) {
				x = tempx;
				y = tempy;
				state.sparseSpace.setObjectLocation(this, x, y);
			}
		} else {// any patch will do
			x = state.sparseSpace.stx(x + xdir);
			y = state.sparseSpace.sty(y + ydir);
			state.sparseSpace.setObjectLocation(this, x, y);
		}
	}

	/**
	 * Searches locally in it search radius for agents and returns a bag of them. If
	 * none, it returns null
	 * 
	 * @param state
	 * @return
	 */
	public Bag search(Environment state) {
		Bag agents = null;
		if (state.groups) {// For Aktipis model
			Bag temp = state.sparseSpace.getObjectsAtLocation(x, y);
			if (temp == null) {
				return null;
			}
			agents = new Bag(temp.numObjs);// size of temp
			agents.addAll(temp); // have to make a copy because temp points to a bag in the sparseSpace
			agents.remove(this);// remove this agent
			return agents;// return the rest if any
		} else {
			agents = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadiusPlayer, state.sparseSpace.TOROIDAL,
					false);
			return agents;// return all the neighboring agents except this agent
		}
	}

	/**
	 * Removes an agent from the simulation
	 * 
	 * @param state
	 */
	public void die(Environment state) {
		state.sparseSpace.remove(this);// remove it from space
		event.stop(); // remove it from the schedule
	}

	/**
	 * tests for whether the carrying capacity is exceeded.
	 * 
	 * @param state
	 * @return
	 */

	public boolean carryingCapacityExceeded(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		if (agents.numObjs < state.carryingCapacity) {
			return false;// there is room for at least one more
		} else {
			return true;// no room for even one more
		}
	}

	/**
	 * Step method required by MASON
	 */

	public void step(SimState state) {
		Environment eState = (Environment) state;
		if (age > maxAge) {// check to see if the agent is to old and must die
			die(eState);
			return;
		}
		if (eState.useLowerBoundsSurvival && resources < eState.lowerBoundsSurvival) {// check to see whether agent
																						// starves to death
			die(eState);
			return;
		}
		if (resources >= eState.resoucesToReproduce) {// check to see whether agent can reproduce
			replicate(eState);
		}
		age++; // aging
		if (played) {
			return;// if already played on this step, then exit step
		}
		play(eState);// if it hasn't played, play
	}

}
