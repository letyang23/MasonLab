package evolutionaryGames;

import java.awt.Color;

import khModelLab4.AgentsGUI;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class GUI extends GUIStateSweep {



	public GUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
	}

	public static void main(String[] args) {
		//TODO:  Add histogram initialization code here
		//All arrays must have the same number of elements as the number of charts.
		String[] title2 = {"Strategy Distribution in Population"};//A string array, where every entry is the title of a chart
		String[] x2 = {"Strategies"};//A string array, where every entry is the x-axis title
		String[] y2 = {"Number of Agents"};//A string array, where every entry is the y-axis title
		AgentsGUI.initializeArrayHistogramChart(1, title2, x2, y2, new int[100]);
		GUI.initialize(Environment.class, Experimenter.class, GUI.class, 400, 400, Color.WHITE, Color.RED, false, spaces.SPARSE);

	}

}
