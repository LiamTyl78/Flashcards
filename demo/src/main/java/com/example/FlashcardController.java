package com.example;

public class FlashcardController extends StudyMode{
    private FlashcardView view;
    private FlashcardModel model;
    private MainMenu mainMenu;

    public FlashcardController(String filePath, MainMenu mainMenu){
        this.model = new FlashcardModel(filePath, false);
        this.view = new FlashcardView(this);
        this.mainMenu = mainMenu;
        view.update(model.getCurrentFlashcard(), model.getCurrentIndex());
        view.displaySide(false);
    }

    public void handleQuitButton(){
        view.dispose();
        this.mainMenu.show();
    }

    public void handleNextButton(){
        view.update(model.loadNextFlashcard(), model.getCurrentIndex());
        view.displaySide(false);
    }

    public void handlePrevButton(){
        view.update(model.loadPreviousFlashcard(), model.getCurrentIndex());
        view.displaySide(false);
    }

    public void handleMouseClicked(){
        view.displaySide(model.flipFlashcard());
    }

    public int getDeckSize(){
        return model.getFlashcardsSize();
    }
}
