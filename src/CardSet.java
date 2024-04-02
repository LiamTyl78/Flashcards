import java.io.File;
import java.util.*;

public class CardSet {
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private int set, currentQuestion, correct = 0, ansPos, sets = 2;
    private Flashcard flashcard;

    public CardSet(){
        this.set = chooseSet();
        init(set);
        shuffle();
    }

    public void newFlashcard(){
        RandomInteger random = new RandomInteger(0, 3);
            flashcard = new Flashcard(questions.get(currentQuestion).getImageLink(),questions.get(currentQuestion).getQuestion(),questions.get(currentQuestion).getAnswer(),this);
            random.SetMax(3);
            random.SetMax(questions.size() - 1);
            int index = random.Generate();
            for (int i = 0; i < 3 && i < questions.size() - 1; i++) {
                while (answers.contains(questions.get(index).getAnswer()) || questions.get(currentQuestion).getAnswer().equals(questions.get(index).getAnswer())) {
                    index = random.Generate();
                }
                answers.add(questions.get(index).getAnswer());
            }
            random.SetMax(answers.size() - 1);
            ansPos = random.Generate();
            answers.add(ansPos, questions.get(currentQuestion).getAnswer());
            flashcard.setButtons(answers);
            flashcard.show();
            
    }

    public void checkAnswer(int ans) {
        if (ans == (ansPos + 1)) {
            flashcard.correct("Correct!");
            correct++;
        } else {
            flashcard.incorrect("Incorrect, the correct answer was \"" + questions.get(currentQuestion).getAnswer() + "\"");
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        flashcard.dispose();
        answers.clear();
        if (currentQuestion < (questions.size() - 1)) {
            currentQuestion++;
        }
        else{
            flashcard.correct("You got " + correct + " answers correct out of " + questions.size());
            return;
        }
        newFlashcard();
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

    private void init(int set) {
        String filepath = "";
        if (set == 1) {// nervous system terms
            filepath = "src/cardsets/NervousSystem.csv";
        }
        if (set == 2) {
            filepath = "src//cardsets//MusclePosistions.csv";
        }
        
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
    
    private int chooseSet() {
        int ans = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Please chooes a set to study\n1) Nervous System Terms\n2) Muscle Positions ");
        while (ans > sets || ans < 1) {
            ans = in.nextInt();
        }
        in.close();
        return ans;
    }
}
