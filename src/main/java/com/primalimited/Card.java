package com.primalimited;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.security.SecureRandom;

class Card {
    private String rank;
    private String suit;
    private String svgImagePath;
    private String pngImagePath;

    public Card(String rank, String suit, String svgImagePath, String pngImagePath) {
        this.rank = rank;
        this.suit = suit;
        this.svgImagePath = svgImagePath;
        this.pngImagePath = pngImagePath;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getSvgImagePath() {
        return svgImagePath;
    }

    public String getPngImagePath() {
        return pngImagePath;
    }

    @Override
    public String toString() {
        return rank + " of " + suit + " [SVG: " + svgImagePath + ", PNG: " + pngImagePath + "]";
    }
}

