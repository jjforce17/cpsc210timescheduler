package ui;

import model.AppUser;
import model.GroupModel;
import model.MemberModel;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

//Member data input page
public class MemberDataPage {
    private AppUser currUser;
    private GroupModel currGroup;
    private MemberModel currMember;

    private JFrame currFrame = new JFrame();

    private JPanel panelN = new JPanel();
    private JPanel panelE = new JPanel();
    private JPanel panelEContent  = new JPanel();
    private JScrollPane panelEScr = new JScrollPane(panelEContent);
    private JPanel panelW = new JPanel();
    private JPanel panelWContent = new JPanel();
    private JPanel panelWTimeSlots = new JPanel();
    private JScrollPane panelWContentScr = new JScrollPane(panelWContent);
    private JPanel userPanel = new JPanel();
    JPanel timeTable = new JPanel();

    private JTextField memberNameField;
    ArrayList<ArrayList<JButton>> buttonListList = new ArrayList<ArrayList<JButton>>();

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: initializes the member data page.
    public MemberDataPage(GroupModel g, AppUser a) {
        this.currUser = a;
        this.currGroup = g;
        //Framte setup
        currFrame.setTitle("Group Scheduler");
        currFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currFrame.setSize(900,600);
        currFrame.setLayout(new BorderLayout());

        //Panel Setup
        panelHelper();

        //Program title
        titleHelper();

        //Input Helper
        memberDataHelper();

        //Table helper
        tableHelper();
        currFrame.setVisible(true);

        //Uesr Panel Update
        updateUserPanel();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and returns the current frame.
    public JFrame getFrame() {
        //member setup
        memberSetup();
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: call helpers to get and load the members data from the current user info.
    private void memberSetup() {
        currMember = new MemberModel("");
        for (int i = 1; i <= 7; i++) {
            currMember.getSpecificDay(i).setFullDayBusy();
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: helper to generate and display member info.
    private void memberDataHelper() {
        memberNameField = new JTextField(25);
        JLabel memberNameLabel = new JLabel("Member Name :");
        JButton nextMemberBtn = genNextMem();
        JButton finishGroupBtn = finishGroup();
        userPanel.add(memberNameLabel);
        userPanel.add(memberNameField);
        userPanel.add(nextMemberBtn);
        userPanel.add(finishGroupBtn);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the finish group button.
    private JButton finishGroup() {
        JButton retBtn = new JButton("Finish Group");
        retBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currGroup.getMemberList().size() < 2) {
                    JFrame errFrame = new InvalidInputPage("Group requires 2 member.").getFrame();
                    errFrame.setVisible(true);
                } else {
                    JFrame displayFrame = new GroupDetailsPage(currUser, currGroup).getFrame();
                    displayFrame.setVisible(true);
                    currFrame.dispose();
                }
            }
        });
        return retBtn;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the get next member button.
    private JButton genNextMem() {
        JButton retBtn = new JButton("Next member");
        retBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (memberNameField.getText().isEmpty()) {
                    JFrame errFrame = new InvalidInputPage("Member name cannot be empty.").getFrame();
                    errFrame.setVisible(true);
                } else {
                    currMember.setName(memberNameField.getText());
                    if (checkMemberName(currGroup.getMemberList())) {
                        currGroup.removeGroupMember(memberNameField.getText());
                    }
                    currGroup.addGroupMember(currMember);
                    memberSetup();
                    memberNameField.setText("");
                    resetBtns();
                    updateUserPanel();
                    panelE.validate();
                }
            }
        });
        return retBtn;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: checks if member name is a duplicate, returns true if yes, false if not.
    private Boolean checkMemberName(ArrayList<MemberModel> memList) {
        String memberName = memberNameField.getText();
        for (MemberModel m : memList) {
            if (memberName.equals(m.getName())) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: resets the time table when changing to a new member.
    private void resetBtns() {
        if (buttonListList.isEmpty()) {
            generateButtonHelper();
        } else {
            for (ArrayList<JButton> arrJB : buttonListList) {
                for (JButton jb : arrJB) {
                    jb.setText("Busy");
                    jb.setBackground(Color.red);
                }
            }
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the timetable in the frame.
    private void tableHelper() {
        JLabel colNames = new JLabel("Time                   "
                + "       Day 1       |       Day 2       |"
                + "       Day 3       |       Day 4       |       Day 5       |"
                + "       Day 6       |       Day 7");
        panelWContent.add(colNames, BorderLayout.NORTH);
        panelWContent.add(panelWTimeSlots, BorderLayout.WEST);
        timeTable.setBackground(Color.red);
        timeTable.setLayout(new BoxLayout(timeTable, BoxLayout.Y_AXIS));
        panelWContent.add(timeTable, BorderLayout.CENTER);
        generateTimeNames();
        generateButtonHelper();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the buttons for the time table
    private void generateButtonHelper() {
        for (int i = 0; i < 96; i++) {
            ArrayList<JButton> buttonList = new ArrayList<JButton>();
            JPanel buttonRow = new JPanel();
            buttonRow.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 1));
            JButton buttonD1 = schedButton(100 + i);
            JButton buttonD2 = schedButton(200 + i);
            JButton buttonD3 = schedButton(300 + i);
            JButton buttonD4 = schedButton(400 + i);
            JButton buttonD5 = schedButton(500 + i);
            JButton buttonD6 = schedButton(600 + i);
            JButton buttonD7 = schedButton(700 + i);
            buttonList.add(buttonD1);
            buttonList.add(buttonD2);
            buttonList.add(buttonD3);
            buttonList.add(buttonD4);
            buttonList.add(buttonD5);
            buttonList.add(buttonD6);
            buttonList.add(buttonD7);
            addBtnToFrame(buttonD1, buttonD2, buttonD3, buttonD4, buttonD5, buttonD6, buttonD7, buttonRow);
            timeTable.add(buttonRow);
            buttonListList.add(buttonList);
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: helper to display the buttons in the timetable
    private void addBtnToFrame(JButton buttonD1,JButton  buttonD2, JButton buttonD3, JButton buttonD4, JButton buttonD5,
                               JButton buttonD6, JButton buttonD7, JPanel buttonRow) {
        buttonRow.add(buttonD1);
        buttonRow.add(buttonD2);
        buttonRow.add(buttonD3);
        buttonRow.add(buttonD4);
        buttonRow.add(buttonD5);
        buttonRow.add(buttonD6);
        buttonRow.add(buttonD7);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: geenrates the individual schedule button in the timetable and handles its functions.
    private JButton schedButton(int id) {
        JButton schedBut = new JButton("");
        schedBut.setPreferredSize(new Dimension(75,15));
        schedBut.setBackground(Color.red);
        schedBut.setText("Busy");
        schedBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (schedBut.getText().equals("Busy")) {
                    buttonListList.get(id - (id / 100) * 100).get(id / 100 - 1).setBackground(Color.green);
                    buttonListList.get(id - (id / 100) * 100).get(id / 100 - 1).setText("Free");
                    changeTimeSlot((id / 100), (id - (id / 100) * 100),false);
                } else if (schedBut.getText().equals("Free")) {
                    buttonListList.get(id - (id / 100) * 100).get(id / 100 - 1).setBackground(Color.red);
                    buttonListList.get(id - (id / 100) * 100).get(id / 100 - 1).setText("Busy");
                    changeTimeSlot((id / 100), (id - (id / 100) * 100),true);
                }
            }
        });
        return schedBut;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: changes the members data based on time table information
    private void changeTimeSlot(int day, int timeSlot, boolean status) {
        int[] timeHM = getTTime(timeSlot);
        currMember.getSpecificDay(day).setTimeSlot(timeHM[0], timeHM[1], status);
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
    //EFFECTS: generates the time labels for the timetable
    private void generateTimeNames() {
        for (int i = 0; i < 96; i++) {
            int[] timeInt = getStringTime(i);
            int[] timeInt2 = getStringTime(i + 1);
            String timeString1 = timeToString(timeInt);
            String timeString2 = timeToString(timeInt2);
            JLabel timeLabel = new JLabel(timeString1 + "-" + timeString2);
            timeLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            panelWTimeSlots.add(timeLabel);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: generates a better formatted string for the time labels and returns the string.
    private String timeToString(int[] timeInt) {
        int h = timeInt[0];
        int m = timeInt[1];
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
        return x[0] + ":" + x[1];
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: generates the time ints based on the time slots index and retuns it as an int array in hour and mnute
    // to be converted to a string later.
    private int[] getStringTime(int t) {
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
    //EFFECTS: generates and handels the title for the current frame.
    private void titleHelper() {
        JLabel appTitle = new JLabel("View/Edit your members");
        appTitle.setVerticalAlignment(JLabel.TOP);
        appTitle.setHorizontalAlignment(JLabel.CENTER);
        appTitle.setFont(new Font("Verdana", Font.PLAIN, 25));
        panelN.add(appTitle);
        //ImageIcon icon = newImageIcon("")
        //currFrame.setIconImage(icon.getImage);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: manages the helpers for the current frame.
    private void panelHelper() {
        panelE.setBackground(Color.green);
        panelW.setBackground(Color.yellow);

        leftPanelHelper();

        panelN.setPreferredSize(new Dimension(200,80));
        panelE.setPreferredSize(new Dimension(200,100));
        panelW.setPreferredSize(new Dimension(687,100));

        currFrame.add(panelN, BorderLayout.NORTH);
        currFrame.add(panelE, BorderLayout.EAST);
        currFrame.add(panelW, BorderLayout.WEST);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: helper for the panel manager for the left part of the frame.
    private void leftPanelHelper() {
        panelW.setLayout(new BoxLayout(panelW, BoxLayout.Y_AXIS));
        panelWContent.setLayout(new BorderLayout());
        panelWTimeSlots.setLayout(new BoxLayout(panelWTimeSlots, BoxLayout.Y_AXIS));
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        userPanel.setPreferredSize(new Dimension(300, 35));
        panelWContentScr.setPreferredSize(new Dimension(300, 6000));

        panelW.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelW.add(userPanel);
        panelW.add(panelWContentScr);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: helper for the panel manager for the user panel.
    private void updateUserPanel() {
        panelE.removeAll();
        panelEScr.removeAll();
        panelEContent.removeAll();
        panelEScr = new JScrollPane(panelEContent);
        panelEContent.setLayout(new BoxLayout(panelEContent, BoxLayout.Y_AXIS));
        panelEContent.setPreferredSize(new Dimension(197,9999));
        panelE.add(panelEScr);
        for (MemberModel m : currGroup.getMemberList()) {
            JLabel memberName = new JLabel("Member name : " + m.getName());
            JButton editMem = editMember(m);
            JButton removeMem = removeMember(m);
            JLabel spacer = new JLabel("---------");
            panelEContent.add(memberName);
            panelEContent.add(editMem);
            panelEContent.add(removeMem);
            panelEContent.add(spacer);
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and handles the member edit button.
    private JButton editMember(MemberModel m) {
        JButton retBtn = new JButton("Edit member");
        retBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memberNameField.setText(m.getName());
                setBtns(m);
                currMember = m;
                timeTable.validate();
            }
        });
        return retBtn;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and handles the member remove button.
    private JButton removeMember(MemberModel m) {
        JButton retBtn = new JButton("Remove member");
        retBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currGroup.removeGroupMember(m.getName());
                updateUserPanel();
                panelE.validate();
            }
        });
        return retBtn;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and displays the buttons for the timetable based on existing user info.
    private void setBtns(MemberModel m) {
        for (int i = 0; i < m.getMemberSchedule().size(); i++) {
            int day = i + 1;
            for (int x = 0; x < 96; x++) {
                int[] timeHM = getTTime(x);
                if (!m.getSpecificDay(day).getTimeSlot(timeHM[0],timeHM[1])) {
                    buttonListList.get(x).get(i).setBackground(Color.green);
                    buttonListList.get(x).get(i).setText("Free");
                } else {
                    buttonListList.get(x).get(i).setBackground(Color.red);
                    buttonListList.get(x).get(i).setText("Busy");
                }
            }
        }
    }
}
