package ui;

import model.AppUser;
import model.DayScheduleModel;
import model.GroupModel;
import model.MemberModel;
import org.json.JSONObject;

import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Scanner;
import java.util.*;

import persistence.*;

import java.io.*;  // Import the FileWriter class

//Acts as current console ui, subject to change in the future.
public class ScheduleApp {
    private static final String JSON_STORE = "./data/groupsData.json";
    private Scanner inpControl = new Scanner(System.in);
    private File currFile;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private AppUser currUser = new AppUser();

    //Initialize file reading and ui
    public ScheduleApp() throws  FileNotFoundException {
        try {
            currFile = new File("data/groupsData.json");
            if (currFile.createNewFile()) {
                System.out.println("File created: " + currFile.getName());
                jsonWriter.open();
                jsonWriter.write(new AppUser());
                jsonWriter.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        menuControl();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Present main menu options.
    private int mainMenu() {
        System.out.println("Hello, welcome to the group scheduler.");
        System.out.println("Please select an option: ");
        System.out.println("1. Create New group (NOTE: This resets existing user file unless user file is loaded).");
        System.out.println("2. Load user file.");
        System.out.println("3. Edit group file.");
        System.out.println("4. Exit Program.");
        return inputNumber();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Manages main menu input selection.
    private void menuControl() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        int currChoice = mainMenu();
        if (1 == currChoice) {
            System.out.println("Creating new group");
            createNewGroup();
        } else if (currChoice == 2) {
            System.out.println("Opening groups file");
            readOldGroups();
        } else if (currChoice == 3) {
            if (currUser.getGroupsList().isEmpty()) {
                System.out.println("No file selected/user groups empty. Please load file or create a group.");
                menuControl();
            } else {
                groupSelector();
            }
        } else if (currChoice == 4) {
            System.out.println("Exiting Program");
        } else {
            System.out.println("Invalid input! Please use 1,2 or 3 as choices.");
            menuControl();
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: prints out list of existing group names and call group select function
    private void groupSelector() {
        for (int i = 0; i < currUser.getGroupsList().size(); i++) {
            System.out.println((i + 1) + ". " + currUser.getGroupsList().get(i).getGroupName());
        }
        getGroupSelect();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Asks user to pick whcih group to select
    private void getGroupSelect() {
        int valInp = inputNumber();
        int groupInd = valInp - 1;
        if (valInp > currUser.getGroupsList().size() || valInp < 0) {
            System.out.println("Inavlid value, please try again");
            getGroupSelect();
        } else {
            groupEdit(currUser.getGroupsList().get(groupInd));
        }
    }

    //REQUIRES:
    //MODIFIES: g
    //EFFECTS: takes in a GroupModel as a param, does the editing process to said GroupModel
    private void groupEdit(GroupModel g) {
        printAvailMeets(g);
        for (MemberModel m : g.getMemberList()) {
            memberEdit(m);
        }
        System.out.println("Current group details:");
        printAvailMeets(g);
        System.out.println("Edit group details:");
        System.out.println("Edit group name: ");
        inpControl.nextLine();
        g.setGroupName(inpControl.nextLine());
        System.out.println("Edit group desc: ");
        g.setGroupDesc(inpControl.nextLine());
        System.out.println("Edit start date: ");
        g.setStartDate(inpControl.nextLine());
        System.out.println("Edit activity details");
        filterMatchActivityLength(g);
        askConfEdit(g);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: asks user if they want to save their edits or discard it.
    private void askConfEdit(GroupModel g) {
        System.out.println("1. Save group.");
        System.out.println("2. Discard group and return to main menu.");
        int userInp = inputNumber();
        if (userInp == 1) {
            try {
                jsonWriter.open();
                jsonWriter.write(currUser);
                jsonWriter.close();
                System.out.println("Saved " + g.getGroupName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
            menuControl();
        } else if (userInp == 2) {
            System.out.println("Group discarded");
            menuControl();
        } else {
            System.out.println("Invalid value, please try again.");
            askSaveFile(g);
        }
    }

    //REQUIRES:
    //MODIFIES: m
    //EFFECTS: edits member individual data
    private void memberEdit(MemberModel m) {
        System.out.println("Editing : " + m.getName());
        System.out.println("1. Edit current member schedule.");
        System.out.println("2. Continue to next member.");
        int valInp = inputNumber();
        if (valInp == 1) {
            for (int i = 0; i < m.getMemberSchedule().size(); i++) {
                editDaySched(m.getMemberSchedule().get(i), i);
            }
        } else if (valInp == 2) {
            return;
        } else {
            System.out.println("Invalid value please choose again.");
            memberEdit(m);
        }
    }

    //REQUIRES:
    //MODIFIES: d
    //EFFECTS: edits the daily schedule of a member
    private void editDaySched(DayScheduleModel d, int dayInd) {
        System.out.println("Editing day: " + (dayInd + 1));
        System.out.println("1. Edit current day schedule.");
        System.out.println("2. Continue to next day.");
        int valInp = inputNumber();
        if (valInp == 1) {
            d.setFullDayFree();
            setDaySchedule(d, dayInd);
        } else if (valInp == 2) {
            return;
        } else {
            System.out.println("Invalid value please choose again.");
            editDaySched(d, dayInd);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Manages number input, insures that inputted value is a valid integer.
    private int inputNumber() {
        while (true) {
            if (inpControl.hasNextInt()) {
                return inpControl.nextInt();
            }
            System.out.println("Invalid input, please type out an integer");
            inpControl.next();
        }
    }

    //REQUIRES:
    //MODIFIES: currGroup
    //EFFECTS: Manages group creation, creates and initializes a new group.
    private void createNewGroup() {
        System.out.println("Please enter group name: ");
        inpControl.nextLine();
        String groupName = inpControl.nextLine();
        System.out.println("Please enter group description: ");
        String groupDesc = inpControl.nextLine();
        System.out.println("Please enter start of schedule date: ");
        String startDate = inpControl.nextLine();
        GroupModel currGroup = new GroupModel(groupName, groupDesc, startDate);
        System.out.println("Please enter how many member you want to create: ");
        int memberNum = inputNumber();
        if (memberNum <= 1) {
            System.out.println("Invalid Number of members, please try again.");
            createNewGroup();
            return;
        }
        insertGroupMembers(memberNum, currGroup);
        filterMatchActivityLength(currGroup);
        askSaveFile(currGroup);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Uses members available times and finds slots that can fit activity length.
    private void filterMatchActivityLength(GroupModel currGroup) {
        System.out.println("How long is the activity? (in minutes)");
        int meetupLen = inputNumber();
        currGroup.setActTime1(meetupLen);
        System.out.println("Group complete, press enter to find group schedule");
        inpControl.nextLine();
        inpControl.nextLine();
        printAvailMeets(currGroup);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Prints out to the user the possible meeting times and saves group data to the storage file.
    private void printAvailMeets(GroupModel currGroup) {
        currGroup.findCommonSched();
        ArrayList<String> pm = currGroup.stringAvailTimes(currGroup.matchActivityLength(currGroup.getActTime1()));
        System.out.println("Group name: " + currGroup.getGroupName());
        System.out.println("Group description: " + currGroup.getGroupDesc());
        System.out.println("Scheudle starting date: " + currGroup.getStartDate());
        System.out.println("Meetup Length: " + currGroup.getActTime1());
        if (currGroup.getAvailableTimes().isEmpty()) {
            System.out.println("Unable to meet, please try again with a different scheudle.");
        } else {
            for (String s1 : pm) {
                System.out.println(s1);
            }
        }
        askViewMember(currGroup);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Asks if user wants to save or discard data.
    private void askSaveFile(GroupModel currGroup) {
        System.out.println("1. Save group.");
        System.out.println("2. Discard group and return to main menu.");
        int userInp = inputNumber();
        if (userInp == 1) {
            saveDataToFile(currGroup);
            menuControl();
        } else if (userInp == 2) {
            System.out.println("Group discarded");
            menuControl();
        } else {
            System.out.println("Invalid value, please try again.");
            askSaveFile(currGroup);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Asks if user wants to view member informaiton.
    private void askViewMember(GroupModel currGroup) {
        System.out.println("1. View individual memeber details.");
        System.out.println("2. Continue. ");
        int userInp = inputNumber();
        if (userInp == 1) {
            System.out.println(currGroup.printGroupMembers());
        } else if (userInp == 2) {
            return;
        } else {
            System.out.println("Invalid value, please try again.");
            askViewMember(currGroup);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Controls member data collection and inserts member into currGroup.memberList.
    private void insertGroupMembers(int memCount, GroupModel currGroupParam) {
        for (int i = 0; i < memCount; i++) {
            inpControl.nextLine();
            String insertName = checkMemberName(currGroupParam.getMemberList());
            MemberModel newMem = new MemberModel(insertName);
            MemberModel scheduledMember = memberSetSchedule(newMem);
            currGroupParam.addGroupMember(scheduledMember);
        }
        System.out.println(memCount + " members added successfully.");
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Manages members schedule for the week.
    private MemberModel memberSetSchedule(MemberModel curMem) {
        for (int i = 0; i < curMem.getMemberSchedule().size(); i++) {
            setDaySchedule(curMem.getMemberSchedule().get(i), i);
        }
        return curMem;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Present day schedule managing options.
    private void setDaySchedule(DayScheduleModel currDay, int dayNum) {
        System.out.println("Please select an option for day " + (dayNum + 1) + ": ");
        System.out.println("1. Set full day busy, then select free hours.");
        System.out.println("2. Set night busy (9pm-12am), then select busy hours during the day.");
        System.out.println("3. Set morning busy (12am-9am) and Set night busy (9pm-12am), "
                + "then select busy hours during the day.");
        System.out.println("4. Set morning busy (12am-9am), "
                + "then select busy hours during the day.");
        System.out.println("5. Select hours busy.");
        System.out.println("6. Full day busy, go to next day.");
        checkInpOptions(currDay, dayNum);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Processes daily set methods, call appropriate method for schedule setting method.
    private void checkInpOptions(DayScheduleModel currDay, int dayNum) {
        int option = inputNumber();
        if (1 == option) {
            currDay.setFullDayBusy();
            selectFreeSlots(currDay);
        } else if (option == 2) {
            currDay.setNightBusy();
            selectBusySlots(currDay, 0, 20);
        } else if (option == 3) {
            currDay.setMorningBusy();
            currDay.setNightBusy();
            selectBusySlots(currDay, 9,20);
        } else if (option == 4) {
            currDay.setMorningBusy();
            selectBusySlots(currDay, 9,23);
        } else if (option == 5) {
            selectBusySlots(currDay, 0,23);
        } else if (option == 6) {
            currDay.setFullDayBusy();
        } else {
            System.out.println("Invalid input, please try again.");
            setDaySchedule(currDay, dayNum);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:  Asks if user is free for every hour in the schedule
    private void selectFreeSlots(DayScheduleModel cd) {
        for (int i = 0; i < 24; i++) {
            askHourFree(cd, i);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Asks user if available during a time duration, calls the slot set method based on input.
    private void askHourFree(DayScheduleModel cd, int hour) {
        System.out.println("Are you free between " + hour + ":00 to " + (hour + 1) + ":00 ? (if free between"
                + "hours select yes)");
        System.out.println("1 for yes, 2 for no");
        int option = inputNumber();
        if (option == 2) {
            String ignore = null;
        } else if (option == 1) {
            askFullHour(hour, false, cd);
        } else {
            System.out.println("Invalid input, please try again");
            askHourFree(cd, hour);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: asks if user is busy for every hour based on the set time start and end times.
    private void selectBusySlots(DayScheduleModel cd, int hourStart, int hourEnd) {
        for (int i = hourStart; i <= hourEnd; i++) {
            askHourBusy(cd, i);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Asks if user is busy during time duration, calls the slot set method based on input.
    private void askHourBusy(DayScheduleModel cd, int hour) {
        System.out.println("Are you busy between " + hour + ":00 to " + (hour + 1) + ":00 ? (if busy between"
                + "hours select yes)");
        System.out.println("1 for yes, 2 for no");
        int option = inputNumber();
        if (option == 2) {
            String ignore = null;
        } else if (option == 1) {
            askFullHour(hour, true, cd);
        } else {
            System.out.println("Invalid input, please try again");
            askHourFree(cd, hour);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Sets the slots in the hour to the appropriate states based on user input.
    private void askFullHour(int hour, boolean status, DayScheduleModel cd) {
        System.out.println("Select slots to be set, to select multiple, please insert in order. (ex: 123)");
        System.out.println("1. " + hour + ":00 and " + hour + ":15");
        System.out.println("2. " + hour + ":15 and " + hour + ":30");
        System.out.println("3. " + hour + ":30 and " + hour + ":45");
        System.out.println("4. " + hour + ":45 and " + (hour + 1) + ":00");
        int option = inputNumber();
        if (option == 1) {
            cd.setTimeSlot(hour, 0, status);
        } else if (option == 2) {
            cd.setTimeSlot(hour, 15, status);
        } else if (option == 3) {
            cd.setTimeSlot(hour, 30, status);
        } else if (option == 4) {
            cd.setTimeSlot(hour, 45, status);
        }  else {
            multipleOptionChoice(hour, status, cd, option);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Manages inputs where multiple time slots are chosen.
    private void multipleOptionChoice(int hour, boolean status, DayScheduleModel cd, int option) {
        if (option == 12) {
            cd.setTimeSlot(hour, 0, status);
            cd.setTimeSlot(hour, 15, status);
        } else if (option == 123) {
            cd.setTimeSlot(hour, 0, status);
            cd.setTimeSlot(hour, 15, status);
            cd.setTimeSlot(hour, 30, status);
        } else if (option == 1234) {
            cd.setHourSlot(hour, 0, status);
        } else if (option == 23) {
            cd.setTimeSlot(hour, 15, status);
            cd.setTimeSlot(hour, 30, status);
        } else if (option == 234) {
            cd.setTimeSlot(hour, 15, status);
            cd.setTimeSlot(hour, 30, status);
            cd.setTimeSlot(hour, 45, status);
        } else if (option == 34) {
            cd.setTimeSlot(hour, 30, status);
            cd.setTimeSlot(hour, 45, status);
        } else {
            System.out.println("Invalid input, please try again");
            askFullHour(hour,status,cd);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Ensures no member name duplication can occur.
    private String checkMemberName(ArrayList<MemberModel> memList) {
        System.out.println("Please enter member name: ");
        String memberName = inpControl.nextLine();
        for (MemberModel m : memList) {
            if (memberName.equals(m.getName())) {
                System.out.println("Member name already exists, please use a different one");
                inpControl.nextLine();
                return checkMemberName(memList);
            }
        }
        return memberName;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Saves currGroup information to groupsData file.
    private void saveDataToFile(GroupModel currGroup) {
        try {
            currUser.addToGroupList(currGroup);
            jsonWriter.open();
            jsonWriter.write(currUser);
            jsonWriter.close();
            System.out.println("Saved " + currGroup.getGroupName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Reads group data from groupsData and initalizes groups based on the data.
    private void readOldGroups() {
        try {
            currUser = jsonReader.read();
            System.out.println("Loaded teams from " + JSON_STORE);
            for (GroupModel g : currUser.getGroupsList()) {
                printAvailMeets(g);
            }
            menuControl();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
