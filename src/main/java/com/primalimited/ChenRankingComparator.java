package com.primalimited;

import java.util.Comparator;

public class ChenRankingComparator implements Comparator<Hole> {

    @Override
    public int compare(Hole o1, Hole o2) {
        int rank1 = new ChenFormula(o1).evaluate();
        int rank2 = new ChenFormula(o2).evaluate();
        return Integer.compare(rank2, rank1);
    }
}
