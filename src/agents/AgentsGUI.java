package agents;

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
        String[] title = {"Agents Frozen"};//A string array, where every entry is the title of a chart
        String[] x = {"Time Steps"};//A string array, where every entry is the x-axis title
        String[] y = {"Number"};//A string array, where every entry is the y-axis title
        //AgentsGUI.initializeArrayTimeSeriesChart(number of charts, chart titles, x-axis titles, y-axis titles);
        AgentsGUI.initializeArrayTimeSeriesChart(1, title, x, y);//creates as many charts as indicated by the first number.
        //All arrays must have the same number of elements as the number of charts.

        AgentsGUI.initialize(Environment.class, Experimenter.class, AgentsGUI.class, 400, 400, Color.WHITE, Color.blue, true, Spaces.SPARSE);
    }
}