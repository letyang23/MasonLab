package evolutionaryGamesFinal;

/**
 * Strategy list. Constants are in caps and their numerical id is in parentheses
 * @author jeffreyschank
 *
 */
public enum Strategy {
	COOPERATOR (1.0),
	DEFECTOR (2.0),
	WALKAWAY_COOPERATOR (3.0),
	WALKAWAY_DEFECTOR (4.0),
	TFT_MOBILE (5.0),
	TFT_SATIONARY (6.0),
	PAVLOV_MOBILE (7.0),
	PAVLOV_SATIONARY (8.0);
	
	/**
	 * For the chart used in this simulation the code below allows you to get the numerical id of a strategy from
	 * the agent's strategy.  So, for an agent a, the id would be a.strategy.id();  If the agent's strategy was
	 * a.strategy = WALKAWAY_COOPERATOR, then a.strategy.id()== 3.0.
	 */
	private final double id;
	
	Strategy(double id){
		this.id =id;
	}
	
	public double id() {
		return id;
	}
}
