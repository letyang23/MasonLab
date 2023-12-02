package evolutionaryGamesFinal;

import observer.Observer;
import sim.engine.SimState;
import sim.util.Bag;
import sweep.ParameterSweeper;
import sweep.SimStateSweep;

public class Experimenter extends Observer {
	public int cooperators = 0; //variables for counting strategies
	public int defectors = 0;
	public int walkaways = 0;
	public int walkawaysD = 0;
	public int tftm = 0;
	public int tfts = 0;
	public int pavlovm = 0;
	public int pavlovs = 0;

	/**
	 * Stops the experimenter if there are no more agents in space
	 * @param state
	 */
	public void stop(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		if(agents == null || agents.numObjs == 0) {
			event.stop();
		}
	}
	
	/**
	 * Counts up the number of agents using different strategies
	 * @param state
	 */

	public void countStrategies(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		for(int i=0;i<agents.numObjs;i++) {
			Agent a =(Agent)agents.objs[i];
			switch(a.strategy) {
			case COOPERATOR:
				cooperators ++;
				break;
			case DEFECTOR:
				defectors++;
				break;
			case WALKAWAY_COOPERATOR:
				walkaways++;
				break;
			case WALKAWAY_DEFECTOR:
				walkawaysD++;
				break;
			case TFT_MOBILE:
				tftm++;
				break;
			case TFT_SATIONARY:
				tfts++;
				break;
			case PAVLOV_MOBILE:
				pavlovm++;
				break;
			case PAVLOV_SATIONARY:
				pavlovs++;
				break;
			}
		}
	}

	/**
	 * Resets the counting variables to 0
	 * @param state
	 * @return
	 */
	public boolean reset(SimState state) {
		cooperators =0;
		defectors = 0;
		walkaways = 0;
		walkawaysD = 0;
		tftm = 0;
		tfts = 0;
		pavlovm = 0;
		pavlovs = 0;
		return true;
	}
	
	/**
	 * This method collects data for automated simulation sweeps.  Behind the scenes, data are stored in arrays
	 * that allow the calculations of means and standard deviations between simulation runs.
	 * @return
	 */
	public boolean nextInterval() {
		double total = cooperators+defectors+walkaways+walkawaysD+ tftm+tfts+pavlovm+pavlovs;
		data.add(total);
		data.add(cooperators/total);
		data.add(defectors/total);
		data.add(walkaways/total);
		data.add(walkawaysD/total);
		data.add(tftm/total);
		data.add(tfts/total);
		data.add(pavlovm/total);
		data.add(pavlovs/total);
		return false;
	}
	
	/**
	 * constructor method
	 * @param fileName
	 * @param folderName
	 * @param state
	 * @param sweeper
	 * @param precision
	 * @param headers
	 */

	public Experimenter(String fileName, String folderName, SimStateSweep state, ParameterSweeper sweeper,
			String precision, String[] headers) {
		super(fileName, folderName, state, sweeper, precision, headers);

	}
	
	/**
	 * Experimenter updates the population on each step
	 * @param state
	 */
	public void upDatePopulation(Environment state) {
		Bag agents = state.sparseSpace.getAllObjects();
		for(int i=0;i<agents.numObjs;i++) {
			Agent a = (Agent)agents.objs[i];
			a.played=false;//sets played = false for all agents after each step so they'll play on the next step
		}
	}
	
	/**
	 * This method gets the strategy id from each agent based on its strategy.  This is then fed into the histogram chart
	 * to plot the frequency of each strategy. In the histogram chart, you identify each strategy by its id. For example,
	 * cooperator is 1.0 and defector is 2.0.
	 * @param state
	 */
	public void strategyDistribution(Environment state) {
		Bag agents = state.sparseSpace.allObjects;//get remaining agents
		if(agents.numObjs > 0) {
			double [] data = new double[agents.numObjs];
			for(int i = 0;i<data.length;i++) {
				Agent a = (Agent)agents.objs[i];
				data[i]=a.strategy.id();
			}
			if(agents.numObjs > 0)
				this.upDateHistogramChart(0, (int)state.schedule.getSteps(), data, 100);//give it the data with a 1000 milisecond delay
		}
	}

	/**
	 * The experimenter is an agent, so a step method is required by MASON
	 */
	public void step(SimState state) {
		super.step(state);
		upDatePopulation((Environment) state);//call the update method each time
		if(((Environment)state).charts && getdata) { //if charts = true and getdata = true, then update the chart
			strategyDistribution((Environment) state);
		}
		if(((Environment)state).paramSweeps && getdata) {//for parameter sweeps
			reset(state);//reset variables
			countStrategies((Environment) state);//count the strategies
			nextInterval();//This saves data to a file
		}
		
	}
}
