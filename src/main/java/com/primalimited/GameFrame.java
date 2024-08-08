package com.primalimited;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    TablePanel tablePanel = new TablePanel();

    public GameFrame(JButton dealButton) {
        setTitle("Game");
        setSize(1290, 624);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/images/png/poker_chip_100.png")).getImage());
        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(dealButton);
        setVisible(true);
    }

    public void redeal() {
        Game game = new Game(6);
        game.dealGame();
        tablePanel.updateContents(game.getBoard(), game.getPlayerCards());
    }
}
