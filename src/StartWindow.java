import javax.swing.*;
public class StartWindow {
    JScrollPane pane;
    JTextArea text;
    public StartWindow(){
        text = new JTextArea();
        pane = new JScrollPane();
        pane.add(text);
        pane.setVisible(true);
    }

    public void show(){
        pane.setVisible(true);
    }
}
