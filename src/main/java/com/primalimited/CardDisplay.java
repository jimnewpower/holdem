package com.primalimited;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardDisplay extends JFrame {

    private static final int CARD_WIDTH = 222;
    private static final int CARD_HEIGHT = 323;

    private enum State {
        PRE_FLOP,
        FLOP,
        TURN,
        RIVER;
    }

    private List<Card> currentHand;
    private State state = State.PRE_FLOP;
    private JPanel textPanel = new JPanel();

    public CardDisplay(Deck deck) {
        setTitle("Holdem");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        add(textPanel, BorderLayout.NORTH);
        textPanel.add(new JLabel(""));

        add(cardPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton nextButton = new JButton("Deal");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(e -> deal(cardPanel, deck));

        deal(cardPanel, deck);

        setSize((CARD_WIDTH + 10) * 5 + 30, CARD_HEIGHT * 2);
        setVisible(true);
    }

    private void deal(JPanel cardPanel, Deck deck) {
        switch (state) {
            case PRE_FLOP:
                flop(cardPanel, deck);
                break;
            case FLOP:
                turn(cardPanel, deck);
                break;
            case TURN:
                river(cardPanel, deck);
                break;
            case RIVER:
                cardPanel.removeAll();
                deck.shuffle();
                flop(cardPanel, deck);
                break;
        }
        this.validate();
        this.repaint();
    }

    private List<Card> flop(JPanel cardPanel, Deck deck) {
        List<Card> flop = deck.getDeck().subList(1, 4); // Burn 1, turn 3
        textPanel.removeAll();
        textPanel.add(new JLabel(""));
        for (Card card : flop) {
            JLabel cardImageLabel = new JLabel();
            ImageIcon icon = createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            cardPanel.add(cardImageLabel);
        }
        addBlankCard(cardPanel);
        addBlankCard(cardPanel);
        this.state = State.FLOP;
        currentHand = new ArrayList<>(flop);
        return flop;
    }

    private void addBlankCard(JPanel cardPanel) {
        JLabel cardImageLabel = new JLabel();
        String pngPath = "/images/png/" + "kem-cardback" + ".png";
        ImageIcon icon = createScaledImageIcon(pngPath, CARD_WIDTH, CARD_HEIGHT);
        cardImageLabel.setIcon(icon);
        cardPanel.add(cardImageLabel);
    }

    private void turn(JPanel cardPanel, Deck deck) {
        cardPanel.remove(4);
        cardPanel.remove(3);
        List<Card> hand = deck.getDeck().subList(4, 5); // Burn 1, turn 1
        for (Card card : hand) {
            currentHand.add(card);
            JLabel cardImageLabel = new JLabel();
            ImageIcon icon = createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            cardPanel.add(cardImageLabel);
        }
        addBlankCard(cardPanel);
        this.state = State.TURN;
    }

    private void river(JPanel cardPanel, Deck deck) {
        cardPanel.remove(4);
        List<Card> hand = deck.getDeck().subList(5, 6); // Burn 1, turn 1
        for (Card card : hand) {
            currentHand.add(card);
            JLabel cardImageLabel = new JLabel();
            ImageIcon icon = createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            cardPanel.add(cardImageLabel);
        }
        this.state = State.RIVER;

        textPanel.removeAll();
        HandRank handRank = new HandEvaluator().evaluateHand(currentHand);
        textPanel.add(new JLabel(handRank.toString()));
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

    private ImageIcon createScaledImageIconWithWhiteBackground(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource(path));

            // Create a new image with a white background
            BufferedImage whiteBackgroundImage = new BufferedImage(
                    originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = whiteBackgroundImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            // Scale the image with a white background
            Image scaledImage = whiteBackgroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
