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
        SwingUtilities.invokeLater(() -> new CardDisplay(deck));
    }
}
