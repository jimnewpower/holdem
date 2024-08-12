package com.primalimited;

import java.util.Map;

/**
 * The Chen formula.
 *
 * 1. Score your highest card only. Do not add any points for your lower card.
 * A = 10 points.
 * K = 8 points.
 * Q = 7 points.
 * J = 6 points.
 * 10 to 2 = 1/2 of card value. (e.g. a 6 would be worth 3 points)
 *
 * 2. Multiply pairs by 2 of one card’s value. However, minimum score for a pair is 5.
 * (e.g. KK = 16 points, 77 = 7 points, 22 = 5 points)
 *
 * 3. Add 2 points if cards are suited.
 *
 * 4. Subtract points if their is a gap between the two cards.
 * No gap = -0 points.
 * 1 card gap = -1 points.
 * 2 card gap = -2 points.
 * 3 card gap = -4 points.
 * 4 card gap or more = -5 points. (Aces are high this step, so hands like A2, A3 etc. have a 4+ gap.)
 *
 * 5. Add 1 point if there is a 0 or 1 card gap and both cards are lower than a Q. (e.g. JT, 75, 32 etc, this bonus point does not apply to pocket pairs)
 * 6. Round half point scores up. (e.g. 7.5 rounds up to 8)
 *
 * For step 5, it’s easier to refer to this extra 1 point as a "straight bonus" to save confusion between steps 4 and 5. Subtracting 1 point for 1 gap and then adding it back again for lower cards seems a bit awkward I know, but that’s the way it works.
 */
public class ChenFormula {

    private Hole hole;

    public ChenFormula(Hole hole) {
        this.hole = hole;
    }

    @Override
    public String toString() {
        return "ChenFormula{" +
                "hole=" + hole +
                " score=" + evaluate() +
                '}';
    }

    public int evaluate() {
        float rank = 0;
        rank += highestCardScore();
        rank = hole.isPair() ? Math.max(5, rank * 2) : rank;
        rank += hole.isSuited() ? 2 : 0;
        rank -= gapPenalty();
        rank += gapBonus();
        return (int) Math.round(rank);
    }

    float highestCardScore() {
        Rank rank = hole.getHighestRankedCard();
        switch (rank) {
            case ACE -> {
                return 10f;
            }
            case KING -> {
                return 8f;
            }
            case QUEEN -> {
                return 7f;
            }
            case JACK -> {
                return 6f;
            }
        }
        return (float) (hole.getHighestRank() / 2.f);
    }

    float pairValue() {
        if (!hole.isPair()) {
            return 0.f;
        }
        return Math.max(5, highestCardScore() * 2);
    }

    int gapPenalty() {
        if (hole.isPair()) {
            return 0;
        }

        int gap = hole.getSpread();
        gap -= 1;

        int penalty = 0;

        switch (gap) {
            case 1:
                penalty = 1;
                break;
            case 2:
                penalty = 2;
                break;
            case 3:
                penalty = 4;
                break;
        }
        if (gap >= 4) {
            penalty = 5;
        }

        return penalty;
    }

    int gapBonus() {
        if (hole.isPair()) {
            return 0;
        }
        if (hole.getHighestRank() < Rank.QUEEN.getRankNumeric() && hole.getSpread() <= 2) {
            return 1;
        }
        return 0;
    }

    public static Hole getHighestRank(Map<Hole, Integer> map) {
        Hole high = null;
        int highest = -1;
        for (Hole hole : map.keySet()) {
            int rank = map.get(hole);
            if (rank > highest) {
                highest = rank;
                high = hole;
            }
        }
        return high;
    }

}
