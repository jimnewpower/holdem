package com.primalimited;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {

    @Test
    void testSimulator() {
        Simulator simulator = new Simulator(100000, 2);
        simulator.run();
    }
}