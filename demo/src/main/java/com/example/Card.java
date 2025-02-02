package com.example;
public class Card {
    private String term, definition, imageLink;
    
    Card(String definition, String term, String imageLink){
        this.term = term;
        this.definition = definition;
        this.imageLink = imageLink;
    }
    
    public String getImageLink() {
        return imageLink;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public boolean checkCorrect(String ans){
        if (ans.equals(this.term)) {
            return true;
        }
        return false;
    }
}
