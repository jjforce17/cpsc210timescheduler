package ui;

import model.AppUser;
import model.Event;
import model.EventLog;
import model.GroupModel;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//Main window page
public class MainWindow {
    private static final String JSON_STORE = "./data/groupsData.json";
    private File currFile;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private AppUser currUser = new AppUser();

    private JFrame currFrame = new JFrame();

    private JPanel panelN = new JPanel();
    private JPanel panelE = new JPanel();
    private JPanel panelW = new JPanel();
    private JPanel panelS = new JPanel();
    private JPanel panelM = new JPanel();

    JLabel calLogoLab = new JLabel();
    ImageIcon calLogoPic;
    private JLabel loadString = new JLabel("ERROR: loadString STRING NOT SET");

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: initializes the main window and displays it.
    public MainWindow() {

        //Framte setup
        currFrame.setTitle("Group Scheduler");
        currFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currFrame.setSize(900,600);
        currFrame.setLayout(new BorderLayout());

        //Panel Setup
        panelHelper();

        //Program title
        titleHelper();

        //Button Setup
        buttonHelper();

        //Load Status
        loadStatusLoader();

        //Show Image
        showImage();

        JLabel warnLabel = new JLabel("MAKE SURE TO LOAD BEFORE ADDING GROUP AS THIS WILL OVERRIDE PREVIOUS "
                + "GROUPS DATA");
        warnLabel.setBounds(130, -200, 700,700);
        panelM.add(warnLabel);

        currFrame.setVisible(true);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the iamge logo for the top of the program.
    private void showImage() {
        try {
            ImageIcon temp = new ImageIcon("data/icons/callogo2.jpg");
            Image editImg = temp.getImage();
            editImg = editImg.getScaledInstance(60,60, Image.SCALE_SMOOTH);
            calLogoPic = new ImageIcon(editImg);
            if (calLogoPic == null) {
                System.out.println("Image not detected");
            }
            calLogoLab = new JLabel();
            calLogoLab.setBounds(0,0,60,60);
            calLogoLab.setIcon(calLogoPic);
            panelN.add(calLogoLab);
        } catch (Exception e) {
            System.out.println("Image error :" + e);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: generates and returns the main menu.
    public JFrame getMainMenu() {
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: handles the title for the current window.
    private void titleHelper() {
        JLabel appTitle = new JLabel("Hello, welcome to the group scheduler");
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
        panelM.setLayout(null);
        panelN.setLayout(new FlowLayout());

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
        currFrame.add(panelM, BorderLayout.CENTER);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and displays the buttons in the main menu.
    private void buttonHelper() {
        JButton loadButton = loadButtonF("Load Groups");
        JButton addButton = addButtonF("Add Group");
        JButton editButton = editButtonF("View/Edit Groups");
        JButton exitButton = exitButtonF("Exit App");
        loadButton.setBounds((340 - 155),15,150,30);
        addButton.setBounds(340,15,150,30);
        editButton.setBounds((340 + 155),15,150,30);
        exitButton.setBounds(340,50,150,30);
        panelM.add(loadButton);
        panelM.add(addButton);
        panelM.add(editButton);
        panelM.add(exitButton);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: laods the current status for the user (saved/unsaved)
    private void loadStatusLoader() {
        loadString.setText("Group(s) have not been Loaded.");
        loadString.setBounds(321, -250, 700,700);
        panelM.add(loadString);
        if (currUser.getGroupsList().size() > 0) {
            loadStatusWorked();
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the loaded string for the status loader.
    private void loadStatusWorked() {
        loadString.setText("Group(s) have been Loaded.");
        loadString.setBounds(330, -250, 700,700);
        panelM.add(loadString);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the error string for the status loader.
    private void loadStatusError() {
        loadString.setText("Error loading group(s), please restart application.");
        loadString.setBounds(300, -250, 700,700);
        panelM.add(loadString);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the load button.
    private JButton loadButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currUser = jsonReader.read();
                    loadStatusWorked();
                } catch (IOException err) {
                    createFileHelper();
                } catch (Exception ex) {
                    System.out.println("File error");
                    loadStatusError();
                }
            }
        });
        return currButton;
    }

    private void createFileHelper() {
        try {
            currFile = new File("data/groupsData.json");
            if (currFile.createNewFile()) {
                jsonWriter.open();
                jsonWriter.write(new AppUser());
                jsonWriter.close();
                loadStatusWorked();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the add button.
    private JButton addButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                currFrame = new CreateGroupPage(currUser).getFrame();
                currFrame.setVisible(true);
            }
        });
        return currButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the edit button.
    private JButton editButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                currFrame = new SelectGroupsWindow(currUser).getFrame();
                currFrame.setVisible(true);
            }
        });
        return currButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates and handles the exit button.
    private JButton exitButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return currButton;
    }
}
