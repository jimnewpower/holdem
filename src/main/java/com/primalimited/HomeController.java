package com.primalimited;

import javax.swing.*;
import java.awt.*;

public class HomeController implements Controller {
    private JFrame frame;

    @Override
    public void init() {
        SwingUtilities.invokeLater(() -> buildFrame());
    }

    @Override
    public void removeAll() {
        if (frame == null) {
            throw new IllegalStateException("Frame not initialized");
        }
        frame.removeAll();
        frame.validate();
        frame.repaint();
    }

    @Override
    public void add(Component component, Object constraints) {
        if (frame == null) {
            throw new IllegalStateException("Frame not initialized");
        }
        if (!(component instanceof Component)) {
            throw new IllegalArgumentException("Component must be a Component");
        }
        frame.removeAll();
        frame.add((Component) component, constraints);
        frame.validate();
        frame.repaint();
    }

    @Override
    public void resize(int width, int height) {
        frame.setSize(width, height);
        frame.validate();
        frame.repaint();
    }

    private JFrame buildFrame() {
        this.frame = new JFrame("Texas Hold'em");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(new JLabel("Welcome to Texas Hold'em"), BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(buttonPanel, BorderLayout.CENTER);

        JButton holeCardQuizButton = new JButton("Hole Card Rankings Quiz");
        holeCardQuizButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new HoleRankingsQuiz();
            });
        });
        buttonPanel.add(holeCardQuizButton);
        holeCardQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(20)); // 20 pixels of space

        JButton randomHandsButton = new JButton("Random Hands");
        randomHandsButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                Deck deck = new Deck();
                deck.shuffle();
                CardDisplay cardDisplay = new CardDisplay(deck);
            });
        });
        buttonPanel.add(randomHandsButton);
        randomHandsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(20)); // 20 pixels of space

        JButton holeRankingsButton = new JButton("Hole Card Rankings");
        holeRankingsButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new HoleRankingsView();
            });
        });
        buttonPanel.add(holeRankingsButton);
        holeRankingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(20)); // 20 pixels of space

        frame.setSize(300, 300);
        frame.setVisible(true);
        return frame;
    }
}
