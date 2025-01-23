import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DeckModifyPane {
    private JDialog frame;
    private JPanel cardSetPanel, buttonPanel;
    private int questionFields;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<JTextField> defFields = new ArrayList<>();
    private ArrayList<JTextField> termFields = new ArrayList<>();
    private ArrayList<String> imageFields = new ArrayList<>();

    
    /**
     * Constructor for the DeckModifyPane class
     * @param cardset the file to be edited
     */
    public DeckModifyPane(File cardset, JFrame parent){
        frame = new JDialog(parent, "Editing " + cardset.getName(), true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());
        
        loadFromFile(cardset.getPath());
        int questionNum = questions.size();
        cardSetPanel = new JPanel();
        cardSetPanel.setLayout(new BoxLayout(cardSetPanel, BoxLayout.Y_AXIS));

        while (questionFields < questionNum) {
            String term = questions.get(questionFields).getQuestion();
            String definition = questions.get(questionFields).getAnswer();
            String imageLink = questions.get(questionFields).getImageLink();
            addFlashcardFields(term, definition, imageLink);
        }
        cardSetPanel.add(Box.createVerticalGlue());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add new...");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                addFlashcardFields(null,null,null);
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                save(cardset);
                frame.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // set up the scroll pane layout within the frame to be above the button panel
        JScrollPane scrollPane = new JScrollPane(cardSetPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);

    }

    private void addFlashcardFields(String term, String definition, String imageLink) {
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

        JLabel questionLabel = new JLabel("Question " + (questionFields++ + 1));
        JTextField termTextField = new JTextField(20);
        termTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        termTextField.setText("Term...");
        if (term != null) {
            termTextField.setText(term);
        }
        termTextField.addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (termTextField.getText().equals("Term...")) {
                    termTextField.setText("");  
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (termTextField.getText().equals("")) {
                    termTextField.setText("Term...");
                }
            }
        });

        JTextField defTextField = new JTextField(20);
        defTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        defTextField.setText("Definition...");
        if (definition != null) {
            defTextField.setText(definition);
        }
        defTextField.addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (defTextField.getText().equals("Definition...")) {
                    defTextField.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (defTextField.getText().equals("")) {
                    defTextField.setText("Definition...");
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardSetPanel.remove(questionPanel);
                cardSetPanel.remove(defTextField);
                termTextField.setText("Term...");
                defTextField.setText("Definition...");
                cardSetPanel.remove(termTextField);
                cardSetPanel.remove(deleteButton);
                cardSetPanel.remove(questionLabel);
                frame.revalidate();
                frame.repaint();
            }
        });

        termFields.add(termTextField);
        defFields.add(defTextField);
        imageFields.add(imageLink);

        cardSetPanel.add(Box.createVerticalStrut(10));
        cardSetPanel.add(questionLabel);
        cardSetPanel.add(termTextField);
        cardSetPanel.add(defTextField);
        cardSetPanel.add(deleteButton);
    }

    private void loadFromFile(String filepath) {
        try {
            Scanner sc = new Scanner(new File(filepath));

            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            
            while (sc.hasNext()) {
                String line =  sc.nextLine().trim();
                String[] parts = line.split(",");
                
                if (parts.length >= 3){
                    String definition = parts[0];
                    String term = parts[1];
                    String image = parts[2];
                    questions.add(new Question(term, definition, image));
                }
            }
            sc.close();
        } catch (Exception e) {
            
        }
    }

    private ArrayList<String[]> getFlashcardValues() {
        ArrayList<String[]> flashcards = new ArrayList<>();
        for (int i = 0; i < termFields.size(); i++) {
            String term = termFields.get(i).getText();
            String definition = defFields.get(i).getText();
            String image = imageFields.get(i);
            if (image == null) {
                image = "na";
            }
            if (!term.equals("Term...") && !definition.equals("Definition...")) {
                flashcards.add(new String[]{definition, term, image});
            }
        }
        return flashcards;
    }

    private void save(File file) {
        System.out.println("Saving " + file.getName() + "...");
        ArrayList<String[]> flashcards = getFlashcardValues();

        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(file);
            writer.println("Definition,Term,Image");
            int i = 1;
            for (String[] flashcard : flashcards) {
                writer.println(flashcard[0] + "," + flashcard[1] + "," + flashcard[2] + ",");
                System.out.println("Question " + i++ + " saved.");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done.");


    }
}
