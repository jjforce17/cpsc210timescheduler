package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScheduleButton {
    JButton schedBut = new JButton();

    public ScheduleButton(int id) {
        schedBut.setPreferredSize(new Dimension(75,15));
        schedBut.setBackground(Color.red);
        schedBut.setText("Busy");
        schedBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button ID " + id + " pressed " + schedBut.getText());
                System.out.println(schedBut.getText().equals("Busy"));
                if (schedBut.getText().equals("Busy")) {
                    schedBut.setBackground(Color.green);
                    schedBut.setText("Free");
                }
                if (schedBut.getText().equals("Free")) {
                    schedBut.setBackground(Color.red);
                    schedBut.setText("Busy");
                }
            }
        });
    }

    public JButton getButton() {
        return schedBut;
    }
}
