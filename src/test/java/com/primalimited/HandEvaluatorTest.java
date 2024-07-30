package com.primalimited;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandEvaluatorTest {

    @Test
    void evaluateHand() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Spades");
        Card card2 = new Card("King", 13, "Spades");
        Card card3 = new Card("Queen", 12, "Spades");
        Card card4 = new Card("Jack", 11, "Spades");
        Card card5 = new Card("10", 10, "Spades");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(HandRank.ROYAL_FLUSH, handEvaluator.evaluateHand(hand));

        card1 = new Card("9", 9, "Spades");
        card2 = new Card("King", 13, "Spades");
        card3 = new Card("Queen", 12, "Spades");
        card4 = new Card("Jack", 11, "Spades");
        card5 = new Card("10", 10, "Spades");
        hand = List.of(card1, card2, card3, card4, card5);
        assertEquals(HandRank.STRAIGHT_FLUSH, handEvaluator.evaluateHand(hand));

    }

    @Test
    void containsPair() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("Ace", 14, "Spades");
        Card card3 = new Card("4", 4, "Hearts");
        Card card4 = new Card("5", 5, "Diamonds");
        Card card5 = new Card("6", 6, "Clubs");
        List<Card> hand = List.of(card1, card2, card3, card4, card5);
        assertTrue(handEvaluator.containsPair(hand));
    }

    @Test
    void containsTwoPairs() {
        HandEvaluator handEvaluator = new HandEvaluator();
        Card card1 = new Card("Ace", 14, "Hearts");
        Card card2 = new Card("Ace", 14, "Spades");
        Card card3 = new Card("4", 4, "Hearts");
        Card card4 = new Card("4", 4, "Diamonds");
        Card card5 = new Card("9", 9, "Clubs");
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
}