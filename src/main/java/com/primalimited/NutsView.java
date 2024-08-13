package com.primalimited;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NutsView extends JFrame {

    private static final int CARD_WIDTH = 222;
    private static final int CARD_HEIGHT = 323;

    private enum State {
        PRE_FLOP,
        FLOP,
        TURN,
        RIVER;
    }

    private List<Card> currentHand;
    private Hole currentNuts;
    private State state = State.PRE_FLOP;
    private JPanel textPanel = new JPanel();
    private JPanel nutsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

    private Images images = new Images();

    public NutsView(Deck deck) {
        setTitle("Nuts");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setIconImage(new ImageIcon(getClass().getResource("/images/png/poker_chip_100.png")).getImage());

        JPanel cardsPanel = new JPanel();
        add(cardsPanel, BorderLayout.CENTER);

        JPanel boardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        cardsPanel.add(boardPanel);
        cardsPanel.add(nutsPanel);

        add(textPanel, BorderLayout.NORTH);
        textPanel.add(new JLabel(""));

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton nextButton = new JButton("Deal");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(e -> deal(boardPanel, deck));

        deal(boardPanel, deck);

        setSize((CARD_WIDTH + 10) * 5 + 30, (CARD_HEIGHT + 10) * 2 + 125);
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
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            cardPanel.add(cardImageLabel);
        }
        addBlankCard(cardPanel);
        addBlankCard(cardPanel);
        this.state = State.FLOP;
        currentHand = new ArrayList<>(flop);
        showNuts();
        return flop;
    }

    private void addBlankCard(JPanel cardPanel) {
        JLabel cardImageLabel = new JLabel();
        String pngPath = "/images/png/" + "kem-cardback" + ".png";
        ImageIcon icon = images.createScaledImageIcon(pngPath, CARD_WIDTH, CARD_HEIGHT);
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
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            cardPanel.add(cardImageLabel);
        }
        showNuts();
        addBlankCard(cardPanel);
        this.state = State.TURN;
    }

    private void river(JPanel cardPanel, Deck deck) {
        cardPanel.remove(4);
        List<Card> hand = deck.getDeck().subList(5, 6); // Burn 1, turn 1
        for (Card card : hand) {
            currentHand.add(card);
            JLabel cardImageLabel = new JLabel();
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            cardPanel.add(cardImageLabel);
        }
        this.state = State.RIVER;

        textPanel.removeAll();
        HandRank boardRank = new HandEvaluator().evaluateHand(currentHand);
        List<Card> totalHand = new ArrayList<>(currentHand);
        showNuts();
        totalHand.addAll(currentNuts.getCards());
        HandRank nutsRank = new HandEvaluator().evaluateHand(totalHand);
        textPanel.add(new JLabel("Board: " + boardRank.toString() + "  Nuts: " + nutsRank.toString()));
    }

    private void showNuts() {
        Nuts nuts = new Nuts();
        this.currentNuts = nuts.findNuts(currentHand);
        nutsPanel.removeAll();
        for (Card card : currentNuts.getCards()) {
            JLabel cardImageLabel = new JLabel();
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            nutsPanel.add(cardImageLabel);
        }
    }
}
