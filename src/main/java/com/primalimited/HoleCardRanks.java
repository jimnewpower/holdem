package com.primalimited;

import java.security.SecureRandom;
import java.util.*;

import static com.primalimited.CardBuilder.*;

public class HoleCardRanks {

    List<Hole> ranked = new ArrayList<>();

    public HoleCardRanks() {
        // Source: https://preflophands.com/
        addPair(Rank.ACE);
        addPair(Rank.KING);
        addPair(Rank.QUEEN);
        addSuited(Rank.ACE, Rank.KING);
        addPair(Rank.JACK);
        addSuited(Rank.ACE, Rank.QUEEN);
        addSuited(Rank.KING, Rank.QUEEN);
        addSuited(Rank.ACE, Rank.JACK);
        addSuited(Rank.KING, Rank.JACK);
        addPair(Rank.TEN);
        addOffsuit(Rank.ACE, Rank.KING);
        addSuited(Rank.ACE, Rank.TEN);
        addSuited(Rank.QUEEN, Rank.JACK);
        addSuited(Rank.KING, Rank.TEN);
        addSuited(Rank.QUEEN, Rank.TEN);
        addSuited(Rank.JACK, Rank.TEN);
        addPair(Rank.NINE);
        addOffsuit(Rank.ACE, Rank.QUEEN);
        addSuited(Rank.ACE, Rank.NINE);
        addOffsuit(Rank.KING, Rank.QUEEN);
        addPair(Rank.EIGHT);
        addSuited(Rank.KING, Rank.NINE);
        addSuited(Rank.TEN, Rank.NINE);
        addSuited(Rank.ACE, Rank.EIGHT);
        addSuited(Rank.QUEEN, Rank.NINE);
        addSuited(Rank.JACK, Rank.NINE);
        addOffsuit(Rank.ACE, Rank.JACK);
        addSuited(Rank.ACE, Rank.FIVE);
        addPair(Rank.SEVEN);
        addSuited(Rank.ACE, Rank.SEVEN);
        addOffsuit(Rank.KING, Rank.JACK);
        addSuited(Rank.ACE, Rank.FOUR);
        addSuited(Rank.ACE, Rank.THREE);
        addSuited(Rank.ACE, Rank.SIX);
        addOffsuit(Rank.QUEEN, Rank.JACK);
        addPair(Rank.SIX);
        addSuited(Rank.KING, Rank.EIGHT);
        addSuited(Rank.TEN, Rank.EIGHT);
        addSuited(Rank.ACE, Rank.TWO);
        addSuited(Rank.NINE, Rank.EIGHT);
        addSuited(Rank.JACK, Rank.EIGHT);
        addOffsuit(Rank.ACE, Rank.TEN);
        addSuited(Rank.QUEEN, Rank.EIGHT);
        addSuited(Rank.KING, Rank.SEVEN);
        addOffsuit(Rank.KING, Rank.TEN);
        addPair(Rank.FIVE);
        addOffsuit(Rank.JACK, Rank.TEN);
        addSuited(Rank.EIGHT, Rank.SEVEN);
        addOffsuit(Rank.QUEEN, Rank.TEN);
        addPair(Rank.FOUR);
        addPair(Rank.THREE);
        addPair(Rank.TWO);
        addSuited(Rank.KING, Rank.SIX);
        addSuited(Rank.NINE, Rank.SEVEN);
        addSuited(Rank.KING, Rank.FIVE);
        addSuited(Rank.SEVEN, Rank.SIX);
        addSuited(Rank.TEN, Rank.SEVEN);
        addSuited(Rank.KING, Rank.FOUR);
        addSuited(Rank.KING, Rank.THREE);
        addSuited(Rank.KING, Rank.TWO);
        addSuited(Rank.QUEEN, Rank.SEVEN);
        addSuited(Rank.EIGHT, Rank.SIX);
        addSuited(Rank.SIX, Rank.FIVE);
        addSuited(Rank.JACK, Rank.SEVEN);
        addSuited(Rank.FIVE, Rank.FOUR);
        addSuited(Rank.QUEEN, Rank.SIX);
        addSuited(Rank.SEVEN, Rank.FIVE);
        addSuited(Rank.NINE, Rank.SIX);
        addSuited(Rank.QUEEN, Rank.FIVE);
        addSuited(Rank.SIX, Rank.FOUR);
        addSuited(Rank.QUEEN, Rank.FOUR);
        addSuited(Rank.QUEEN, Rank.THREE);
        addOffsuit(Rank.TEN, Rank.NINE);
        addSuited(Rank.TEN, Rank.SIX);
        addSuited(Rank.QUEEN, Rank.TWO);
        addOffsuit(Rank.ACE, Rank.NINE);
        addSuited(Rank.FIVE, Rank.THREE);
        addSuited(Rank.EIGHT, Rank.FIVE);
        addSuited(Rank.JACK, Rank.SIX);
        addOffsuit(Rank.JACK, Rank.NINE);
        addOffsuit(Rank.KING, Rank.NINE);
        addSuited(Rank.JACK, Rank.FIVE);
        addOffsuit(Rank.QUEEN, Rank.NINE);
        addSuited(Rank.FOUR, Rank.THREE);
        addSuited(Rank.SEVEN, Rank.FOUR);
        addSuited(Rank.JACK, Rank.FOUR);
        addSuited(Rank.JACK, Rank.THREE);
        addSuited(Rank.NINE, Rank.FIVE);
        addSuited(Rank.JACK, Rank.TWO);
        addSuited(Rank.SIX, Rank.THREE);
        addOffsuit(Rank.ACE, Rank.EIGHT);
        addSuited(Rank.FIVE, Rank.TWO);
        addSuited(Rank.TEN, Rank.FIVE);
        addSuited(Rank.EIGHT, Rank.FOUR);
        addSuited(Rank.TEN, Rank.FOUR);
        addSuited(Rank.TEN, Rank.THREE);
        addSuited(Rank.FOUR, Rank.TWO);
        addSuited(Rank.TEN, Rank.TWO);
        addOffsuit(Rank.NINE, Rank.EIGHT);
        addOffsuit(Rank.TEN, Rank.EIGHT);
        addOffsuit(Rank.ACE, Rank.FIVE);
        addOffsuit(Rank.ACE, Rank.SEVEN);
        addSuited(Rank.SEVEN, Rank.THREE);
        addOffsuit(Rank.ACE, Rank.FOUR);
        addSuited(Rank.THREE, Rank.TWO);
        addSuited(Rank.NINE, Rank.FOUR);
        addSuited(Rank.NINE, Rank.THREE);
        addOffsuit(Rank.JACK, Rank.EIGHT);
        addOffsuit(Rank.ACE, Rank.THREE);
        addSuited(Rank.SIX, Rank.TWO);
        addSuited(Rank.NINE, Rank.TWO);
        addOffsuit(Rank.KING, Rank.EIGHT);
        addOffsuit(Rank.ACE, Rank.SIX);
        addOffsuit(Rank.EIGHT, Rank.SEVEN);
        addOffsuit(Rank.QUEEN, Rank.EIGHT);
        addSuited(Rank.EIGHT, Rank.THREE);
        addOffsuit(Rank.ACE, Rank.TWO);
        addSuited(Rank.EIGHT, Rank.TWO);
        addOffsuit(Rank.NINE, Rank.SEVEN);
        addSuited(Rank.SEVEN, Rank.TWO);
        addOffsuit(Rank.SEVEN, Rank.SIX);
        addOffsuit(Rank.KING, Rank.SEVEN);
        addOffsuit(Rank.SIX, Rank.FIVE);
        addOffsuit(Rank.TEN, Rank.SEVEN);
        addOffsuit(Rank.KING, Rank.SIX);
        addOffsuit(Rank.EIGHT, Rank.SIX);
        addOffsuit(Rank.FIVE, Rank.FOUR);
        addOffsuit(Rank.KING, Rank.FIVE);
        addOffsuit(Rank.JACK, Rank.SEVEN);
        addOffsuit(Rank.SEVEN, Rank.FIVE);
        addOffsuit(Rank.QUEEN, Rank.SEVEN);
        addOffsuit(Rank.KING, Rank.FOUR);
        addOffsuit(Rank.KING, Rank.THREE);
        addOffsuit(Rank.NINE, Rank.SIX);
        addOffsuit(Rank.KING, Rank.TWO);
        addOffsuit(Rank.SIX, Rank.FOUR);
        addOffsuit(Rank.QUEEN, Rank.SIX);
        addOffsuit(Rank.FIVE, Rank.THREE);
        addOffsuit(Rank.EIGHT, Rank.FIVE);
        addOffsuit(Rank.TEN, Rank.SIX);
        addOffsuit(Rank.QUEEN, Rank.FIVE);
        addOffsuit(Rank.FOUR, Rank.THREE);
        addOffsuit(Rank.QUEEN, Rank.FOUR);
        addOffsuit(Rank.QUEEN, Rank.THREE);
        addOffsuit(Rank.SEVEN, Rank.FOUR);
        addOffsuit(Rank.QUEEN, Rank.TWO);
        addOffsuit(Rank.JACK, Rank.SIX);
        addOffsuit(Rank.SIX, Rank.THREE);
        addOffsuit(Rank.JACK, Rank.FIVE);
        addOffsuit(Rank.NINE, Rank.FIVE);
        addOffsuit(Rank.FIVE, Rank.TWO);
        addOffsuit(Rank.JACK, Rank.FOUR);
        addOffsuit(Rank.JACK, Rank.THREE);
        addOffsuit(Rank.FOUR, Rank.TWO);
        addOffsuit(Rank.JACK, Rank.TWO);
        addOffsuit(Rank.EIGHT, Rank.FOUR);
        addOffsuit(Rank.TEN, Rank.FIVE);
        addOffsuit(Rank.TEN, Rank.FOUR);
        addOffsuit(Rank.THREE, Rank.TWO);
        addOffsuit(Rank.TEN, Rank.THREE);
        addOffsuit(Rank.SEVEN, Rank.THREE);
        addOffsuit(Rank.TEN, Rank.TWO);
        addOffsuit(Rank.SIX, Rank.TWO);
        addOffsuit(Rank.NINE, Rank.FOUR);
        addOffsuit(Rank.NINE, Rank.THREE);
        addOffsuit(Rank.NINE, Rank.TWO);
        addOffsuit(Rank.EIGHT, Rank.THREE);
        addOffsuit(Rank.EIGHT, Rank.TWO);
        addOffsuit(Rank.SEVEN, Rank.TWO);
    }

    private void addOffsuit(Rank rank1, Rank rank2) {
        add(new Card(rank1, Suit.DIAMONDS), new Card(rank2, Suit.CLUBS));
    }

    private void addPair(Rank rank) {
        add(new Card(rank, Suit.HEARTS), new Card(rank, Suit.SPADES));
    }

    private void addSuited(Rank rank1, Rank rank2) {
        Suit suit = Suit.random();
        add(new Card(rank1, suit), new Card(rank2, suit));
    }

    private void add(Card card1, Card card2) {
        ranked.add(new Hole(card1, card2));
    }

    public List<Hole> getRanked() {
        return Collections.unmodifiableList(ranked);
    }

    public List<Hole> getRandom(int n) {
        List<Hole> random = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            random.add(ranked.get((int) (Math.random() * ranked.size())));
        }
        return Collections.unmodifiableList(random);
    }

    public Map<Hole, Integer> getRandomMap(int n) {
        Map<Hole, Integer> random = new HashMap<>();
        SecureRandom randomizer = new SecureRandom();
        while (random.size() < n) {
            int index = randomizer.nextInt(ranked.size());
            random.put(ranked.get(index), index+1);
        }
        return Collections.unmodifiableMap(random);
    }

    public Hole getHighestRank(Map<Hole, Integer> map) {
        Hole high = null;
        int low = Integer.MAX_VALUE;
        for (Hole hole : map.keySet()) {
            int rank = map.get(hole);
            if (rank < low) {
                low = rank;
                high = hole;
            }
        }
        return high;
    }
}
