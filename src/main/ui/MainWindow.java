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
import java.io.IOException;
import java.util.Scanner;

public class MainWindow {
    private static final String JSON_STORE = "./data/groupsData.json";
    private Scanner inpControl = new Scanner(System.in);
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

    public JFrame getMainMenu() {
        return currFrame;
    }

    private void titleHelper() {
        JLabel appTitle = new JLabel("Hello, welcome to the group scheduler");
        appTitle.setVerticalAlignment(JLabel.TOP);
        appTitle.setHorizontalAlignment(JLabel.CENTER);
        appTitle.setFont(new Font("Verdana", Font.PLAIN, 25));
        panelN.add(appTitle);
        //ImageIcon icon = newImageIcon("")
        //currFrame.setIconImage(icon.getImage);
    }

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

    private void loadStatusLoader() {
        loadString.setText("Group(s) have not been Loaded.");
        loadString.setBounds(321, -250, 700,700);
        panelM.add(loadString);
        if (currUser.getGroupsList().size() > 0) {
            loadStatusWorked();
        }
    }

    private void loadStatusWorked() {
        System.out.println("Load Status True");
        loadString.setText("Group(s) have been Loaded.");
        loadString.setBounds(330, -250, 700,700);
        panelM.add(loadString);
    }

    private void loadStatusError() {
        loadString.setText("Error loading group(s), please restart application.");
        loadString.setBounds(300, -250, 700,700);
        panelM.add(loadString);
    }

    private JButton loadButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Buton Pressed " + name);
                try {
                    currUser = jsonReader.read();
                    System.out.println("Loaded teams from " + JSON_STORE);
                    for (GroupModel g : currUser.getGroupsList()) {
                        System.out.println(g.getGroupName());
                    }
                    loadStatusWorked();
                } catch (IOException err) {
                    loadStatusError();
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
            }
        });
        return currButton;
    }

    private JButton addButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Buton Pressed " + name);
                currFrame.dispose();
                currFrame = new CreateGroupPage(currUser).getFrame();
                currFrame.setVisible(true);
            }
        });
        return currButton;
    }

    private JButton editButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Buton Pressed " + name);
                currFrame.dispose();
                currFrame = new SelectGroupsWindow(currUser).getFrame();
                currFrame.setVisible(true);
            }
        });
        return currButton;
    }

    private JButton exitButtonF(String name) {
        JButton currButton = new JButton(name);
        currButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Buton Pressed " + name);
                System.exit(0);
            }
        });
        return currButton;
    }
}
