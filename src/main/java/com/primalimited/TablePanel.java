package com.primalimited;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel {
    private static final double BOARD_CARD_WIDTH_FRACTION = 0.05;

    private static final int BOARD_CARD_WIDTH = 88;
    private static final int BOARD_CARD_HEIGHT = 130;
    private static final int BOARD_CARD_SPACING = 4;
    private static final int PLAYER_CARD_WIDTH = 44;
    private static final int PLAYER_CARD_HEIGHT = 65;

    private Images images = new Images();
    private BufferedImage backgroundImage;
    private List<Card> board;
    private List<Hole> playerCards;

    private JPanel boardPanel = new JPanel();
    private JPanel playerPanel = new JPanel();

    public TablePanel() {
        init();
    }

    public TablePanel(List<Card> board, List<Hole> playerCards) {
        this.board = board;
        this.playerCards = playerCards;
        init();
    }

    private void init() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/png/poker_felt.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateContents(List<Card> board, List<Hole> playerCards) {
        this.board = board;
        this.playerCards = playerCards;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawTableBackground(g);

        drawBoardCards(g);

        drawPlayerHoleCards(g);
    }

    private void drawPlayerHoleCards(Graphics g) {
        if (playerCards == null || playerCards.isEmpty()) {
            return;
        }

        Dimension playerCardDimension = getPlayerCardDimension();
        int cardWidth = playerCardDimension.width;
        int cardHeight = playerCardDimension.height;

        // hand locations for six hands
        int[] xs = new int[] {
                this.getWidth() / 4 - cardWidth - BOARD_CARD_SPACING,
                this.getWidth() / 2 - cardWidth - BOARD_CARD_SPACING,
                this.getWidth() - this.getWidth() / 4 - cardWidth - BOARD_CARD_SPACING,
                this.getWidth() / 4 - cardWidth - BOARD_CARD_SPACING,
                this.getWidth() / 2 - cardWidth - BOARD_CARD_SPACING,
                this.getWidth() - this.getWidth() / 4 - cardWidth - BOARD_CARD_SPACING
        };
        int[] ys = new int[] {
                this.getHeight() / 3 - cardHeight,
                this.getHeight() / 3 - cardHeight - cardHeight / 2,
                this.getHeight() / 3 - cardHeight,
                this.getHeight() - this.getHeight() / 3,
                this.getHeight() - this.getHeight() / 3 + cardHeight / 2,
                this.getHeight() - this.getHeight() / 3
        };


        int handCount = 0;
        for (Hole hole : playerCards) {
            int x = xs[handCount];
            int y = ys[handCount];
            int count = 0;
            for (Card card : hole.getCards()) {
                ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), cardWidth, cardHeight);
                if (count % 2 == 0) {
                    g.drawImage(icon.getImage(), x, y, this);
                } else {
                    g.drawImage(icon.getImage(), x + cardWidth + (BOARD_CARD_SPACING * 2), y, this);
                }
                count++;
            }

            HandRank handRank = evaluateHand(hole);

            Font font = g.getFont().deriveFont(Font.PLAIN, 14);
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(g.getFont());
            String label = getHandLabel(handRank, hole);
            Rectangle2D rect = fontMetrics.getStringBounds(label, g);
            int buffer = 4;
            int buffer2 = buffer * 2;
            int labelX = x + cardWidth + BOARD_CARD_SPACING * 2 - (int)rect.getWidth()/2 - buffer;
            int labelY = y + cardHeight + BOARD_CARD_SPACING * 3;
//            g.setColor(Color.GREEN.darker().darker().darker());
//            g.fillRect(labelX - buffer, labelY - buffer, (int) rect.getWidth() + buffer2, (int) rect.getHeight() + buffer2);
            g.setColor(Color.WHITE);
//            g.drawRect(labelX - buffer, labelY - buffer, (int) rect.getWidth() + buffer2, (int) rect.getHeight() + buffer2);
            g.drawString(label, labelX, labelY + fontMetrics.getAscent());

            handCount++;
        }
    }

    private String getHandLabel(HandRank handRank, Hole hole) {
        HandEvaluator handEvaluator = new HandEvaluator();
        return handEvaluator.getHandLabel(hole, board);
    }

    private HandRank evaluateHand(Hole hole) {
        List<Card> hand = buildHand(hole);
        HandEvaluator handEvaluator = new HandEvaluator();
        HandRank handRank = handEvaluator.evaluateHand(hand);
        return handRank;
    }

    private List<Card> buildHand(Hole hole) {
        List<Card> hand = new ArrayList<>();
        hand.addAll(hole.getCards());
        hand.addAll(board);
        return hand;
    }

    private void drawBoardCards(Graphics g) {
        int midX = this.getWidth() / 2 - BOARD_CARD_SPACING * 4;
        int midY = this.getHeight() / 2;
        Dimension boardCardDimension = getBoardCardDimension();
        int cardWidth = boardCardDimension.width;
        int cardHeight = boardCardDimension.height;

        if ((board == null || board.isEmpty()) && (playerCards == null || playerCards.isEmpty())) {
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground("/images/png/kem-cardback.png", cardWidth, cardHeight);
            int boardWidth = this.getWidth() - (this.getWidth() / 10);
            double x = midX - boardWidth / 2;
            int y = midY - cardHeight / 2;
            double xIncrement = boardWidth / 52.0;
            for (int i = 0; i < 52; i++) {
                g.drawImage(icon.getImage(), (int)Math.round(x), y, this);
                x += xIncrement;
            }
            return;
        }

        int boardWidth = 5 * cardWidth;

        int x = midX - boardWidth / 2;
        final int y = midY - cardHeight / 2;
        int xIncrement = cardWidth + BOARD_CARD_SPACING * 2;

        if (board == null || board.isEmpty()) {
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground("/images/png/kem-cardback.png", cardWidth, cardHeight);
            for (int i = 0; i < 5; i++) {
                g.drawImage(icon.getImage(), x, y, this);
                x += xIncrement;
            }
            return;
        }

        for (Card card : board) {
            ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), cardWidth, cardHeight);
            g.drawImage(icon.getImage(), x, y, this);
            x += xIncrement;
        }

        ImageIcon icon = images.createScaledImageIconWithWhiteBackground("/images/png/kem-cardback.png", cardWidth, cardHeight);
        for (int i = board.size(); i < 5; i++) {
            g.drawImage(icon.getImage(), x, y, this);
            x += xIncrement;
        }

    }

    private void drawTableBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private Dimension getBoardCardDimension() {
        double cardWidth = this.getWidth() * BOARD_CARD_WIDTH_FRACTION;
        double cardHeight = cardWidth / Constants.CARD_RATIO;
        return new Dimension((int) Math.round(cardWidth), (int) Math.round(cardHeight));
    }

    private Dimension getPlayerCardDimension() {
        double cardWidth = this.getWidth() * BOARD_CARD_WIDTH_FRACTION;
        double cardHeight = cardWidth / Constants.CARD_RATIO;
        return new Dimension((int) Math.round(cardWidth), (int) Math.round(cardHeight));
    }
}
