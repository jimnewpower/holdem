package com.primalimited;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HoleTest {

    @Test
    void isPlayable() {
        HoleCardRanks holeCardRanks = new HoleCardRanks();
        List<Hole> ranked = holeCardRanks.getRanked();
        int rank = 1;
        int playableCount = 0;
        for (Hole hole : ranked) {
            if (hole.isPlayable()) {
                playableCount++;
                System.out.println(rank + " : " + hole);
            }
            rank++;
        }
        assertEquals(119, playableCount);
    }
}