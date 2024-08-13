package com.primalimited.app.view;

import com.primalimited.sim.Game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private TablePanel tablePanel = new TablePanel();

    private Game game = new Game(6);

    private enum State {
        PREFLOP, FLOP, TURN, RIVER
    };
    private State state = State.PREFLOP;

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

    public void deal() {
        switch (state) {
            case PREFLOP:
                game.shuffle();
                game.dealHoleCards();
                state = State.FLOP;
                break;
            case FLOP:
                game.dealFlop();
                state = State.TURN;
                break;
            case TURN:
                game.dealTurn();
                state = State.RIVER;
                break;
            case RIVER:
                game.dealRiver();
                state = State.PREFLOP;
                break;
        }
        tablePanel.updateContents(game.getBoard(), game.getPlayerCards());
    }
}
