
public class App {
    private static CardSet deck = new CardSet();
    private static StartWindow start = new StartWindow();
    
    
    public static void main(String[] args) throws Exception {
        start.show();
        deck.newFlashcard();
    }    
}
