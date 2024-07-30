package com.primalimited;

import javax.swing.*;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Deck deck = new Deck();
        deck.shuffle();
        List<Card> hand = deck.getDeck().subList(0, 5);

        SwingUtilities.invokeLater(() -> new CardDisplay(hand));
    }
}
