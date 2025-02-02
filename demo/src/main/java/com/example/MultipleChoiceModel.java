package com.example;
import java.io.File;
import java.util.*;

public class MultipleChoiceModel implements StudyMode{
    private ArrayList<Card> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private int currentQuestionNum, correct = 0, ansPos;
    private MultipleChoiceView multipleChoiceView;
    private MainMenu start;
    private RandomInteger random = new RandomInteger(0, 3);

    public MultipleChoiceModel(String path){
        loadQuestions(path);
        shuffle();
        this.start = App.start;
        multipleChoiceView = new MultipleChoiceView(this);
    }
    @Override
    public void startMode(){
        System.out.println("Loading multiple choice question "+ (currentQuestionNum + 1) + "...");
        
        random.SetMax(questions.size() - 1);
        multipleChoiceView.update(questions.get(currentQuestionNum).getImageLink(),questions.get(currentQuestionNum).getDefinition());
        multipleChoiceView.setTitle(currentQuestionNum,questions.size());
        int index = random.Generate();
        for (int i = 0; i < 3 && i < questions.size() - 1; i++) {
            boolean containsCurrent = true;
            boolean equalsAnswer = true;
            while ( containsCurrent || equalsAnswer) {
                index = random.Generate();
                containsCurrent = answers.contains(questions.get(index).getTerm());
                equalsAnswer = questions.get(currentQuestionNum).getTerm().equals(questions.get(index).getTerm());
            }
            String correctAnswer = questions.get(index).getTerm();
            answers.add(correctAnswer);
        }
        random.SetMax(answers.size() - 1);
        ansPos = random.Generate();
        answers.add(ansPos, questions.get(currentQuestionNum).getTerm());
        int buttonsShown = answers.size();
        multipleChoiceView.setButtons(answers, buttonsShown);
        if (currentQuestionNum < (questions.size())) {
            multipleChoiceView.show();
        }
        System.out.println("Done.");
            
    }

    public void checkAnswer(int ans) {
        if (ans == (ansPos + 1)) {
            multipleChoiceView.correct("Correct!");
            correct++;
        } else {
            multipleChoiceView.incorrect("Incorrect, the correct answer was \"" + questions.get(currentQuestionNum).getTerm() + "\"");
        }
        answers.clear();
        if (currentQuestionNum < (questions.size()-1)) {
            currentQuestionNum++;
        }
        else{
            multipleChoiceView.hide();
            float percent = ((float)correct / questions.size()) * 100;
            percent = (float) (Math.round(percent * 10) / 10.0);
            start.show();
            if (percent >= 80) {
                start.correct("You got " + correct + " answers correct out of " + questions.size() +  " for " + percent  + "%! You are doing great!");
            } else {
                start.correct("You got " + correct + " answers correct out of " + questions.size() +  " for " + percent  + "%. Please practice some more!");
            }
            multipleChoiceView.dispose();
            return;
        }
        startMode();
    }

    private void shuffle() {
        RandomInteger random = new RandomInteger(0, questions.size() - 1);
        for (int i = 0; i < 40; i++) {
            int randomindex = random.Generate();
            Card temp = questions.get(randomindex);
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
                questions.add(new Card(sc.next(), sc.next(), sc.next()));
            }
            sc.close();
        } catch (Exception e) {
            
        }
    }
}
