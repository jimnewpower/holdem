package com.primalimited;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HoleRankingsView extends JFrame {

    private static final int CARD_WIDTH = 44;
    private static final int CARD_HEIGHT = 65;

    JPanel cardsPanel = new JPanel();

    public HoleRankingsView() {
        setTitle("Hole Rankings");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(cardsPanel, BorderLayout.CENTER);

        Images images = new Images();
        HoleCardRanks ranks = new HoleCardRanks();
        for (Hole hole : ranks.getRanked()) {
            for (Card card : hole.getCards()) {
                JLabel cardImageLabel = new JLabel();
                ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
                cardImageLabel.setIcon(icon);
                cardsPanel.add(cardImageLabel);
            }
        }
        setVisible(true);
    }
}
