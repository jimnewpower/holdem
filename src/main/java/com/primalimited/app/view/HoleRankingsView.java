package com.primalimited.app.view;

import com.primalimited.card.Hole;
import com.primalimited.card.HoleCardRanks;
import com.primalimited.card.Card;

import javax.swing.*;
import java.awt.*;

public class HoleRankingsView extends JFrame {

    private static final int CARD_WIDTH = 44;
    private static final int CARD_HEIGHT = 65;

    private JPanel cardsPanel = new JPanel();

    private Images images = new Images();

    public HoleRankingsView() {
        setTitle("Hole Rankings");
        setSize((CARD_WIDTH + 40) * 20 + 64, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setIconImage(new ImageIcon(getClass().getResource("/images/png/poker_chip_100.png")).getImage());

        cardsPanel.setLayout(new GridLayout(0, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        add(scrollPane, BorderLayout.CENTER);

        Images images = new Images();
        HoleCardRanks ranks = new HoleCardRanks();
        int rank = 1;
        for (Hole hole : ranks.getRanked()) {
            cardsPanel.add(createHoleCardPanel(hole, rank++));
        }
        setVisible(true);
    }

    private JPanel createHoleCardPanel(Hole hole, int rank) {
        JPanel holeCardPanel = new JPanel();
        holeCardPanel.setBorder(BorderFactory.createEtchedBorder());
        holeCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        holeCardPanel.add(new JLabel(String.valueOf(rank)));
        for (Card card : hole.getCards()) {
            JLabel cardImageLabel = new JLabel();
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
            cardImageLabel.setIcon(icon);
            holeCardPanel.add(cardImageLabel);
        }
        return holeCardPanel;
    }
}
