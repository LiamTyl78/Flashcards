import java.io.File;
import java.util.*;

public class CardSet {
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private int currentQuestionNum, correct = 0, ansPos;
    private Flashcard flashcard;
    private StartWindow start;
    private RandomInteger random = new RandomInteger(0, 3);

    public CardSet(String path){
        loadQuestions(path);
        shuffle();
        this.start = App.start;
        flashcard = new Flashcard(this);
    }
    
    public void Flashcards(){
        System.out.println("Loading Flashcard "+ (currentQuestionNum + 1) + "...");
        
        random.SetMax(questions.size() - 1);
        flashcard.updateFlashcard(questions.get(currentQuestionNum).getImageLink(),questions.get(currentQuestionNum).getQuestion());
        flashcard.setTitle(currentQuestionNum,questions.size());
        int index = random.Generate();
        for (int i = 0; i < 3 && i < questions.size() - 1; i++) {
            boolean containsCurrent = true;
            while ( containsCurrent || questions.get(currentQuestionNum).getAnswer().equals(questions.get(index).getAnswer())) {
                index = random.Generate();
                containsCurrent = answers.contains(questions.get(index).getAnswer());
            }
            String correctAnswer = questions.get(index).getAnswer();
            answers.add(correctAnswer);
        }
        random.SetMax(answers.size() - 1);
        ansPos = random.Generate();
        answers.add(ansPos, questions.get(currentQuestionNum).getAnswer());
        int buttonsShown = answers.size();
        flashcard.setButtons(answers, buttonsShown);
        if (currentQuestionNum < (questions.size())) {
            flashcard.show();
        }
        System.out.println("Done.");
            
    }

    public void checkAnswer(int ans) {
        if (ans == (ansPos + 1)) {
            flashcard.correct("Correct!");
            correct++;
        } else {
            flashcard.incorrect("Incorrect, the correct answer was \"" + questions.get(currentQuestionNum).getAnswer() + "\"");
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        answers.clear();
        if (currentQuestionNum < (questions.size()-1)) {
            currentQuestionNum++;
        }
        else{
            flashcard.hide();
            float percent = ((float)correct / questions.size()) * 100;
            start.show();
            if (percent >= 80) {
                start.correct("You got " + correct + " answers correct out of " + questions.size() +  " for " + percent  + "%! You are doing great!");
            } else {
                start.correct("You got " + correct + " answers correct out of " + questions.size() +  " for " + percent  + "%. Please practice some more!");
            }
            flashcard.dispose();
            return;
        }
        Flashcards();
    }

    private void shuffle() {
        RandomInteger random = new RandomInteger(0, questions.size() - 1);
        for (int i = 0; i < 40; i++) {
            int randomindex = random.Generate();
            Question temp = questions.get(randomindex);
            temp = questions.set(random.Generate(), temp);
            questions.set(randomindex, temp);
        }
    }

    private void loadQuestions(String filepath) {
        try {
            Scanner sc = new Scanner(new File(filepath));
            sc.useDelimiter(",");
            sc.nextLine();
            while (sc.hasNext()) {
                questions.add(new Question(sc.next(), sc.next(), sc.next()));
            }
            sc.close();
        } catch (Exception e) {
            
        }
    }
}
