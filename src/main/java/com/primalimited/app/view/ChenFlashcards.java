package com.primalimited.app.view;

import com.primalimited.card.*;
import com.primalimited.stats.QuizTally;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;

public class ChenFlashcards extends JFrame {

    private JButton dealButton;

    private Images images = new Images();

    private BufferedImage backgroundImage;

    private Panel panel;

    private JComboBox<Integer> answerComboBox;

    private JLabel answerLabel = new JLabel();

    private int currentChenRanking = -1;

    private String currentChenRankingLabel = "";

    private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    private JTable table = new JTable();

    private ChenFormula chenFormula;

    private QuizTally quizTally = new QuizTally();

    private JLabel quizTallyLabel = new JLabel();

    private List<Hole> randomHoleCards;

    public ChenFlashcards(JButton dealButton) {
        this.dealButton = dealButton;
        this.randomHoleCards = new HoleCardRanks().getRandomizedList();

        setTitle("Chen Flashcards");
        setSize(1290, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/images/png/poker_chip_100.png")).getImage());
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel, BorderLayout.CENTER);
        panel = new Panel();
        mainPanel.add(panel);
        panel.init();

        JPanel controlPanel = new JPanel(new BorderLayout());
        mainPanel.add(controlPanel);
        initControlPanel(controlPanel);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(dealButton);
        deal();
        setVisible(true);

        splitPane.setDividerLocation(0.5);
        validate();
        repaint();
    }

    private void initControlPanel(JPanel controlPanel) {
        controlPanel.setBorder(BorderFactory.createEtchedBorder());

        controlPanel.add(splitPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        splitPane.setTopComponent(topPanel);

        JLabel selectLabel = new JLabel("Select Chen Ranking: ");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(selectLabel);

        answerLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel comboPanel = new JPanel();
        topPanel.add(comboPanel);
        answerComboBox = new JComboBox<>(ChenFormula.RANKINGS);
        answerComboBox.setSelectedIndex(0);
        comboPanel.add(answerComboBox);

        answerComboBox.setFont(new Font("Arial", Font.PLAIN, 20));

        // Set the preferred size of the JComboBox
        answerComboBox.setSize(new Dimension(150, 40));
        answerComboBox.setMinimumSize(new Dimension(150, 40));
        answerComboBox.setMaximumSize(new Dimension(150, 40));
        answerComboBox.setPreferredSize(new Dimension(150, 40));

        answerComboBox.addActionListener(e -> evaluateAnswer(answerComboBox.getItemAt(answerComboBox.getSelectedIndex())));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        splitPane.setBottomComponent(bottomPanel);

        bottomPanel.add(answerLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        Font tableFont = new Font("Arial", Font.PLAIN, 18); // Font name, style, size
        table.setFont(tableFont);
        table.setRowHeight(30); // Adjust the row height to fit the larger text

        quizTallyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        bottomPanel.add(quizTallyLabel, BorderLayout.SOUTH);
    }

    public void evaluateAnswer(int chenRankingAnswer) {
        disableControls();
        dealButton.setEnabled(true);

        if (chenRankingAnswer != currentChenRanking) {
            answerLabel.setText("Wrong. Ranking is: " + currentChenRanking);
            quizTally.tallyIncorrectAnswer();
        } else {
            answerLabel.setText("Correct!");
            quizTally.tallyCorrectAnswer();
        }
        quizTallyLabel.setText(quizTally.toString());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnCount(2);
        tableModel.setRowCount(5);
        tableModel.setColumnIdentifiers(new String[] { "Category", "Score" });
        int row = 0;
        int col = 0;
        col = 0;
        Map<String, Integer> map = chenFormula.getRankingValues();
        for (String key : map.keySet()) {
            tableModel.setValueAt(key, row, col++);
            tableModel.setValueAt(map.get(key), row, col++);
            row++;
            col = 0;
        }
        tableModel.setValueAt("Total", row, col++);
        tableModel.setValueAt(chenFormula.evaluate(), row, col);
        this.table.setModel(tableModel);
    }

    private void disableControls() {
        answerComboBox.setEnabled(false);
    }

    private void enableControls() {
        answerComboBox.setEnabled(true);
    }

    public void deal() {
        Hole hole = panel.deal();
        this.chenFormula = new ChenFormula(hole);
        this.currentChenRanking = chenFormula.evaluate();
        this.currentChenRankingLabel = chenFormula.toString();
        answerLabel.setText("");

        table.setModel(new DefaultTableModel());

        enableControls();
        dealButton.setEnabled(false);

        validate();
        repaint();
    }

    private class Panel extends JPanel {
        private Deck deck = new Deck();
        private Hole hole;

        void init() {
            try {
                backgroundImage = ImageIO.read(getClass().getResource("/images/png/poker_felt.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Hole deal() {
            this.hole = randomHoleCards.get(new SecureRandom().nextInt(randomHoleCards.size()) - 1);
            return hole;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            drawTableBackground(g);

            drawHoleCards(g);
        }

        private void drawTableBackground(Graphics g) {
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }

        private void drawHoleCards(Graphics g) {
            final int spacing = Constants.BOARD_CARD_SPACING;

            Dimension cardDimension = getCardDimension();
            int cardWidth = cardDimension.width;
            int cardHeight = cardDimension.height;

            int x = getWidth() / 2 - cardWidth - spacing * 2;
            int y = getHeight() / 2 - cardHeight / 2;

            ImageIcon card1 = images.createScaledImageIconWithWhiteBackground(hole.getCard1().getPngImagePath(), cardWidth, cardHeight);
            drawCard(g, card1, x, y, cardWidth, cardHeight);

            x += cardWidth + spacing * 2;
            ImageIcon card2 = images.createScaledImageIconWithWhiteBackground(hole.getCard2().getPngImagePath(), cardWidth, cardHeight);
            drawCard(g, card2, x, y, cardWidth, cardHeight);
        }

        private Dimension getCardDimension() {
            double cardWidth = this.getWidth() * Constants.BOARD_CARD_WIDTH_FRACTION * 5;
            double cardHeight = cardWidth / Constants.CARD_RATIO;
            return new Dimension((int) Math.round(cardWidth), (int) Math.round(cardHeight));
        }

        private void drawCard(Graphics g, ImageIcon icon, int x, int y, int cardWidth, int cardHeight) {
            g.drawImage(icon.getImage(), x, y, this);
            g.setColor(Color.lightGray);
            g.drawRect(x, y, cardWidth, cardHeight);

            int shadow = 1;
            // Add a shadow to the bottom and right edges for the 3D effect
            g.setColor(new Color(150, 150, 150, 100)); // Shadow color with transparency
            g.fillRect(x + cardWidth, y + shadow, shadow, cardHeight); // Right shadow
            g.fillRect(x + shadow, y + cardHeight, cardWidth, shadow); // Bottom shadow

            // Add a highlight to the top and left edges for the 3D effect
            g.setColor(new Color(255, 255, 255, 100)); // Highlight color with transparency
            g.fillRect(x - shadow, y - shadow, cardWidth, shadow); // Top highlight
            g.fillRect(x - shadow, y - shadow, shadow, cardHeight); // Left highlight
        }

    }

}
