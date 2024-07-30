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
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("Ace", 14, "Spades");
        Card card3 = new Card("Ace", 14, "Hearts");
        Card card4 = new Card("4", 4, "Diamonds");
        Card card5 = new Card("9", 9, "Clubs");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isThreeOfAKind(hand));
    }

    @Test
    void isStraight() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("King", 13, "Spades");
        Card card3 = new Card("Queen", 12, "Hearts");
        Card card4 = new Card("Jack", 11, "Diamonds");
        Card card5 = new Card("10", 10, "Clubs");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isStraight(hand));
    }

    @Test
    void isFlush() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("King", 13, "Hearts");
        Card card3 = new Card("9", 9, "Hearts");
        Card card4 = new Card("4", 4, "Hearts");
        Card card5 = new Card("10", 10, "Hearts");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isFlush(hand));
    }

    @Test
    void isFourOfaKind() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("Ace", 14, "Spades");
        Card card3 = new Card("Ace", 14, "Clubs");
        Card card4 = new Card("Ace", 14, "Diamonds");
        Card card5 = new Card("10", 10, "Hearts");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.isFourOfAKind(hand));
    }

    @Test
    void fourOfaKindRank() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("Ace", 14, "Spades");
        Card card3 = new Card("Ace", 14, "Clubs");
        Card card4 = new Card("Ace", 14, "Diamonds");
        Card card5 = new Card("10", 10, "Hearts");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(14, handEvaluator.getFourOfAKindRank(hand));

        card1 = new Card("7", 7, "Hearts");
        card2 = new Card("7", 7, "Spades");
        card3 = new Card("7", 7, "Clubs");
        card4 = new Card("7", 7, "Diamonds");
        card5 = new Card("10", 10, "Hearts");
        hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(7, handEvaluator.getFourOfAKindRank(hand));

    }
}