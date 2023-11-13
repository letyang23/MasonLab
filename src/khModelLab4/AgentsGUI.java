package khModelLab4;

import java.awt.Color;
import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class AgentsGUI extends GUIStateSweep {

	

	public AgentsGUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		//The code below allows you to create as many time series charts as you want.
				String[] title = {"Pair Correlation", "Mean Attractiveness"};//A string array, where every entry is the title of a chart
				String[] x = {"Time Steps","Time Steps"};//A string array, where every entry is the x-axis title
				String[] y = {"Correlation", "Mean"};//A string array, where every entry is the y-axis title
				//AgentsGUI.initializeArrayTimeSeriesChart(number of charts, chart titles, x-axis titles, y-axis titles);
				AgentsGUI.initializeArrayTimeSeriesChart(2, title, x, y);//creates as many charts as indicated by the first number.
				//All arrays must have the same number of elements as the number of charts.
				String[] title2 = {"Frequency Distribution of Attractiveness"};//A string array, where every entry is the title of a chart
				String[] x2 = {"Time Steps"};//A string array, where every entry is the x-axis title
				String[] y2 = {"Frequency"};//A string array, where every entry is the y-axis title
				AgentsGUI.initializeArrayHistogramChart(1, title2, x2, y2, new int[10]);
		AgentsGUI.initialize(Environment.class,Experimenter.class, AgentsGUI.class, 600, 600, Color.WHITE, Color.BLUE, false, Spaces.SPARSE);

	}

}
