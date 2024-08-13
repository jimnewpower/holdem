package com.primalimited.card;

import com.primalimited.card.*;

import java.util.ArrayList;
import java.util.List;

public class Nuts {

    public Hole findNuts(List<Card> board) {
        List<Card> deck = new ArrayList<>(new Deck().getDeck());
        deck.removeAll(board);

        List<Hole> holes = findAllHoleCardCombinations(deck);

        HandEvaluator handEvaluator = new HandEvaluator();
        Hole best = null;
        int maxRank = -1;
        for (Hole hole : holes) {
            List<Card> hand = new ArrayList<>(board);
            hand.addAll(hole.getCards());
            HandRank handRank = handEvaluator.evaluateHand(hand);
            int rank = handRank.ordinal();
            if (rank > maxRank) {
                maxRank = rank;
                best = hole;
            } else if (rank == maxRank) {
                if (HandRank.STRAIGHT == handRank) {
                    List<Card> temp1 = new ArrayList<>(board);
                    temp1.addAll(best.getCards());
                    List<Card> temp2 = new ArrayList<>(board);
                    temp2.addAll(hole.getCards());
                    if (handEvaluator.getHighestCardRankInStraight(temp2) > handEvaluator.getHighestCardRankInStraight(temp1)) {
                        best = hole;
                    }
                } else {
                    int rank1 = handEvaluator.getRank(best.getCards());
                    int rank2 = handEvaluator.getRank(hole.getCards());
                    if (rank2 > rank1) {
                        best = hole;
                    } else {
                        if (hole.getHighestRank() > best.getHighestRank()) {
                            best = hole;
                        }
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
