package com.primalimited;

import java.util.ArrayList;
import java.util.List;

public class Nuts {

    public Hole findNuts(List<Card> board) {
        List<Card> deck = new Deck().getDeck();
        deck.removeAll(board);

        List<Hole> holes = findAllHoleCardCombinations(deck);

        HandEvaluator handEvaluator = new HandEvaluator();
        Hole best = null;
        int maxRank = 0;
        for (Hole hole : holes) {
            List<Card> hand = new ArrayList<>(board);
            hand.addAll(hole.getCards());
            HandRank handRank = handEvaluator.evaluateHand(hand);
            int rank = handRank.ordinal();
            if (rank > maxRank) {
                maxRank = rank;
                best = hole;
            } else if (rank == maxRank) {
                if (best != null) {
                    int rank1 = handEvaluator.getRank(best.getCards());
                    int rank2 = handEvaluator.getRank(hole.getCards());
                    if (rank2 > rank1) {
                        best = hole;
                    }
                }
            }
        }
        return best;
    }

    public List<Hole> findAllHoleCardCombinations(List<Card> deck) {
        List<Hole> holes = new ArrayList<>();
        int deckSize = deck.size();
        for (int i = 0; i < deckSize - 1; i++) {
            for (int j = i + 1; j < deckSize; j++) {
                holes.add(new Hole(deck.get(i), deck.get(j)));
            }
        }
        return holes;
    }
}
