package agents;

import sweep.SimStateSweep;
import spaces.Spaces;

/**
 * Initial Conditions:
 * 1. In the Environment class, create n agents
 * 2. place them randomly in space, and
 * 3. place the one Agent in the middle of the space and set frozen = true for that agent.
 * 4. Make sure frozen = false for the remaining n - 1 agents randomly placed in space (at the time they are created).
 */

public class Environment extends SimStateSweep {
    int n = 2000;  // number of agents
    double p = 1.0;  // probability of random movement
    boolean broadRule = true;  // broad rule else narrow rule
    boolean bounded = true;  // is the space bounded or unbounded?
    boolean oneAgentPerCell = false;

    public boolean isOneAgentPerCell() {
        return oneAgentPerCell;
    }

    public void setOneAgentPerCell(boolean oneAgentPerCell) {
        this.oneAgentPerCell = oneAgentPerCell;
    }

    public Environment(long seed, Class observer) {
        super(seed, observer);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public boolean isBroadRule() {
        return broadRule;
    }

    public void setBroadRule(boolean broadRule) {
        this.broadRule = broadRule;
    }

    public boolean isBounded() {
        return bounded;
    }

    public void setBounded(boolean bounded) {
        this.bounded = bounded;
    }

    public void makeAgents() {
        // Check to ensure the number of agents doesn't exceed the grid's capacity
        if (this.oneAgentPerCell && n > gridWidth * gridHeight) {
            System.out.println("Too many agents! Reduce the number.");
            return;
        }

        // Create and place the frozen agent in the middle
        Agent middleAgent = new Agent(true, gridWidth / 2, gridHeight / 2, 0, 0);
        middleAgent.placeAgent(this);
        schedule.scheduleRepeating(middleAgent);

        // Create and place the remaining n-1 unfrozen agents
        for (int i = 1; i < n; i++) {
            Agent agent;
            do {
                int randomX = random.nextInt(gridWidth);
                int randomY = random.nextInt(gridHeight);
                agent = new Agent(false, randomX, randomY, random.nextInt(3) - 1, random.nextInt(3) - 1); // random directions (-1, 0, or 1)
            } while (sparseSpace.getObjectsAtLocation(agent.x, agent.y) != null); // Ensure unique location

            agent.placeAgent(this);
            schedule.scheduleRepeating(agent);
        }
    }


    public void start() {
        super.start();
        spaces = Spaces.SPARSE;
        make2DSpace(spaces, gridWidth, gridHeight);
        makeAgents();
        if (observer != null) {
            observer.initialize(sparseSpace, spaces);//initialize the experimenter by calling initialize in the parent class
        }
    }
}