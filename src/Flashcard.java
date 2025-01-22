import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;


public class Flashcard {
    private JFrame f = new JFrame();
    private CardSet deck;
    private JTextArea quesLabel;
    private JLabel image;
    private JButton ans1 = new JButton(), ans2 = new JButton(), ans3 = new JButton(), ans4 = new JButton(), backButton = new JButton("Back");
    private boolean buttonsInit = false;
    private StartWindow start;

    public Flashcard(CardSet deck) {
        image = new JLabel();
        this.deck = deck;
        this.start = App.start;
        quesLabel = new JTextArea();
        f.setSize(700, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLayout(null);
        backButton.setBounds(237,430,150,25);
        backButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            {
                hide();
                start.show();
            } 
        });

        quesLabel.setVisible(true);
        quesLabel.setLineWrap(true);
        quesLabel.setWrapStyleWord(true);
        quesLabel.setBounds(100,150,450,100);
        quesLabel.setEditable(false);
        
        f.add(image);
        f.add(quesLabel);
        f.add(backButton);
        image.setVisible(true);
        
    }

    public void updateFlashcard(String filepath,String question){
        quesLabel.setText(question);
        
        if (!(filepath.equals("na"))) {
            ImageIcon originalIcon = new ImageIcon(filepath); 
            int labelWidth = 500;
            int labelHeight = 300;
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            image.setIcon(scaledIcon);
            image.setSize(labelWidth, labelHeight);
            quesLabel.setVisible(false);
            image.setLocation(75, 30);
            image.setVisible(true);
        }
        else{
            image.setVisible(false); 
            quesLabel.setText(question);
            quesLabel.setVisible(true);
        }
    }

    public void setButtons(ArrayList<String> answers, int buttonsShown){
        ans1.setEnabled(false);
        ans1.setText("");
        ans2.setEnabled(false);
        ans2.setText("");
        ans3.setEnabled(false);
        ans3.setText("");
        ans4.setEnabled(false);
        ans4.setText("");
        System.err.println(buttonsShown);
        if(buttonsShown >= 1){
            ans1.setEnabled(true);
            ans1.setText(answers.get(0));
        }
        if (buttonsShown >= 2) {
            ans2.setText(answers.get(1));
            ans2.setEnabled(true);
        }
        if (buttonsShown >= 3) {
            ans3.setText(answers.get(2));
            ans3.setEnabled(true);
        }
        if (buttonsShown == 4) {
            ans4.setText(answers.get(3));
            ans4.setEnabled(true);
        }

        if(!buttonsInit){
            ans1.setBounds(150,350,150,25);
            ans2.setBounds(150,390,150,25);
            ans3.setBounds(325,350,150,25);
            ans4.setBounds(325,390,150,25);
            buttonsInit = true;
            ans1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) 
                {
                    deck.checkAnswer(1);
                } 
            }); 
            f.add(ans1);
            ans2.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) 
                {
                    deck.checkAnswer(2);
                } 
            }); 
            f.add(ans2);
            ans3.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) 
                {
                    deck.checkAnswer(3);
                } 
            }); 
            f.add(ans3);
            ans4.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) 
                {
                    deck.checkAnswer(4);
                } 
            }); 
            f.add(ans4);
        }
    }

    public void incorrect(String message){
        JOptionPane.showMessageDialog(f, message,
               "", JOptionPane.ERROR_MESSAGE);
    }

    public void correct(String message){
        JOptionPane.showMessageDialog(f, message,
               "", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void show(){
        f.setVisible(true);
        f.toFront();
    }

    public void hide(){
        f.setVisible(false);
    }

    public void dispose(){
        f.dispose();
    }

    public void setTitle(int quesNumber, int totalQues){
        f.setTitle("Question " + (quesNumber + 1) + " of " + totalQues);
    }
}