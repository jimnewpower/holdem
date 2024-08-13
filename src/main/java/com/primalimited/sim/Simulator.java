package com.primalimited.sim;

import com.primalimited.card.*;

import java.util.*;

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

        List<Hole> all = List.of(holeCardRanks.getRanked().get(0));
        for (Hole heroHole : all) {
            System.out.println("Hero: " + heroHole);
            int winCount = 0;
            int tieCount = 0;
            int lossCount = 0;
            for (int i = 0; i < nHands; i++) {
                Deck deckOfCards = new Deck();
                LinkedList<Card> deck = deckOfCards.shuffle();

                deck.removeAll(heroHole.getCards());

                Map<Integer, Card> playerCardMap = new HashMap<>();
                for (int j = 0; j < nPlayers - 1; j++) {
                    playerCardMap.put(j, deck.poll());
                }

                List<Hole> holes = new ArrayList<>(nPlayers-1);
                for (int j = 0; j < nPlayers - 1; j++) {
                    Card card = deck.poll();
                    holes.add(new Hole(playerCardMap.get(j), card));
                }

                // burn
                List<Card> flop = new ArrayList<>();
                deck.poll(); // burn card
                flop.add(deck.poll());
                flop.add(deck.poll());
                flop.add(deck.poll());

                deck.poll(); // burn card
                Card turn = deck.poll();

                deck.poll(); // burn card
                Card river = deck.poll();

                Board board = new Board(flop, turn, river);

                HandRank heroRank = null;
                List<HandRank> villainRanks = new ArrayList<>();
                for (int player = 0; player < nPlayers; player++) {
                    List<Card> hand = player == 0 ? buildHand(heroHole, board) : buildHand(holes.get(player-1), board);
                    if (player == 0) {
                        heroRank = handEvaluator.evaluateHand(hand);
                    } else {
                        villainRanks.add(handEvaluator.evaluateHand(hand));
                    }
                }

                villainRanks.sort(new HandRankComparator());
                HandRank topVillain = villainRanks.get(0);

                if (heroRank.ordinal() > topVillain.ordinal()) {
                    winCount++;
                } else if (heroRank.ordinal() == topVillain.ordinal()) {
                    tieCount++;
                } else {
                    lossCount++;
                }
            }
            System.out.println("Wins: " + winCount + " Ties: " + tieCount + " Losses: " + lossCount);
        }
    }

    private List<Card> buildHand(Hole hole, Board board) {
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board.getCards());
        return hand;
    }
}
