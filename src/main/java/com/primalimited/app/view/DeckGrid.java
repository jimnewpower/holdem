package com.primalimited.app.view;

import com.primalimited.card.Card;
import com.primalimited.card.Deck;
import com.primalimited.card.Rank;
import com.primalimited.card.Suit;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckGrid extends JPanel implements MouseListener {

    private Images images = new Images();

    private BufferedImage backgroundImage;

    private Deck deck;

    private Map<Card, Boolean> selected = new HashMap<>();

    private Map<Rectangle, Card> rectangleCardMap = new HashMap<>();

    public DeckGrid(Deck deck) {
        this.deck = deck;
        List<Card> cards = deck.getDeck();
        cards.forEach(card -> selected.put(card, false));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/png/poker_felt.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawTableBackground(g);

        paintCards(g);
    }

    private void paintCards(Graphics g) {
        List<Card> cards = deck.getDeck();
        int index = 0;
        int buffer = 12;
        int x = buffer;
        int y = buffer;
        int width = getWidth();
        int cardWidth = (int)Math.round((width - (14.0 * buffer)) / 13.0);
        int cardHeight = (int)Math.round(cardWidth / Constants.CARD_RATIO);
        rectangleCardMap.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = cards.get(index);
                if (selected.get(card)) {
                    ImageIcon icon = images.createScaledImageIconWithGrayBackground(card.getPngImagePath(), cardWidth, cardHeight);
                    g.drawImage(icon.getImage(), x, y, this);
                } else {
                    ImageIcon icon = images.createScaledImageIconWithWhiteBackground(card.getPngImagePath(), cardWidth, cardHeight);
                    g.drawImage(icon.getImage(), x, y, this);
                }
                Rectangle rectangle = new Rectangle(x, y, cardWidth, cardHeight);
                rectangleCardMap.put(rectangle, card);
                x += cardWidth + buffer;
                ++index;
            }
            x = buffer;
            y += cardHeight + buffer;
        }
    }

    private void drawTableBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void selectCard(Card card) {
        selected.put(card, true);
    }

    public void unselectCard(Card card) {
        selected.put(card, false);
    }

    public void toggleCardSelection(Card card) {
        boolean selection = selected.get(card);
        selected.put(card, !selection);
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        boolean haveSelection = false;
        for (Map.Entry<Rectangle, Card> entry : rectangleCardMap.entrySet()) {
            if (entry.getKey().contains(x, y)) {
                toggleCardSelection(entry.getValue());
                haveSelection = true;
                break;
            }
        }
        if (haveSelection) {
            validate();
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
