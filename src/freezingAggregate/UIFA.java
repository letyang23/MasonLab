package freezingAggregate;

import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

import java.awt.Color;

public class UIFA extends GUIStateSweep {
    public UIFA(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor, boolean agentPortrayal) {
        super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
    }

    public static void main(String[] args) {
        UIFA.initialize(Environment.class, null, UIFA.class, 600, 600, Color.WHITE, Color.BLUE, true, Spaces.SPARSE);
    }
}
