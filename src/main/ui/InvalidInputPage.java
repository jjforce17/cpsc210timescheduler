package ui;

import javax.swing.*;
import java.awt.*;

public class InvalidInputPage {
    private JFrame currFrame = new JFrame();

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

    public JFrame getFrame() {
        return currFrame;
    }
}
