package agents_120;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Agent implements Steppable {
    int x;
    int y;
    int xdir;
    int ydir;

    public Agent(int x, int y, int xdir, int ydir) {
        super();
        this.x = x;
        this.y = y;
        this.xdir = xdir;
        this.ydir = ydir;
    }

    public void move(Environment state) {
        if (!state.random.nextBoolean(state.pActive)) {
            return;
        }
        if (state.random.nextBoolean(state.pRandom)) {
            xdir = state.random.nextInt(3) - 1;
            ydir = state.random.nextInt(3) - 1;
        }
        placeAgent(state);

    }

    public void placeAgent(Environment state) {
        int tempx = state.sparseSpace.stx(x + xdir);
        int tempy = state.sparseSpace.sty(y + ydir);
        if (state.oneAgentperCell) {
            if (state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
                x = tempx;
                y = tempy;
                state.sparseSpace.setObjectLocation(this, x, y);
            }
        } else {//false
            x = tempx;
            y = tempy;
            state.sparseSpace.setObjectLocation(this, x, y);
        }
    }

    public void aggregate(Environment state) {
        Bag neighbors = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadius, state.sparseSpace.TOROIDAL, false);
        xdir = decideX(state, neighbors);
        ydir = decideY(state, neighbors);
        placeAgent(state);
    }

    public int decideX(Environment state, Bag neighbors) {
        int negX = 0, posX = 0;
        for (int i = 0; i < neighbors.numObjs; i++) {
            Agent a = (Agent) neighbors.objs[i];
            if (a.x > this.x) {
                posX++;
            } else if (a.x < this.x) {
                negX++;
            }
        }
        if (posX > negX)
            return 1;
        if (posX < negX) {
            return -1;
        }
        return 0;
    }

    public int decideY(Environment state, Bag neighbors) {
        int negY = 0, posY = 0;
        for (int i = 0; i < neighbors.numObjs; i++) {
            Agent a = (Agent) neighbors.objs[i];
            if (a.y > this.y) {
                posY++;
            } else if (a.y < this.y) {
                negY++;
            }
        }
        if (posY > negY)
            return 1;
        if (posY < negY) {
            return -1;
        }
        return 0;
    }

    public void step(SimState state) {
        Environment eState = (Environment) state;
        if (eState.random.nextBoolean(eState.pAggregate)) {
            aggregate(eState);
        } else {
            move(eState);
        }

    }

}
