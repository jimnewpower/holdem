package com.primalimited.stats;

public class QuizTally {
    private int questionsAsked;
    private int correctAnswers;

    public QuizTally() {
        this.questionsAsked = 0;
        this.correctAnswers = 0;
    }

    @Override
    public String toString() {
        double percent = (double) correctAnswers / questionsAsked * 100;
        return correctAnswers + " of " + questionsAsked + " correct " + String.format("%.2f", percent) + "%";
    }

    public void tallyIncorrectAnswer() {
        this.questionsAsked++;
    }

    public void tallyCorrectAnswer() {
        this.questionsAsked++;
        this.correctAnswers++;
    }

    public int getQuestionsAsked() {
        return questionsAsked;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
}
