package com.example;

public class FlashcardController implements StudyMode{
    private FlashcardView view;
    private FlashcardModel model;
    private MainMenu mainMenu;

    public FlashcardController(String filePath, MainMenu mainMenu){
        this.model = new FlashcardModel(filePath, false);
        this.view = new FlashcardView(this);
        this.mainMenu = mainMenu;
        view.update(model.getCurrentFlashcard());
        view.displaySide(false);
    }

    public void handleQuitButton(){
        view.dispose();
        this.mainMenu.show();
    }

    public void handleNextButton(){
        view.update(model.loadNextFlashcard());
        view.displaySide(false);
    }

    public void handlePrevButton(){
        view.update(model.loadPreviousFlashcard());
        view.displaySide(false);
    }

    public void handleMouseClicked(){
        view.displaySide(model.flipFlashcard());
    }

    @Override
    public void startMode() {

    }
}
