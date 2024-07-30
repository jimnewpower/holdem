package com.primalimited;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class CardDisplay extends JFrame {

    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 150;

    public CardDisplay(List<Card> hand) {
        setTitle("5 Card Hand");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (Card card : hand) {
            JLabel label = new JLabel();
            ImageIcon icon = createScaledImageIcon(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            label.setIcon(icon);
            add(label);
        }

        pack();
        setVisible(true);
    }

    private ImageIcon createScaledImageIcon(String path, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
