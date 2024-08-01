package com.primalimited;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;
    private List<Card> burned;
    private List<Card> dealt;
    private List<Card> flop;
    private Card turn;
    private Card river;

    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final int[] RANKS_NUMERIC = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    public Deck() {
        reset();
    }

    public void reset() {
        deck = new ArrayList<>();
        for (String suit : SUITS) {
            int index = 0;
            for (String rank : RANKS) {
                String svgPath = "/images/svg/" + rank + "_of_" + suit + ".svg";
                String pngPath = "/images/png/" + rank + "_of_" + suit + ".png";
                deck.add(new Card(rank, RANKS_NUMERIC[index++], suit, svgPath, pngPath));
            }
        }

        burned = new ArrayList<>();
        dealt = new ArrayList<>();
    }

    public void shuffle() {
        SecureRandom random = new SecureRandom();
        Collections.shuffle(deck, random);
    }

    public List<Card> getDeck() {
        return List.of(deck.toArray(new Card[0]));
    }

    public Card burn() {
        Card burn = deck.remove(0);
        burned.add(burn);
        return burn;
    }

    public Card deal() {
        Card card = deck.remove(0);
        dealt.add(card);
        return card;
    }

    public void flop() {
        burn();
        flop = new ArrayList<>();
        flop.add(deal());
        flop.add(deal());
        flop.add(deal());
    }

    public void turn() {
        burn();
        turn = deal();
    }

    public void river() {
        burn();
        river = deal();
    }

    public List<Card> getFlop() {
        return List.of(flop.toArray(new Card[0]));
    }

    public Card getTurn() {
        return turn;
    }

    public Card getRiver() {
        return river;
    }

    public List<Card> getBoard() {
        List<Card> board = new ArrayList<>();
        board.addAll(flop);
        board.add(turn);
        board.add(river);
        return board;
    }

    public List<Card> getBurned() {
        return List.of(burned.toArray(new Card[0]));
    }

    public List<Card> getDealt() {
        return List.of(dealt.toArray(new Card[0]));
    }
}

