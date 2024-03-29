package ui;

import model.AppUser;
import model.GroupModel;
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


public class SelectGroupsWindow {
    private static final String JSON_STORE = "./data/groupsData.json";
    private Scanner inpControl = new Scanner(System.in);
    private File currFile;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    JFrame currFrame = new JFrame();

    JPanel panelN = new JPanel();
    JPanel panelE = new JPanel();
    JPanel panelW = new JPanel();
    JPanel panelS = new JPanel();
    JPanel panelMContent = new JPanel();
    JScrollPane scrPane = new JScrollPane(panelMContent);
    JButton returnButton = new JButton("Return button");

    AppUser currUser;

    ArrayList<GroupModel> groups = new ArrayList<GroupModel>();
    ArrayList<ArrayList<JLabel>> groupsLabels = new ArrayList<ArrayList<JLabel>>();

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS initializes the group selection window.
    public SelectGroupsWindow(AppUser currUser) {
        currFrame.setTitle("Group Scheduler");
        currFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currFrame.setSize(900,600);
        currFrame.setLayout(new BorderLayout());
        this.currUser = currUser;

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
        generateGroups();
        displayGroups();
        panelMContent.add(returnButton);
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and handles the title for the current frame.
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
    //EFFECTS generates the handles the return button.
    private void setReturnButton() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed Return Button");
                currFrame.dispose();
                JFrame mainMenu = new MainWindow().getMainMenu();
                mainMenu.setVisible(true);
            }
        });
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS manages the panels for the current frame.
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
    //EFFECTS generates the group data based on current user info.
    private void generateGroups() {
        for (GroupModel g : currUser.getGroupsList()) {
            groups.add(g);
            generateGroupLabel(g);
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates the labels for each group.
    private void generateGroupLabel(GroupModel g) {
        ArrayList<JLabel> currGroupLabels = new ArrayList<JLabel>();
        currGroupLabels.add(new JLabel("Group Name: " + g.getGroupName()));
        currGroupLabels.add(new JLabel("Group Description: " + g.getGroupDesc()));
        currGroupLabels.add(new JLabel("Activity Start Date: " + g.getStartDate()));
        currGroupLabels.add(new JLabel("Activity Length: " + g.getActTime1() + " minutes"));
        currGroupLabels.add(new JLabel("<html>Available Times: " +  generateTimeString(g) + "</html>"));
        groupsLabels.add(currGroupLabels);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates the available time for meetups into a formatted string.
    private String generateTimeString(GroupModel g) {
        String retString = "";
        g.findCommonSched();
        retString = g.stringAvailTimes(g.matchActivityLength(g.getActTime1())).toString();
        return retString;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS displays the information for each group along with their respective buttons.
    private void displayGroups() {
        if (currUser.getGroupsList().size() <= 0) {
            panelMContent.add(new JLabel("No Groups available. Please return to menu and Create/Load groups"));
        }
        for (int i = 0; i < groupsLabels.size(); i++) {
            for (JLabel lab : groupsLabels.get(i)) {
                panelMContent.add(lab);
            }
            panelMContent.add(generateViewMemberButton(groups.get(i)));
            panelMContent.add(generateEditButton(groups.get(i)));
            panelMContent.add(generateRemoveButton(groups.get(i)));
            panelMContent.add(new JLabel("----------------------------"));
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and handles the edit group button
    private JButton generateEditButton(GroupModel g) {
        JButton retButton = new JButton("Edit this group.");
        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed Return Button");
                currFrame.dispose();
                JFrame mainMenu = new CreateGroupPage(currUser).groupDetailsEdit(g);
                mainMenu.setVisible(true);;
            }
        });
        return retButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and handles the button to view group member details.
    private JButton generateViewMemberButton(GroupModel g) {
        JButton retButton = new JButton("View Group Member Details.");
        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed View Group Member Details");
                currFrame.dispose();
                JFrame memberPage = new ViewMembersPage(g).getFrame();
                memberPage.setVisible(true);
            }
        });
        return retButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates and handles the remove and save group button.
    private JButton generateRemoveButton(GroupModel g) {
        JButton retButton = new JButton("Remove group and save user.");
        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currUser.getGroupsList().remove(g);
                saveFunction(g);
                managaNextFrame();
            }
        });
        return retButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS handels the save function for the group removal.
    private void saveFunction(GroupModel currGroup) {
        try {
            System.out.println(currUser.getGroupsList().size() + "Size");
            jsonWriter.open();
            jsonWriter.write(currUser);
            jsonWriter.close();
            System.out.println("Saved " + currGroup.getGroupName() + " to " + JSON_STORE);
        } catch (FileNotFoundException f) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (Exception x) {
            System.out.println("Unexpected error, not FNF");
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS generates the success and returns user to main menu.
    private void managaNextFrame() {
        currFrame.dispose();
        JFrame successMenu = new SaveSuccessPage().getFrame();
        JFrame mainMenu = new MainWindow().getMainMenu();
        mainMenu.setVisible(true);
        successMenu.setVisible(true);
    }
}
