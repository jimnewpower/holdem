package com.primalimited;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private int nPlayers;

    private List<Card> board;
    private List<Hole> playerCards;

    private Deck deckOfCards = new Deck();

    public Game(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public void dealHoleCards() {
        deckOfCards = new Deck();
        deckOfCards.shuffle();
        this.board = new ArrayList<>();

        Map<Integer, Card> dealt = new HashMap<>();
        for (int player = 0; player < nPlayers; player++) {
            dealt.put(player, deckOfCards.deal());
        }
        Map<Integer, Hole> playerCardsMap = new HashMap<>();
        for (int player = 0; player < nPlayers; player++) {
            Hole hole = new Hole(dealt.get(player), deckOfCards.deal());
            playerCardsMap.put(player, hole);
        }
        playerCards = new ArrayList<Hole>();
        for (int player = 0; player < nPlayers; player++) {
            playerCards.add(playerCardsMap.get(player));
        }
    }

    public void dealFlop() {
        List<Card> flop = deckOfCards.flop();
        this.board.addAll(flop);
    }

    public void dealTurn() {
        Card turn = deckOfCards.turn();
        this.board.add(turn);
    }

    public void dealRiver() {
        Card river = deckOfCards.river();
        this.board.add(river);
    }

    public void dealGame() {
        Deck deckOfCards = new Deck();
        deckOfCards.shuffle();
        List<Card> deck = new ArrayList<>(deckOfCards.getDeck());

        Map<Integer, Card> dealt = new HashMap<>();
        for (int player = 0; player < nPlayers; player++) {
            dealt.put(player, deckOfCards.deal());
        }
        Map<Integer, Hole> playerCardsMap = new HashMap<>();
        for (int player = 0; player < nPlayers; player++) {
            Hole hole = new Hole(dealt.get(player), deckOfCards.deal());
            playerCardsMap.put(player, hole);
        }
        playerCards = new ArrayList<Hole>();
        for (int player = 0; player < nPlayers; player++) {
            playerCards.add(playerCardsMap.get(player));
        }

        List<Card> flop = deckOfCards.flop();
        Card turn = deckOfCards.turn();
        Card river = deckOfCards.river();
        this.board = new ArrayList<>();
        this.board.addAll(flop);
        this.board.add(turn);
        this.board.add(river);
//
//        HandEvaluator handEvaluator = new HandEvaluator();
//        for (int player = 0; player < nPlayers; player++) {
//            List<Card> hand = buildHand(playerCards.get(player), flop, turn, river);
//            HandRank handRank = handEvaluator.evaluateHand(hand);
//        }
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
