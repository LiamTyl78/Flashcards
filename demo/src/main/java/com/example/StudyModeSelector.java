package com.example;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StudyModeSelector {

    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 200;

    private JDialog frame;
    private JPanel panel;
    private MainMenu mainMenu;

    
    public StudyModeSelector(String filePath, JFrame parent, MainMenu mainMenu){
        //initialize the components and window elements along with referencing the main menu window
        this.mainMenu = mainMenu;
        frame = new JDialog(parent, "", true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(parent);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("How do you want to study this set?");
        JButton flashcardsButton = new JButton("Flashcards");
        JButton multipleChoiceButton = new JButton("Multiple Choice");
        
        multipleChoiceButton.setSize(150, 40);
        multipleChoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudyMode deck = new MultipleChoiceModel(filePath);
                close();
                deck.startMode();
            }
        });

        flashcardsButton.setSize(150, 40);
        flashcardsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudyMode deck = new FlashcardController(filePath, mainMenu);
                close();
                deck.startMode();
            }
        });

        //add components and dividers
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));//divider
        flashcardsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(flashcardsButton);
        panel.add(Box.createVerticalStrut(10));
        multipleChoiceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(multipleChoiceButton);
        frame.add(panel);
        frame.setVisible(true);

    }
    private void close(){
        frame.dispose();
        mainMenu.hide();
    }
}
