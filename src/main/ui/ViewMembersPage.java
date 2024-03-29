package ui;

import model.DayScheduleModel;
import model.GroupModel;
import model.MemberModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewMembersPage {
    JFrame currFrame = new JFrame();
    JPanel panelN = new JPanel();
    JPanel panelE = new JPanel();
    JPanel panelW = new JPanel();
    JPanel panelS = new JPanel();

    JPanel panelMContent = new JPanel();
    JScrollPane scrPane = new JScrollPane(panelMContent);

    JButton returnButton = new JButton("Return to Main Menu");
    ArrayList<JLabel> memberLabels = new ArrayList<JLabel>();

    GroupModel currGroup;

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS initlaizes the member data information page.
    public ViewMembersPage(GroupModel g) {
        currFrame.setTitle("Group Scheduler");
        currFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currFrame.setSize(900,600);
        currFrame.setLayout(new BorderLayout());
        this.currGroup = g;

        //Return Button Setup
        setReturnButton();

        //Panel Setup
        panelHelper();

        //Title Setup
        titleHelper();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and returns the current frame.
    public JFrame getFrame() {
        displayGroupDetails();
        generateMemeberDetails();
        displayMemberDetails();
        panelMContent.add(returnButton);
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS displays the current group details
    private void displayGroupDetails() {
        panelMContent.add(new JLabel("Group Name: " + currGroup.getGroupName()));
        panelMContent.add(new JLabel("Group Description: " + currGroup.getGroupDesc()));
        panelMContent.add(new JLabel("Activity Start Date: " + currGroup.getStartDate()));
        panelMContent.add(new JLabel("Activity Length : " + currGroup.getActTime1() + " minutes"));
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates the members details.
    private void generateMemeberDetails() {
        for (MemberModel m : currGroup.getMemberList()) {
            memberLabels.add(new JLabel("Member Name: " + m.getName()));
            memberLabels.add(new JLabel("<html>" + memberSchedString(m) + "</html>"));
            memberLabels.add(new JLabel("---------------------------"));
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS generates and returns the members schedule string.
    private String memberSchedString(MemberModel m) {
        String schedString = "Schedule data: <br>";
        for (int x = 0; x < 7; x++) {
            String boolString = "Day " + (x + 1) + "<br>";
            DayScheduleModel d = m.getMemberSchedule().get(x);
            for (int i = 0; i < 96; i++) {
                int[] timeArr = getTTime(i);
                String[] timeStringL = changeDoubleZero(timeArr[0], timeArr[1]);
                String timeString = timeStringL[0] + ":" +  timeStringL[1];
                if (d.getTimeSlot(timeArr[0], timeArr[1])) {
                    boolString = boolString + "Busy at " + timeString + ", ";
                } else {
                    boolString = boolString + "Free at " + timeString + ", ";
                }
            }
            schedString = schedString +  boolString + "<br>";
        }
        return schedString;
    }

    //REQUIRES:
    //MODIFIES:
    // EFFECTS: returns a string array of a better formatted time for the member schedule.
    private String[] changeDoubleZero(int h, int m) {
        String[] x = new String[2];
        if (h == 0) {
            x[0] = "00";
        } else {
            x[0] = String.valueOf(h);
        }
        if (m  == 0) {
            x[1] = "00";
        } else {
            x[1] = String.valueOf(m);
        }
        return x;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: generate an hour,minute int array based on a timeslots index and returns it.
    private int[] getTTime(int t) {
        int[] retVal = {-1,-1};
        int timeRawMins = t * 15;
        double timeRawHour = timeRawMins / 60d;
        String doubleAsString = String.valueOf(timeRawHour);
        int indexOfDecimal = doubleAsString.indexOf(".");
        int thour = (int)timeRawHour;
        String minuteString = doubleAsString.substring(indexOfDecimal);
        int tminute = -1;
        if (minuteString.equals(".75")) {
            tminute = 45;
        } else if (minuteString.equals(".5")) {
            tminute = 30;
        } else if (minuteString.equals(".25")) {
            tminute = 15;
        } else {
            tminute = 0;
        }
        retVal[0] = thour;
        retVal[1] = tminute;
        return retVal;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: displays the member details.
    private void displayMemberDetails() {
        for (JLabel l : memberLabels) {
            panelMContent.add(l);
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generate and handles the return button.
    private void setReturnButton() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed Return Button");
                currFrame.dispose();
                JFrame mainMenu = new MainWindow().getMainMenu();
                mainMenu.setVisible(true);;
            }
        });
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generate and title for the current frame.
    private void titleHelper() {
        JLabel appTitle = new JLabel("Viewing Group Members");
        appTitle.setVerticalAlignment(JLabel.TOP);
        appTitle.setHorizontalAlignment(JLabel.CENTER);
        appTitle.setFont(new Font("Verdana", Font.PLAIN, 25));
        panelN.add(appTitle);
        //ImageIcon icon = newImageIcon("")
        //currFrame.setIconImage(icon.getImage);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: manages the panels for the current frame
    private void panelHelper() {
        panelMContent.setLayout(new BoxLayout(panelMContent, BoxLayout.Y_AXIS));

        panelE.setBackground(Color.green);
        panelW.setBackground(Color.yellow);
        panelS.setBackground(Color.magenta);

        panelN.setPreferredSize(new Dimension(200,80));
        panelE.setPreferredSize(new Dimension(30,100));
        panelW.setPreferredSize(new Dimension(30,100));
        panelS.setPreferredSize(new Dimension(30,50));

        currFrame.add(panelN, BorderLayout.NORTH);
        currFrame.add(panelE, BorderLayout.EAST);
        currFrame.add(panelW, BorderLayout.WEST);
        currFrame.add(panelS, BorderLayout.SOUTH);
        currFrame.add(scrPane);
    }
}
