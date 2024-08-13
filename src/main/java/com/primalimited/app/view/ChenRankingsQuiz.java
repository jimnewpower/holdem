package com.primalimited.app.view;

import com.primalimited.card.ChenFormula;
import com.primalimited.card.Hole;
import com.primalimited.card.HoleCardRanks;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ChenRankingsQuiz extends JFrame {
    private static final int CARD_WIDTH = 111;
    private static final int CARD_HEIGHT = 161;

    private static final int N_HOLES = 3;
    private static final Color CORRECT_BACKGROUND = Color.green;
    private static final Color INCORRECT_BACKGROUND = Color.red;
    private static final Color BACKGROUND_COLOR = Color.lightGray.brighter().brighter().brighter().brighter();

    JPanel topPanel = new JPanel();

    private Images images = new Images();

    HoleCardRanks ranks = new HoleCardRanks();

    private int count = 0;
    private int correctCount = 0;

    public ChenRankingsQuiz() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        setIconImage(new ImageIcon(getClass().getResource("/images/png/poker_chip_100.png")).getImage());

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

        setTitle("Chen Hole Rankings Quiz");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize((CARD_WIDTH * 2 + 10) * (N_HOLES + 1) + 30, (CARD_HEIGHT + 200));
        setVisible(true);
    }

    private void showQuiz(JPanel cardsPanel) {
        cardsPanel.removeAll();
        final Map<Hole, Integer> holeRankMap = ranks.getRandomUniqueChenRankings(N_HOLES);
        final Hole highestRank = ChenFormula.getHighestRank(holeRankMap);
        final Map<Hole, JPanel> holePanelMap = new HashMap<>();
        final Set<JButton> buttons = new HashSet<>();
        holeRankMap.keySet().forEach(hole -> {
            JButton holeButton = new JButton();
            buttons.add(holeButton);
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
            holePanelMap.put(hole, labelPanel);
            holeButton.add(labelPanel, BorderLayout.SOUTH);
            holeButton.addActionListener(e -> {
                count++;
                Hole selected = hole;
                buttons.stream().forEach(b -> b.setEnabled(false));
                if (highestRank.equals(selected)) {
                    correctCount++;
                    holeButton.setBackground(CORRECT_BACKGROUND);
                } else {
                    holeButton.setBackground(INCORRECT_BACKGROUND);
                }
                holeRankMap.keySet().stream().forEach(h -> {
                    JPanel panel = holePanelMap.get(h);
                    panel.removeAll();
                    int rank = new ChenFormula(h).evaluate();
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
                double percent = (double) correctCount / count * 100;
                String message = "Hole Rankings Quiz: " + correctCount + " of " + count + " correct " + String.format("%.2f", percent) + "%";
                topPanel.add(new JLabel(message));
                validate();
                repaint();
            });
        });
        validate();
        repaint();
    }
}
