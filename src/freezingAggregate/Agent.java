package freezingAggregate;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;

public class Agent implements Steppable {

    public boolean frozen = false;
    public int x;
    public int y;
    int dirx;
    int diry;

    public Agent(boolean frozen, int x, int y, int dirx, int diry) {
        super();
        this.frozen = frozen;
        this.x = x;
        this.y = y;
        this.dirx = dirx;
        this.diry = diry;
    }

    public void placeAgent(Environment state) {
        int tempx;
        int tempy;

        if (state.bounded) {
            tempx = bx(x + dirx, state);                //correct for a bounded space on x-axis;
            tempy = by(y + diry, state);                //correct for a bounded space on y-axis;
        } else {
            tempx = state.sparseSpace.stx(x + dirx);    //correct for a toroidal space on x-axis;
            tempy = state.sparseSpace.sty(y + diry);    //correct for a toroidal space on y-axis;
        }

        //Get the bag of objects at location <tempx, tempy>
        Bag b = state.sparseSpace.getObjectsAtLocation(tempx, tempy);

        if (b == null) {
            x = tempx;
            y = tempy;
            state.sparseSpace.setObjectLocation(this, x, y);
        }

        if (state.broadRule) {
            if (state.bounded)
                //getMooreNeightors for a bounded space
                b = state.sparseSpace.getMooreNeighbors(x, y, 1, SparseGrid2D.BOUNDED, false);
            else
                //getMooreNeightors for a toroidal space
                b = state.sparseSpace.getMooreNeighbors(x, y, 1, SparseGrid2D.TOROIDAL, false);
            testFrozen(state, b);
        } else if (b != null) {
            testFrozen(state, b);
        }
    }

    // PlaceAgent method is used here to place the agent.
    public void move(Environment state){
        if(frozen)
            return;

        if(state.random.nextBoolean(state.p)){
            dirx = state.random.nextInt(3) - 1;
            diry = state.random.nextInt(3) - 1;
        }
        placeAgent(state);
    }

    //neighbors will contain the surrounding agents for the
    //broad rule or one agent at a location for the narrow
    //rule.
    public void testFrozen(Environment state, Bag neighbors) {
        for (int i = 0; i < neighbors.numObjs; i++) {
            Agent neighbor = (Agent) neighbors.objs[i];
            if (neighbor.frozen) {
                frozen = true;
                return;
            }
        }
    }

    public int bx(int x, Environment state) {
        if (x < 0) return 0;
        if (x >= state.gridWidth) return state.gridWidth - 1;
        return x;
    }

    public int by(int y, Environment state) {
        if (y < 0) return 0;
        if (y >= state.gridHeight) return state.gridHeight - 1;
        return y;
    }

    @Override
    public void step(SimState state) {
        if (frozen) return; //nothing else to do
        move((Environment) state);
    }
}
