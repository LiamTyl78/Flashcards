package com.example;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveData {
    private int cardsSorted, totalCards;
    private ArrayList<Card> cards;

    public SaveData(
        @JsonProperty("cards")ArrayList<Card> cards,
        @JsonProperty("cardsSorted")int cardsSorted, 
        @JsonProperty("totalCards")int totalCards){
        this.cardsSorted = cardsSorted;
        this.totalCards = totalCards;
        this.cards = cards;
    }

    public int getCardsSorted() {
        return cardsSorted;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    
}
