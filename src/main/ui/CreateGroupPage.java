package ui;

import model.AppUser;
import model.GroupModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.text.NumberFormat;

//Group creation page
public class CreateGroupPage  {
    private AppUser currUser;
    private GroupModel currGroup;

    private JFrame currFrame = new JFrame();

    JPanel panelN = new JPanel();
    JPanel panelE = new JPanel();
    JPanel panelW = new JPanel();
    JPanel panelS = new JPanel();
    JPanel panelMContent = new JPanel();
    JButton returnButton = new JButton("Discard and return to main menu");
    JTextField groupNameField;
    JTextField groupDescField;
    JTextField dateField;
    JTextField actLenField;

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Initializes group creation page
    public CreateGroupPage(AppUser userInp) {
        this.currUser = userInp;
        //Framte setup
        currFrame.setTitle("Group Scheduler");
        currFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currFrame.setSize(900,600);
        currFrame.setLayout(new BorderLayout());

        //Return Button Setup
        setReturnButton();

        //Panel Setup
        panelHelper();

        //Program title
        titleHelper();

        //Setup input fields
        textInputHelper();


        currFrame.setVisible(true);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: returns the generated frame for group creation.
    public JFrame getFrame() {
        JButton continueToMember = continueMemberData();
        panelMContent.add(continueToMember);
        returnButton.setBounds(0,175,250,30);
        panelMContent.add(returnButton);
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: returns the generated frame for group editing.
    public JFrame groupDetailsEdit(GroupModel cg) {
        this.currGroup = cg;
        JButton continueEdit = continueEditMember();
        panelMContent.add(continueEdit);
        returnButton.setBounds(0,175,250,30);
        panelMContent.add(returnButton);
        groupNameField.setText(cg.getGroupName());
        groupDescField.setText(cg.getGroupDesc());
        dateField.setText(cg.getStartDate());
        actLenField.setText(String.valueOf(cg.getActTime1()));
        return currFrame;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: create a button to continue to the member editing page.
    private JButton continueEditMember() {
        JButton retButton = new JButton("Edit member details");
        retButton.setBounds(0,145,250,30);
        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groupNameField.getText().isEmpty() || groupDescField.getText().isEmpty()
                        || dateField.getText().isEmpty() || dateField.getText().equals("MM/DD/YYYY")) {
                    JFrame errFrame = new InvalidInputPage("Group details cannot be empty.").getFrame();
                    errFrame.setVisible(true);
                } else if (!actLenField.getText().matches("^[0-9]*$")) {
                    JFrame errFrame = new InvalidInputPage("Length of activity can only be a number.").getFrame();
                    errFrame.setVisible(true);
                } else {
                    manageNextFrame();
                }
            }
        });
        return retButton;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: helper for continue edit member, calls and displays the edit member page.
    private void manageNextFrame() {
        currGroup.setGroupName(groupNameField.getText());
        currGroup.setGroupDesc(groupDescField.getText());
        currGroup.setStartDate(dateField.getText());
        currFrame.dispose();
        currGroup.setActTime1(Integer.parseInt(actLenField.getText()));
        JFrame memberFrame = new MemberDataPage(currGroup, currUser).getFrame();
        memberFrame.setVisible(true);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the text input fields
    private void textInputHelper() {
        groupNameField = new JTextField();
        groupDescField = new JTextField();
        dateField = new JTextField();
        actLenField  = new JTextField();
        labelHelper();
        groupNameField.setBounds(125,15,200,30);
        groupDescField.setBounds(125,45,200,30);
        dateField.setBounds(125,75,200,30);
        actLenField.setBounds(125,105,200,30);
        dateField.setText("MM/DD/YYYY");
        panelMContent.add(groupNameField);
        panelMContent.add(groupDescField);
        panelMContent.add(dateField);
        panelMContent.add(actLenField);
    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the labels for the page
    private void labelHelper() {
        JLabel groupNameLabel = new JLabel("Group Name :");
        JLabel groupDescLabel = new JLabel("Group Description :");
        JLabel startDateLabel = new JLabel("Start Date :");
        JLabel activityLengthlabel = new JLabel("Activity Length: ");
        groupNameLabel.setBounds(0,15,150,30);
        groupDescLabel.setBounds(0,45,150,30);
        startDateLabel.setBounds(0,75,150,30);
        activityLengthlabel.setBounds(0,105,150,30);
        panelMContent.add(groupNameLabel);
        panelMContent.add(groupDescLabel);
        panelMContent.add(startDateLabel);
        panelMContent.add(activityLengthlabel);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: generates the member details page, returns a button that activates when pressed.
    private JButton continueMemberData() {
        JButton retButton = new JButton("Continue to Member Details");
        retButton.setBounds(0,145,250,30);
        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groupNameField.getText().isEmpty() || groupDescField.getText().isEmpty()
                        || dateField.getText().isEmpty() || dateField.getText().equals("MM/DD/YYYY")) {
                    JFrame errFrame = new InvalidInputPage("Group details cannot be empty.").getFrame();
                    errFrame.setVisible(true);
                } else if (!actLenField.getText().matches("^[0-9]*$")) {
                    JFrame errFrame = new InvalidInputPage("Length of activity can only be a number.").getFrame();
                    errFrame.setVisible(true);
                } else {
                    GroupModel newGroup = new GroupModel(groupNameField.getText(),
                            groupDescField.getText(), dateField.getText());
                    currFrame.dispose();
                    newGroup.setActTime1(Integer.parseInt(actLenField.getText()));
                    JFrame memberFrame = new MemberDataPage(newGroup, currUser).getFrame();
                    memberFrame.setVisible(true);
                }
            }
        });
        return retButton;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: manages the panel for this current frame.
    private void panelHelper() {
        panelMContent.setLayout(null);

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
        currFrame.add(panelMContent);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: manages the title for the app.
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
    //EFFECTS: generates the return button to go back to main menu.
    private void setReturnButton() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.dispose();
                JFrame mainMenu = new MainWindow().getMainMenu();
                mainMenu.setVisible(true);;
            }
        });
    }
}
