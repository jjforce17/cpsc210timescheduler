package model;

import java.lang.reflect.Array;
import java.util.*;

public class GroupModel {
    // Fields
    private String groupName;
//    private int groupId;
    private String groupDesc;
    private String startDate;
    private ArrayList<MemberModel> memberList;
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

//    public int getGroupNId() {
//        return this.groupId;
//    }
//
//    public void setGroupId(int gid) {
//        this.groupId = gid;
//    }

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
            findCommonInDay(this.memberList, i);
        }
        return this.availableTimes;
    }

    //Helpers
    private void findCommonInDay(ArrayList<MemberModel> memList, int day) {
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
        ArrayList<Integer> filterFreeSlots;
        filterFreeSlots = filterLists(memFreePerDay.get(0), memFreePerDay.get(1));
        for (int x = 2; x < memFreePerDay.size(); x++) {
            filterFreeSlots = filterLists(filterFreeSlots, memFreePerDay.get(x));
        }
        for (int availTimes : filterFreeSlots) {
            this.availableTimes.add(availTimes);
        }
    }

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

}
