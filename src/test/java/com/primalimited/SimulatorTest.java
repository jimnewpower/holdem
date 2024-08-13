package com.primalimited;

import com.primalimited.sim.Simulator;
import org.junit.jupiter.api.Test;

class SimulatorTest {

    @Test
    void testSimulator() {
        Simulator simulator = new Simulator(100000, 2);
        simulator.run();
    }
}