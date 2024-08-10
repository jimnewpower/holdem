package com.primalimited;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

import static com.primalimited.CardBuilder.*;

class HandEvaluatorTest {

    @Test
    void evaluateHand() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofSpades();
        Card card2 = King().ofSpades();
        Card card3 = Queen().ofSpades();
        Card card4 = Jack().ofSpades();
        Card card5 = Ten().ofSpades();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(HandRank.ROYAL_FLUSH, handEvaluator.evaluateHand(hand));

        card1 = Nine().ofSpades();
        card2 = King().ofSpades();
        card3 = Queen().ofSpades();
        card4 = Jack().ofSpades();
        card5 = Ten().ofSpades();
        hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(HandRank.STRAIGHT_FLUSH, handEvaluator.evaluateHand(hand));
    }

    @Test
    void royalFlush7() {
        List<Card> board = List.of(
                King().ofSpades(),
                Six().ofSpades(),
                Jack().ofSpades(),
                Queen().ofSpades(),
                Ten().ofSpades(),
                Two().ofHearts(),
                Ace().ofSpades()
        );
        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(board);
        assertEquals(HandRank.ROYAL_FLUSH, handRank);
    }

    @Test
    void straightFlush7() {
        List<Card> board = List.of(
                Four().ofHearts(),
                Six().ofSpades(),
                Five().ofHearts(),
                Two().ofHearts(),
                Nine().ofSpades(),
                Six().ofHearts(),
                Three().ofHearts()
        );
        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(board);
        assertEquals(HandRank.STRAIGHT_FLUSH, handRank);
    }

    @Test
    void containsPair() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofHearts();
        Card card2 = Ace().ofSpades();
        Card card3 = Four().ofClubs();
        Card card4 = Six().ofDiamonds();
        Card card5 = Eight().ofHearts();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.containsPair(hand));
    }

    @Test
    void containsTwoPairs() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofHearts();
        Card card2 = Ace().ofDiamonds();
        Card card3 = Four().ofClubs();
        Card card4 = Four().ofSpades();
        Card card5 = Nine().ofClubs();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.containsTwoPairs(hand));
    }

    @Test
    void isThreeOfAKind() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofHearts();
        Card card2 = Ace().ofSpades();
        Card card3 = Ace().ofDiamonds();
        Card card4 = Four().ofDiamonds();
        Card card5 = Nine().ofClubs();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isThreeOfAKind(hand));
    }

    @Test
    void isStraight() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofHearts();
        Card card2 = King().ofSpades();
        Card card3 = Queen().ofHearts();
        Card card4 = Jack().ofDiamonds();
        Card card5 = Ten().ofClubs();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isStraight(hand));
    }

    @Test
    void isFlush() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofHearts();
        Card card2 = King().ofHearts();
        Card card3 = Nine().ofHearts();
        Card card4 = Four().ofHearts();
        Card card5 = Ten().ofHearts();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isFlush(hand));
    }

    @Test
    void isFourOfaKind() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Six().ofHearts();
        Card card2 = Six().ofSpades();
        Card card3 = Four().ofDiamonds();
        Card card4 = Six().ofClubs();
        Card card5 = Six().ofDiamonds();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isFourOfAKind(hand));
    }

    @Test
    void fourOfaKindRank() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = Ace().ofHearts();
        Card card2 = Ace().ofSpades();
        Card card3 = Ace().ofClubs();
        Card card4 = Ace().ofDiamonds();
        Card card5 = Ten().ofHearts();
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(14, handEvaluator.getFourOfAKindRank(hand));

        card1 = Seven().ofHearts();
        card2 = Seven().ofSpades();
        card3 = Seven().ofClubs();
        card4 = Seven().ofDiamonds();
        card5 = Ten().ofHearts();
        hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(7, handEvaluator.getFourOfAKindRank(hand));
    }

    @Test
    void specificHand() {
        Hole hole = new Hole(Ace().ofSpades(), Two().ofDiamonds());
        List<Card> board = List.of(
                Three().ofClubs(),
                Five().ofClubs(),
                Ace().ofSpades(),
                Two().ofSpades(),
                Three().ofDiamonds()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.TWO_PAIR, handRank);
    }

    @Test
    void specificHand2() {
        Hole hole = new Hole(Ace().ofDiamonds(), Five().ofClubs());
        List<Card> board = List.of(
                Two().ofHearts(),
                Ace().ofHearts(),
                Four().ofSpades(),
                Four().ofHearts(),
                Three().ofHearts()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.STRAIGHT, handRank);
    }

    @Test
    void specificHand3() {
        Hole hole = new Hole(Four().ofDiamonds(), Five().ofDiamonds());
        List<Card> board = List.of(
                Three().ofSpades(),
                Ace().ofHearts(),
                Two().ofSpades(),
                Queen().ofClubs(),
                Nine().ofClubs()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.STRAIGHT, handRank);
    }


    @Test
    void specificHand4() {
        Hole hole = new Hole(Three().ofHearts(), Five().ofDiamonds());
        List<Card> board = List.of(
                Queen().ofHearts(),
                Ten().ofSpades(),
                Ace().ofHearts(),
                Four().ofHearts(),
                Two().ofClubs()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.STRAIGHT, handRank);
    }

    @Test
    void specificHand5() {
        Hole hole = new Hole(Ten().ofClubs(), Ace().ofClubs());
        List<Card> board = List.of(
                Jack().ofHearts(),
                Four().ofSpades(),
                Two().ofDiamonds(),
                Three().ofSpades(),
                Five().ofClubs()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.STRAIGHT, handRank);
    }

    @Test
    void specificHand6() {
        Hole hole = new Hole(Queen().ofDiamonds(), Two().ofDiamonds());
        List<Card> board = List.of(
                Ace().ofSpades(),
                King().ofSpades(),
                Queen().ofSpades(),
                Seven().ofDiamonds(),
                Jack().ofDiamonds()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.ONE_PAIR, handRank);
    }

    @Test
    void testFlush() {
        Hole hole = new Hole(Ace().ofSpades(), Two().ofSpades());
        List<Card> board = List.of(
                Three().ofSpades(),
                Four().ofSpades(),
                Five().ofSpades(),
                Six().ofDiamonds(),
                Seven().ofHearts()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.FLUSH, handRank);

        assertEquals(14, handEvaluator.getHighestCardRankInFlush(hand));
    }

    @Test
    void testStraight() {
        Hole hole = new Hole(Queen().ofClubs(), King().ofClubs());
        List<Card> board = List.of(
                Nine().ofClubs(),
                Jack().ofSpades(),
                Queen().ofDiamonds(),
                Two().ofHearts(),
                Ten().ofDiamonds()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.STRAIGHT, handRank);

        assertEquals(13, handEvaluator.getHighestCardRankInStraight(hand));
    }

    @Test
    void fullHouse() {
        Hole hole = new Hole(Ace().ofClubs(), Ace().ofDiamonds());
        List<Card> board = List.of(
                Ace().ofHearts(),
                King().ofSpades(),
                King().ofHearts(),
                Queen().ofDiamonds(),
                Queen().ofClubs()
        );
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.FULL_HOUSE, handRank);
    }

    @Test
    void twoSets() {
        Hole hole = new Hole(Jack().ofDiamonds(), Queen().ofHearts());
        List<Card> board = List.of(
                Queen().ofClubs(),
                Jack().ofHearts(),
                Queen().ofSpades(),
                Jack().ofSpades(),
                Ten().ofHearts()
        );

        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);

        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        assertEquals(HandRank.FULL_HOUSE, handRank);
    }
}