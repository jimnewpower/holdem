package com.primalimited;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private List<Card> deck;

    private int freshDeckHash;

    public Deck() {
        reset();
    }

    public void reset() {
        deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            int index = 0;
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }

        this.freshDeckHash = hashCode();
    }

    @Override
    public int hashCode() {
        final StringBuilder builder = new StringBuilder();
        this.deck.forEach(c -> builder.append(c.toString()));
        return builder.toString().hashCode();
    }

    public void shuffleSimple() {
        Collections.shuffle(deck, new SecureRandom());
    }

    public LinkedList<Card> shuffle() {
        SecureRandom random = new SecureRandom();
        int nJavaShuffles = 0;
        int nCuts = 0;
        int nRiffles = 0;
        final int min = 3;
        // want a minimum of 3 of each type of shuffle
        while (nJavaShuffles < min || nCuts < min || nRiffles < min) {
            int rand = Math.abs(random.nextInt() % 3);
            switch (rand) {
                case 0 -> {
                    Collections.shuffle(deck);
                    nJavaShuffles++;
                }
                case 1 -> {
                    cutDeck();
                    nCuts++;
                }
                case 2 -> {
                    riffleShuffle();
                    nRiffles++;
                }
            }
        }

        return new LinkedList<>(deck);
    }

    private int randomCutIndex() {
        int randomHalf = deck.size() / 2;
        int randomOffset = (int)(Math.random() * 100) % 5;
        randomOffset = Math.random() <= 0.5 ? -randomOffset : randomOffset;
        return randomHalf + randomOffset;
    }

    public void cutDeck() {
        int halfSize = randomCutIndex();

        // Split the deck into two halves
        List<Card> firstHalf = new ArrayList<>(deck.subList(0, halfSize));
        List<Card> secondHalf = new ArrayList<>(deck.subList(halfSize, deck.size()));

        // Reorder the deck by placing the second half before the first half
        deck.clear();
        deck.addAll(secondHalf);
        deck.addAll(firstHalf);
    }

    public void riffleShuffle() {
        int halfSize = randomCutIndex();

        // Split the deck into two halves
        List<Card> firstHalf = new ArrayList<>(deck.subList(0, halfSize));
        List<Card> secondHalf = new ArrayList<>(deck.subList(halfSize, deck.size()));

        deck.clear();

        // Interleave the cards from the two halves
        SecureRandom random = new SecureRandom();
        while (!firstHalf.isEmpty() || !secondHalf.isEmpty()) {
            if (!firstHalf.isEmpty() && (secondHalf.isEmpty() || random.nextBoolean())) {
                deck.add(firstHalf.remove(0));
            }
            if (!secondHalf.isEmpty() && (firstHalf.isEmpty() || random.nextBoolean())) {
                deck.add(secondHalf.remove(0));
            }
        }
    }

    public List<Card> getDeck() {
        return List.of(deck.toArray(new Card[0]));
    }

}

