public class Question {
    private String answer, question, imageLink;
    
    Question(String question, String answer, String imageLink){
        this.answer = answer;
        this.question = question;
        this.imageLink = imageLink;
    }
    
    public String getImageLink() {
        return imageLink;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkCorrect(String ans){
        if (ans.equals(this.answer)) {
            return true;
        }
        return false;
    }
}
