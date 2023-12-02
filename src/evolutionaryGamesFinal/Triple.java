package evolutionaryGamesFinal;
/**
 * A triple is a basic packet of memory, the first element is an opponents id, the second, an opponent's
 * last strategy, and the third is the agent's strategy used against that opponent.
 * @author jcschankadmin
 *
 */
class Triple {
	long id;//this is the id of the agent that is part of the memory
	Strategy opponentStrategy;//this is the remembered agent's strategy
	Strategy myStrategy;//this is the strategy played by the agent with the memory
	
	/**
	 * constructor method
	 * @param id
	 * @param opponentStrategy
	 * @param myStrategy
	 */
	public Triple(long id, Strategy opponentStrategy, Strategy myStrategy) {
		super();
		this.id = id;
		this.opponentStrategy = opponentStrategy;
		this.myStrategy = myStrategy;
	}
}