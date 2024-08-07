package com.primalimited;

import java.util.ArrayList;
import java.util.List;

public class Simulator implements Runnable {

    private int nHands;
    private int nPlayers;

    public Simulator(int nHands, int nPlayers) {
        this.nHands = nHands;
        this.nPlayers = nPlayers;
    }

    public Simulator() {
        this.nHands = 1000;
        this.nPlayers = 2;
    }

    @Override
    public void run() {
        HandEvaluator handEvaluator = new HandEvaluator();
        HoleCardRanks holeCardRanks = new HoleCardRanks();

        List<Hole> all = holeCardRanks.getRanked();
        for (Hole heroHole : all) {
            for (int i = 0; i < nHands; i++) {
                Deck deckOfCards = new Deck();
                deckOfCards.shuffle();
                List<Card> deck = deckOfCards.getDeck();
                deck.removeAll(heroHole.getCards());

                List<Hole> holes = holeCardRanks.getRandom(nPlayers-1);
                for (Hole villanHoles : holes) {
                    deck.removeAll(villanHoles.getCards());
                }

                List<Card> flop = deckOfCards.flop();
                Card turn = deckOfCards.turn();
                Card river = deckOfCards.river();

                for (int player = 0; player < nPlayers; player++) {
                    List<Card> hand = player == 0 ? buildHand(heroHole, flop, turn, river) : buildHand(holes.get(player-1), flop, turn, river);
                    HandRank handRank = handEvaluator.evaluateHand(hand);
                }
            }
        }
    }

    private List<Card> buildHand(Hole hole, List<Card> flop, Card turn, Card river) {
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(flop);
        hand.add(turn);
        hand.add(river);
        return hand;
    }
}
