package ui;

import javax.swing.*;
import java.awt.*;

//Inavlid input error page
public class InvalidInputPage {
    private JFrame currFrame = new JFrame();

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: initalizes the invalid input error page
    public InvalidInputPage(String text) {
        currFrame.setTitle("Invalid input, " + text);
        currFrame.setSize(600,100);
        currFrame.setLayout(null);
        JLabel errLbl = new JLabel("Invalid input, " + text);
        errLbl.setHorizontalAlignment(SwingConstants.CENTER);
        errLbl.setVerticalAlignment(SwingConstants.CENTER);
        errLbl.setBounds(0,0,600,100);
        currFrame.add(errLbl);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: generates and returns the current frame.
    public JFrame getFrame() {
        return currFrame;
    }
}
