package model;

import java.lang.reflect.Array;
import java.util.*;

//Class for group. Stores group name, description, start date, available times and member list.
//Has functions to manage group availability, filter slots based on time.
public class GroupModel {
    // Fields
    private String groupName;
//    private int groupId;
    private String groupDesc;
    private String startDate;
    private ArrayList<MemberModel> memberList = new ArrayList<MemberModel>();
    private ArrayList<Integer> availableTimes = new ArrayList<Integer>();

    //Constructor
    public GroupModel(String gn, String gd, String sd) {
        this.groupName = gn;
        this.groupDesc = gd;
        this.startDate = sd;
    }

    //Getters and setters
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String gn) {
        this.groupName = gn;
    }

    public String getGroupDesc() {
        return this.groupDesc;
    }

    public void setGroupDesc(String gd) {
        this.groupDesc = gd;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String sd) {
        this.startDate = sd;
    }

    public ArrayList<MemberModel> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(ArrayList<MemberModel> mlInp) {
        this.memberList = mlInp;
    }

    public ArrayList<Integer> getAvailableTimes() {
        return this.availableTimes;
    }

    public void setAvailableTimes(ArrayList<Integer> at) {
        this.availableTimes = at;
    }

    //Methods

    //REQUIRES: No same member.getName() in each group
    //MODIFIES: this
    //EFFECTS: Add member in parameter to memberlist
    public void addGroupMember(MemberModel minput) {
        this.memberList.add(minput);
    }

    //REQUIRES: No same member.getName() in each group
    //MODIFIES: this
    //EFFECTS: removes specified member from memberlist
    //IF member is in memberlist, remove then return 1 to show success
    //IF member is not found, return -1
    public int removeGroupMember(String memberName) {
        for (MemberModel curMember : this.memberList) {
            if (curMember.getName() == memberName) {
                this.memberList.remove(curMember);
                return 1;
            }
        }
        return -1;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Goes through every members schedule, finds slots that return false,
    //stores the slot in the format of <DAYNUM><HOUR><MINUTE> into a list
    //returns list where the list of slots are available for every member.
    //Returns empty list if not slots available
    public ArrayList<Integer> findCommonSched() {
        for (int i = 1; i < 8; i++) {
            findCommonInDay(i);
        }
        return this.availableTimes;
    }

    //Helpers

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Finds common slots for the group in a single day by creating a list of time slots, filter where member
    //is free, filters where free slots match the whole group. Adds to group available times.
    private void findCommonInDay(int day) {
        ArrayList<ArrayList<Integer>> memFreePerDay = new ArrayList<ArrayList<Integer>>();
        for (MemberModel m : this.memberList) {
            ArrayList<Integer> freeSlots = new ArrayList<Integer>();
            for (int t = 0; t < 96; t++) {
                int[] timeFormat = getTTime(t);
                if (!(m.getSpecificDay(day).getTimeSlot(timeFormat[0], timeFormat[1]))) {
                    freeSlots.add(day * 10000 + timeFormat[0] * 100 + timeFormat[1]);
                }
            }
            memFreePerDay.add(freeSlots);
        }
        ArrayList<Integer> filterFreeSlots = filterLists(memFreePerDay.get(0), memFreePerDay.get(1));
        for (int x = 2; x < memFreePerDay.size(); x++) {
            filterFreeSlots = filterLists(filterFreeSlots, memFreePerDay.get(x));
        }
        for (int availTimes : filterFreeSlots) {
            this.availableTimes.add(availTimes);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Converts time from slot number to hour:minute format.
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
    //MODIFIES:
    //EFFECTS: Filteres lists for schedules that match, returns that values where slot exists in both lists.
    private ArrayList<Integer> filterLists(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        ArrayList<Integer> filteredInts = new ArrayList<Integer>();
        for (int a : l1) {
            for (int b: l2) {
                if (a == b) {
                    filteredInts.add(a);
                }
            }
        }
        return filteredInts;
    }

    //REQUIRES: this.matchActivityLength()
    //MODIFIES: this
    //EFFECTS: matches the available times and the required duration of activity.
    public ArrayList<ArrayList<Integer>> matchActivityLength(int duration) {
        ArrayList<Integer> freeSlots = this.availableTimes;
        if (freeSlots.isEmpty()) {
            return new ArrayList<ArrayList<Integer>>();
        } else {
            int slotsRequired = (int) Math.ceil((double) duration / 15);;
            ArrayList<Integer> convertSlots = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> meetupSlots = new ArrayList<ArrayList<Integer>>();
            for (int time : freeSlots) {
                convertSlots.add(convertToDaySlotnum(time));
            }
            meetupSlots = matchByLength(convertSlots, slotsRequired);
            return meetupSlots;
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Converts day:hour:minute format to day:slotnumber format.
    private int convertToDaySlotnum(int dayHour) {
        int day = dayHour / 10000;
        int hour = (dayHour - (day * 10000)) / 100;
        int min = dayHour - (day * 10000) - (hour * 100);
        int slot = (day * 100) + (hour * 60 + min) / 15;
        return slot;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Filters free time so that only keep slots where they match group length.
    //Filterse slots that are consecutive and returns them as the availble meeting times.
    private ArrayList<ArrayList<Integer>> matchByLength(ArrayList<Integer> convertSlots, int slotsRequired) {
        ArrayList<ArrayList<Integer>> meetupSlotGroup = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> meetupSlots = new ArrayList<ArrayList<Integer>>();
        boolean flag = true;
        meetupSlotGroup = groupTimeByLength(convertSlots, slotsRequired);
        for (ArrayList<Integer> tsg : meetupSlotGroup) {
            flag = true;
            for (int i = 1; i < tsg.size(); i++) {
                if (tsg.get(i) != tsg.get(i - 1) + 1) {
                    flag = false;
                }
            }
            if (flag) {
                meetupSlots.add(tsg);
            }
        }
        return meetupSlots;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Splits slots into array of slots that have length of the desired activity length.
    private ArrayList<ArrayList<Integer>> groupTimeByLength(ArrayList<Integer> convertSlots, int slotsRequired) {
        ArrayList<ArrayList<Integer>> timeSlotGroup = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < convertSlots.size(); i++) {
            if ((i + slotsRequired - 1) < convertSlots.size()) {
                ArrayList<Integer> timeGroup = new ArrayList<Integer>();
                for (int x = 0; x < slotsRequired; x++) {
                    timeGroup.add(convertSlots.get(i + x));
                }
                timeSlotGroup.add(timeGroup);
            }
        }
        return timeSlotGroup;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Prints out the available meeting times, show if group is unable to meet with given activity length.
    public ArrayList<String> stringAvailTimes(ArrayList<ArrayList<Integer>> timeSlotList) {
        ArrayList<String> timeStringList = new ArrayList<String>();
        if (timeSlotList.size() == 0) {
            timeStringList.add("Unable for group meetup, please use different week.");
        } else {
            for (ArrayList<Integer> timeList : timeSlotList) {
                int[] time1 = getTTime(timeList.get(0) - (timeList.get(0) / 100 * 100));
                int[] time2 = getTTime(timeList.get(timeList.size() - 1)
                        - (timeList.get(timeList.size() - 1) / 100 * 100) + 1);
                String time1m = String.valueOf(time1[1]);
                String time2m = String.valueOf(time2[1]);
                if (time1[1] == 0) {
                    time1m = "00";
                }
                if (time2[1] == 0) {
                    time2m = "00";
                }
                String timeString = "Meetup available at " + time1[0] + ":" + time1m
                        + " until " + time2[0] + ":" + time2m
                        + ", "  + (timeList.get(0) / 100 - 1) + " day(s) from initialized date";
                timeStringList.add(timeString);
            }
        }

        return timeStringList;
    }
}
