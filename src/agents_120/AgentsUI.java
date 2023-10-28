package agents_120;

import java.awt.Color;

import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class AgentsUI extends GUIStateSweep {

	public AgentsUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		
	}

	public static void main(String[] args) {
		AgentsUI.initialize(Environment.class, null, AgentsUI.class, 600, 600, Color.WHITE, Color.blue, true, Spaces.SPARSE);

	}

}
