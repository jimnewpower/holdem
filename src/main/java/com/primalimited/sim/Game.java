package com.primalimited.sim;

import com.primalimited.card.Card;
import com.primalimited.card.Deck;
import com.primalimited.card.Hole;

import java.util.*;

public class Game {

    private int nPlayers;

    private List<Card> board;
    private List<Hole> playerCards;

    private Deck deckOfCards = new Deck();

    private LinkedList<Card> cards;

    public Game(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public void shuffle() {
        deckOfCards.reset();
        this.cards = deckOfCards.shuffle();
    }

    private Card dealCard() {
        return cards.poll();
    }

    public void dealHoleCards() {
        this.board = new ArrayList<>();

        Map<Integer, Card> dealt = new HashMap<>();
        for (int player = 0; player < nPlayers; player++) {
            dealt.put(player, dealCard());
        }

        Map<Integer, Hole> playerCardsMap = new HashMap<>();
        for (int player = 0; player < nPlayers; player++) {
            Hole hole = new Hole(dealt.get(player), dealCard());
            playerCardsMap.put(player, hole);
        }

        playerCards = new ArrayList<Hole>();
        for (int player = 0; player < nPlayers; player++) {
            playerCards.add(playerCardsMap.get(player));
        }
    }

    public void dealFlop() {
        // burn
        dealCard();
        board.clear();
        board.add(dealCard());
        board.add(dealCard());
        board.add(dealCard());
    }

    public void dealTurn() {
        // burn
        dealCard();
        board.add(dealCard());
    }

    public void dealRiver() {
        dealCard();
        board.add(dealCard());
    }
    private List<Card> buildHand(Hole hole, List<Card> flop, Card turn, Card river) {
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(flop);
        hand.add(turn);
        hand.add(river);
        return hand;
    }

    public List<Card> getBoard() {
        return board;
    }

    public List<Hole> getPlayerCards() {
        return playerCards;
    }
}
