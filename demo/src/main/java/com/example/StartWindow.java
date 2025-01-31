package com.example;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.*;

public class StartWindow {
    private JScrollPane pane;
    private DefaultListModel<File> list;
    private JFrame frame;
    private JButton studyButton, editButton, helpButton;
    private CardSet deck;
    private JMenuBar menuBar;
    private JMenu file;
    private ArrayList<File> files = new ArrayList<>();


    public StartWindow(){
        System.out.println("Loading Main Menu Interface...");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setTitle("Flashcards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        file = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import study set...");

        importMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                File newFile = importFile();
                if (!(newFile == null)) {
                    files.add(newFile);
                    updateFlashcardList();
                }
            }
        });

        menuBar.add(file);
        file.add(importMenuItem);


        files = readFlashcardSets();
        
        studyButton = new JButton("Study");
        editButton = new JButton("Edit");
        helpButton = new JButton("Help");
        studyButton.setBounds(145, 300, 100, 30);
        editButton.setBounds(255,300,100,30);
        helpButton.setBounds(200,335,100,30);
        
        list = new DefaultListModel<>();

        updateFlashcardList();

        JList<File> flashcardsList = new JList<>(list);
        
        pane = new JScrollPane(flashcardsList);
        pane.setBounds(50, 50, 400, 200);
        pane.setVisible(true);
        // pane.setSize(400, 200);

        studyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (!flashcardsList.isSelectionEmpty()) {
                    String path = (flashcardsList.getSelectedValue().getPath());
                    deck = new CardSet(path);
                    deck.Flashcards();
                    hide();
                    
                }
            }
        });

        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (!flashcardsList.isSelectionEmpty()) {
                    java.io.File selectedFile = new java.io.File(flashcardsList.getSelectedValue().getPath());
                    SwingUtilities.invokeLater(() -> new EditStudySetPane(selectedFile, frame));
                }
            }
        });

        frame.add(pane);
        frame.add(studyButton);
        frame.add(editButton);
        frame.add(menuBar);
        // frame.add(helpButton);
        System.out.println("Done.");

    }

    public void show(){
        frame.setVisible(true);
    }

    public void hide(){
        frame.setVisible(false);
    }

    public void incorrect(String message){
        JOptionPane.showMessageDialog(frame, message,
               "", JOptionPane.ERROR_MESSAGE);
    }

    public void correct(String message){
        JOptionPane.showMessageDialog(frame, message,
               "", JOptionPane.PLAIN_MESSAGE);
    }

    private ArrayList<File> readFlashcardSets(){
        ArrayList<File> files = new ArrayList<>();
        String directoryPath = "cardsets";
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(path -> path.toString().toLowerCase().endsWith(".csv"))
            .forEach(path -> {
                files.add(new File(removeFileExtension(path.getFileName().toString()), path.toAbsolutePath().toString()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    private String removeFileExtension(String filename){
        int dotindx = filename.indexOf(".");
        if (dotindx == -1) {
            return filename;
        }
        return filename.substring(0, dotindx);
    }

    private File importFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV files", "csv"));
        while (true) {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION ) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                File file = new File(removeFileExtension(selectedFile.getName()), selectedFile.getAbsolutePath());
                if (!selectedFile.getAbsolutePath().toLowerCase().endsWith(".csv")) {
                    error("Invalid file type", "Invalid file type. Please select a CSV file.");
                }
                else if (!files.contains(file)) {
                    return file;
                }
                else{
                    return null;
                }
            }
            else{
                return null;
            }
        }
    }

    private void updateFlashcardList(){
        list.clear();
        for (File file : files) {
            list.addElement(file);
        }
    }


    private void error(String title, String message){
        JOptionPane.showMessageDialog(frame, message , title, JOptionPane.ERROR_MESSAGE);  
    }
}
