package com.primalimited;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HoleRankingsQuiz extends JFrame {
    private static final int CARD_WIDTH = 111;
    private static final int CARD_HEIGHT = 161;

    private static final int N_HOLES = 3;
    private static final Color BACKGROUND_COLOR = Color.lightGray.brighter().brighter().brighter().brighter();

    JPanel topPanel = new JPanel();

    private Images images = new Images();

    HoleCardRanks ranks = new HoleCardRanks();

    private int count = 0;
    private int correctCount = 0;

    public HoleRankingsQuiz() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        add(topPanel, BorderLayout.NORTH);
        topPanel.add(new JLabel("Select the best hole ranking"));

        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        add(cardsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        JButton nextButton = new JButton("Deal");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(e -> showQuiz(cardsPanel));

        showQuiz(cardsPanel);

        setTitle("Hole Rankings Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((CARD_WIDTH * 2 + 10) * (N_HOLES + 1) + 30, (CARD_HEIGHT + 10) * N_HOLES + 125);
        setVisible(true);
    }

    private void showQuiz(JPanel cardsPanel) {
        cardsPanel.removeAll();
        final Map<Hole, Integer> map = ranks.getRandomMap(N_HOLES);
        final Hole highestRank = ranks.getHighestRank(map);
        final Map<Hole, JPanel> holeLabelMap = new HashMap<>();
        map.keySet().forEach(hole -> {
            JButton holeButton = new JButton();
            holeButton.setLayout(new BorderLayout());
            JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            holeButton.add(cardPanel, BorderLayout.CENTER);
            hole.getCards().forEach(card -> {
                JLabel cardImageLabel = new JLabel();
                ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), CARD_WIDTH, CARD_HEIGHT);
                cardImageLabel.setIcon(icon);
                cardPanel.add(cardImageLabel);
            });
            cardsPanel.add(holeButton);
            JPanel labelPanel = new JPanel();
            holeLabelMap.put(hole, labelPanel);
            holeButton.add(labelPanel, BorderLayout.SOUTH);
            holeButton.addActionListener(e -> {
                count++;
                Hole selected = hole;
                if (highestRank.equals(selected)) {
                    correctCount++;
                }
                map.keySet().stream().forEach(h -> {
                    JPanel panel = holeLabelMap.get(h);
                    panel.removeAll();
                    int rank = map.get(h);
                    String label = "";
                    if (selected == h) {
                        label += "*";
                    }
                    label += "Rank: " + rank;
                    panel.add(new JLabel(label));
                    panel.validate();
                    panel.repaint();
                });
                topPanel.removeAll();
                topPanel.add(new JLabel("Hole Rankings Quiz: " + correctCount + " of " + count + " correct"));
                validate();
                repaint();
            });
        });
        validate();
        repaint();
    }
}
