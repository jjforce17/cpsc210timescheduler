package ui;

import javax.swing.*;
import java.awt.*;

public class SaveSuccessPage {
    private JFrame currFrame = new JFrame();

    private ImageIcon smileIcon;

    private JLabel imgLabel = new JLabel();

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS initializes the save success page.
    public SaveSuccessPage() {
        currFrame.setTitle("Successsfully saved group.");
        currFrame.setSize(600,150);
        currFrame.setLayout(null);
        JLabel errLbl = new JLabel("Group saved succesfully, close to continue");
        errLbl.setHorizontalAlignment(SwingConstants.CENTER);
        errLbl.setVerticalAlignment(SwingConstants.CENTER);
        errLbl.setBounds(0,0,300,100);
        showImage();
        currFrame.add(errLbl);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates the smiley face image
    private void showImage() {
        try {
            ImageIcon temp = new ImageIcon("data/icons/smile.jpg");
            Image editImg = temp.getImage();
            editImg = editImg.getScaledInstance(60,60, Image.SCALE_SMOOTH);
            smileIcon = new ImageIcon(editImg);
            if (smileIcon == null) {
                System.out.println("Image not detected");
            }
            imgLabel.setBounds(0,0,60,60);
            imgLabel.setIcon(smileIcon);
            imgLabel.setBounds(300,0,600,100);
            currFrame.add(imgLabel);
        } catch (Exception e) {
            System.out.println("Image error :" + e);
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and returns the current frame.
    public JFrame getFrame() {
        return currFrame;
    }
}
