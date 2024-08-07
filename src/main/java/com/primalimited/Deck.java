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

    public List<Card> flop() {
        burn();
        flop = new ArrayList<>();
        flop.add(deal());
        flop.add(deal());
        flop.add(deal());
        return Collections.unmodifiableList(flop);
    }

    public Card turn() {
        burn();
        turn = deal();
        return turn;
    }

    public Card river() {
        burn();
        river = deal();
        return river;
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

