import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * ImageJFrame
 */
public class Flashcard {
    JFrame f = new JFrame();
    int answer;
    CardSet deck;
    public Flashcard(String filepath, String question, String answer, CardSet deck) {
        this.deck = deck;
        f.setSize(700, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLayout(null);
        ImageIcon originalIcon = new ImageIcon(filepath); 
        JTextArea quesLabel = new JTextArea();
        int labelWidth = 500;
        int labelHeight = 300;
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledIcon);
        label.setSize(labelWidth, labelHeight);
        //f.setAlwaysOnTop(true);
        if (!(filepath.equals(""))) {
            label.setLocation(75, 30);
            f.add(label);
        }
        quesLabel.setLineWrap(true);
        quesLabel.setWrapStyleWord(true);
        quesLabel.setText(question);
        quesLabel.setBounds(100,150,450,100);
        quesLabel.setEditable(false); 
        // quesLabel.set
        f.add(quesLabel);
        
    }

    public void setButtons(ArrayList<String> answers){
        Button A1 = new Button(answers.get(0));
        Button A2 = new Button(answers.get(1));
        Button A3 = new Button(answers.get(2));
        Button A4 = new Button(answers.get(3));
        A1.setBounds(150,350,150,25);
        A2.setBounds(150,390,150,25);
        A3.setBounds(325,350,150,25);
        A4.setBounds(325,390,150,25);
        A1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            {
                deck.checkAnswer(1);
            } 
        }); 
        f.add(A1);
        A2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            {
                deck.checkAnswer(2);
            } 
        }); 
        f.add(A2);
        A3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            {
                deck.checkAnswer(3);
            } 
        }); 
        f.add(A3);
        A4.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            {
                deck.checkAnswer(4);
            } 
        }); 
        f.add(A4);
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
    }

    public void dispose(){
        f.dispose();
    }
}