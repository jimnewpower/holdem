package com.primalimited.card;

import java.util.Comparator;

public class HandRankComparator implements Comparator<HandRank> {
    @Override
    public int compare(HandRank o1, HandRank o2) {
        int result = o2.compareTo(o1);
        return result;
    }
}
