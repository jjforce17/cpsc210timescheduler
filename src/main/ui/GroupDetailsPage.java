package ui;

import model.AppUser;
import model.GroupModel;
import model.MemberModel;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Group Details page
public class GroupDetailsPage {
    private static final String JSON_STORE = "./data/groupsData.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    JFrame currFrame = new JFrame();

    JPanel panelN = new JPanel();
    JPanel panelE = new JPanel();
    JPanel panelW = new JPanel();
    JPanel panelS = new JPanel();
    JPanel panelMContent = new JPanel();
    JScrollPane scrPane = new JScrollPane(panelMContent);
    JButton returnButton = new JButton("Discard and return to main menu");

    AppUser currUser;
    GroupModel currGroup;

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Initializes the page to display the details of a specific group after creation.
    public GroupDetailsPage(AppUser au, GroupModel cu) {
        currFrame.setTitle("Group Scheduler");
        currFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currFrame.setSize(900,600);
        currFrame.setLayout(new BorderLayout());
        this.currUser = au;
        this.currGroup = cu;

        //Return Button Setup
        setReturnButton();

        //Panel Setup
        panelHelper();

        //Title Setup
        titleHelper();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and returns the frame/window of the gorup details page.
    public JFrame getFrame() {
        generateGroupData();
        panelMContent.add(saveButton());
        panelMContent.add(returnButton);
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: manages the title for the page.
    private void titleHelper() {
        JLabel appTitle = new JLabel("View/Edit your group(s)");
        appTitle.setVerticalAlignment(JLabel.TOP);
        appTitle.setHorizontalAlignment(JLabel.CENTER);
        appTitle.setFont(new Font("Verdana", Font.PLAIN, 25));
        panelN.add(appTitle);
        //ImageIcon icon = newImageIcon("")
        //currFrame.setIconImage(icon.getImage);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the return button to go back to the main menu.
    private void setReturnButton() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                JFrame mainMenu = new MainWindow().getMainMenu();
                mainMenu.setVisible(true);
            }
        });
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: manages the panel of the current frame.
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

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the detials for each group into a label.
    private void generateGroupData() {
        panelMContent.add(new JLabel("Group Name: " + currGroup.getGroupName()));
        panelMContent.add(new JLabel("Group Description: " + currGroup.getGroupDesc()));
        panelMContent.add(new JLabel("Activity Start Date: " + currGroup.getStartDate()));
        panelMContent.add(new JLabel("Activity Length: " + currGroup.getActTime1() + " minutes"));
        panelMContent.add(new JLabel("<html>Available Times: " +  generateTimeString(currGroup) + "</html>"));
        panelMContent.add(new JLabel("----------------------------"));
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: generates the string form of the group activity times and returns it.
    private String generateTimeString(GroupModel g) {
        g.findCommonSched();
        String retString = "ERROR";
        retString = g.stringAvailTimes(g.matchActivityLength(g.getActTime1())).toString();
        return retString;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handels the save button to save the group.
    private JButton saveButton() {
        JButton retButton = new JButton("Save Group.");
        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    managaUser();
                    jsonWriter.open();
                    jsonWriter.write(currUser);
                    jsonWriter.close();
                    managaNextFrame();
                } catch (FileNotFoundException f) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                } catch (Exception x) {
                    System.out.println("Unexpected error, not FNF");
                }
            }
        });
        return retButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: a helper for the save function, internally manages the current user and its groups.
    private void managaUser() {
        if (currUser.getGroupsList().contains(currGroup)) {
            currUser.removeGroupFromList(currGroup);
        }
        currUser.addToGroupList(currGroup);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the next frames such as the success menu and main page.
    private void managaNextFrame() {
        currFrame.dispose();
        JFrame successMenu = new SaveSuccessPage().getFrame();
        JFrame mainMenu = new MainWindow().getMainMenu();
        mainMenu.setVisible(true);
        successMenu.setVisible(true);
    }
}
