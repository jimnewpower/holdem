package com.primalimited;

import java.util.ArrayList;
import java.util.List;

public class Nuts {

    public Hole findNuts(Flop flop) {
        List<Card> deck = new Deck().getDeck();
        deck.remove(flop.getCard1());
        deck.remove(flop.getCard2());
        deck.remove(flop.getCard3());

        List<Hole> holes = findAllHoleCardCombinations(deck);

        return null;
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
