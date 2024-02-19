package ui;

import model.DayScheduleModel;
import model.GroupModel;
import model.MemberModel;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.Scanner;
import java.util.*;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class

//Acts as current console ui, subject to change in the future.
public class ScheduleApp {
    Scanner inpControl = new Scanner(System.in);
    File currFile;

    //Initialize file reading and ui
    public ScheduleApp() {
        try {
            currFile = new File("data/groupsData.txt");
            if (currFile.createNewFile()) {
                System.out.println("File created: " + currFile.getName());
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
        System.out.println("1. Create New Group");
        System.out.println("2. Access Existing Group");
        System.out.println("3. Exit Program");
        return inputNumber();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Manages main menu input selection.
    private void menuControl() {
        int currChoice = mainMenu();
        if (1 == currChoice) {
            System.out.println("Creating new group");
            createNewGroup();
        } else if (currChoice == 2) {
            System.out.println("Opening groups file");
            readOldGroups();
        } else if (currChoice == 3) {
            System.out.println("Exiting Program");
        } else {
            System.out.println("Invalid input! Please use 1,2 or 3 as choices.");
            menuControl();
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
        }
        insertGroupMembers(memberNum, currGroup);
        currGroup.findCommonSched();
        filterMatchActivityLength(currGroup);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Uses members available times and finds slots that can fit activity length.
    private void filterMatchActivityLength(GroupModel currGroup) {
        System.out.println("How long is the activity? (in minutes)");
        int meetupLen = inputNumber();
        int meetupLen2 = 9999999;
        if (meetupLen > 60) {
            meetupLen2 = meetupLen / 2;
        }
        ArrayList<String> possibleMeet1 = currGroup.stringAvailTimes(currGroup.matchActivityLength(meetupLen));
        ArrayList<String> possibleMeet2 = currGroup.stringAvailTimes(currGroup.matchActivityLength(meetupLen2));
        System.out.println("Group complete, press enter to find group schedule");
        inpControl.nextLine();
        inpControl.nextLine();
        printAvailMeets(possibleMeet1, possibleMeet2, meetupLen, meetupLen2, currGroup);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Prints out to the user the possible meeting times and saves group data to the storage file.
    private void printAvailMeets(ArrayList<String> m1, ArrayList<String> m2, int t1, int t2, GroupModel currGroup) {
        System.out.println("Group name: " + currGroup.getGroupName());
        System.out.println("Group description: " + currGroup.getGroupDesc());
        System.out.println("Scheudle starting date: " + currGroup.getStartDate());
        if (m1.isEmpty()) {
            System.out.println("Unable to meet, please try again with a different scheudle.");
        } else if (t2 == 9999999 || t2 == 9999999 / 2) {
            for (String s1 : m1) {
                System.out.println(s1);
            }
        } else {
            for (String s1 : m1) {
                System.out.println(s1);
            }
            System.out.println("Alternative meeting method where each meeting is " + t2 + " minutes.");
            for (String s1 : m2) {
                System.out.println(s1);
            }
        }
        saveDataToFile(currGroup, t1, t2);
        System.out.println("Press enter to go back to main menu");
        inpControl.nextLine();
        menuControl();
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
    private void saveDataToFile(GroupModel currGroup, int t1, int t2) {
        String gn = currGroup.getGroupName();
        String gd = currGroup.getGroupDesc();
        String gsd = currGroup.getStartDate();
        String gat = currGroup.getAvailableTimes().toString();
        String time1 = String.valueOf(t1);
        String time2 = String.valueOf(t2);
        ArrayList<MemberModel> ml = currGroup.getMemberList();
        ArrayList<String> indvmems = new ArrayList<String>();
        for (MemberModel m : ml) {
            String indvmem = m.getName() + "-" + expStr(m.getMemberSchedule());
            indvmems.add("*" + indvmem);
        }
        String compileString = gn + "//" + gd + "//" + gsd + "//" + gat + "//" + time1 + "//" + time2 + "//"
                + indvmems + "\n";
        //expand
        try {
            FileWriter myWriter = new FileWriter("data/groupsData.txt", true);
            myWriter.write(compileString);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: expands the day schedule model of each day of the members schedule so that it can be stored as a string.
    private String expStr(ArrayList<DayScheduleModel> dsm) {
        ArrayList<String> dsms = new ArrayList<String>();
        for (DayScheduleModel d : dsm) {
            ArrayList<String> schedConv = convBool(d.getScheduleArr());
            String tempString = schedConv.toString();
            dsms.add("_" + tempString);
        }
        return dsms.toString();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: converts the boolean values in users schedule array into string values.
    private ArrayList<String> convBool(Boolean[] barr) {
        ArrayList<String> retval = new ArrayList<String>();
        for (Boolean b : barr) {
            String boolVal = b.toString();
            retval.add(boolVal);
        }
        return retval;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Reads group data from groupsData and initalizes groups based on the data.
    private void readOldGroups() {

    }
}
